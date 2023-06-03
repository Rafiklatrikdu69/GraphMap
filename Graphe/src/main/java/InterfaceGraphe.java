import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static javax.swing.JOptionPane.showOptionDialog;

public class InterfaceGraphe extends JFrame {
	private CardLayout cardLayout;
	private JFrame fenetrePrincipale; // la fenetre
	
	public static JPanel cp;
	
	private AccueilPanel accueilPanel;
	private JPanel barreDeChargementPanel,nord;
	private DessinGraphe graphePanel;

	public static DefaultTableModel modelInfosVoisins;
	public  static  DefaultTableModel infoChemin;
	public static  JTable tableInfoChemin;
	public static JTable tableInfosVoisins;
	private JScrollPane affichageVoisinScrollPane;

	private static JButton boutonSuivant, boutonPrecedent;

	private JPanel allPanel;
	private static JPanel affichageVoisinPanel;
	private JPanel contenuNomTypeSommetPanel;
	private JPanel contenuFonctionnalitePanel;
	private static JPanel contenuTousLesCheminsPanel;
	private JPanel contenuAutrePanel;
	private JPanel contenuTousInfosPanel;
	private JPanel contenuButtonSuivPrec;
	private JPanel contenuInfoSommetPanel;
	private JPanel cardPanelInfos;

	private static JLabel nombreRouteLabel, nombreSommetLabel, nomSommetSelectionneLabel, typeSommetSelectionneLabel;
	private static JButton afficherCheminButton;
	private static JComboBox<String> choixTypeCheminComboBox, choixDestinationComboBox;



	
	static JMenuBar menu;
	private JRadioButtonMenuItem modeLight, modeDark;
	private JMenu itemFichier;
	private JMenu itemFenetre;
	private static JMenu itemFonctionnalites;
	private static JMenu itemOptionFonction;
	private static JMenu itemChoixTheme;
	private JMenuItem itemFermerFenetre, itemMenuPrincipale, itemOuvrirFichier;
	
	private  JMenuItem itemAfficherCheminPlusCourts;
	static JRadioButton bloquerGraphe;
	private JProgressBar barreChargement;
	private int progresse;
	private Timer timer;
	
	private Dimension screenSize;
	
	private File fichierCharge;
	public static LCGraphe Graphe;
	private Integer nombreRoutes;
	private Integer nombreCentre;
	static boolean cheminValide = false;
	
	public static JPanel contenuGraphePanel;
	static boolean selectedItem = false;
	
	public InterfaceGraphe() {
		super();
		fenetrePrincipale = this;
		accueilPanel = new AccueilPanel(fenetrePrincipale);
		setContentPane(accueilPanel);

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		setSize(new Dimension(screenWidth / 2 +300, screenHeight / 2 + 200));
		
		initComponents();
		mettreInvisibleComposantSommet();
		mettreInvisibleComposantGraphe();
		
		setTitle("Graphe");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 *
	 */
	private void initComponents() {
		cp = new JPanel();
		cp.setLayout(new BorderLayout());

		contenuGraphePanel = new JPanel();
		contenuGraphePanel.setLayout(new BorderLayout());
		cp.add(contenuGraphePanel, BorderLayout.CENTER);
		initBarreDeMenu();
		initComposantsBarreDeChargement();
		initContainerTousInfos();
		initEventListeners();
	}
	
	/**
	 *
	 */
	private void initContainerTousInfos(){
		cardLayout = new CardLayout();
		allPanel = new JPanel(new BorderLayout());
		cardPanelInfos = new JPanel(cardLayout);
		//cardPanelInfos.setBorder(BorderFactory.createTitledBorder("card layout"));
		cardPanelInfos.setPreferredSize(new Dimension((int) screenSize.getWidth()/6, (int) screenSize.getHeight()));
		contenuTousInfosPanel = new JPanel(new BorderLayout());
		contenuTousInfosPanel.setOpaque(false);
		//Panel Bouton
		contenuButtonSuivPrec = new JPanel();
		boutonSuivant = new JButton("Suivant");
		boutonPrecedent = new JButton("Précédent");
		contenuButtonSuivPrec.setOpaque(false);
		contenuButtonSuivPrec.add(boutonPrecedent);
		contenuButtonSuivPrec.add(boutonSuivant);
	
		initContainerInfoSommet();

		cardPanelInfos.add(contenuInfoSommetPanel,"panel de base ");
		cardPanelInfos.add(contenuTousLesCheminsPanel,"panel Suivant");

		allPanel.setBorder(BorderFactory.createTitledBorder("Panel Infos"));
		allPanel.add(contenuNomTypeSommetPanel, BorderLayout.NORTH);
		allPanel.add(contenuButtonSuivPrec,BorderLayout.SOUTH);
		allPanel.add(cardPanelInfos,BorderLayout.CENTER);
		cp.add(allPanel, BorderLayout.EAST);
	}
	
	/**
	 *
	 */

	private void initContainerInfoSommet(){
		contenuInfoSommetPanel = new JPanel(new BorderLayout());
		contenuInfoSommetPanel.setOpaque(false);

		contenuNomTypeSommetPanel = new JPanel(new GridBagLayout());
		contenuNomTypeSommetPanel.setOpaque(false);
		
		contenuFonctionnalitePanel = new JPanel(new BorderLayout());
		contenuFonctionnalitePanel.setOpaque(false);
		
		contenuTousLesCheminsPanel = new JPanel(new BorderLayout());
		contenuTousLesCheminsPanel.setOpaque(false);
		
		contenuAutrePanel = new JPanel(new BorderLayout());
		contenuAutrePanel.setOpaque(false);

		
		nombreSommetLabel = new JLabel("");
		nombreRouteLabel = new JLabel("");
		
		nomSommetSelectionneLabel = new JLabel("");
		Font font = new Font("Arial", Font.PLAIN, 25);
		nomSommetSelectionneLabel.setFont(font);
		typeSommetSelectionneLabel = new JLabel("");

		afficherCheminButton = new JButton("Afficher chemin");

		choixTypeCheminComboBox = new JComboBox<>();
		choixTypeCheminComboBox.addItem("Distance");
		choixTypeCheminComboBox.addItem("Durée");
		choixTypeCheminComboBox.addItem("Fiabilité");

		choixDestinationComboBox = new JComboBox<>();

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weighty = 1.0;
		contenuNomTypeSommetPanel.add(nomSommetSelectionneLabel, constraints);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weighty = 1.0;
		contenuNomTypeSommetPanel.add(typeSommetSelectionneLabel, constraints);
		//contenuNomTypeSommetPanel.setBorder(BorderFactory.createTitledBorder("Sommet"));
		initContainerInfosVoisins();
	
		contenuFonctionnalitePanel.add(affichageVoisinPanel,BorderLayout.CENTER);
		 nord = new JPanel(new BorderLayout() );
		
		nord.add(choixTypeCheminComboBox,BorderLayout.WEST);
		nord.add(choixDestinationComboBox,BorderLayout.EAST);
		nord.add(afficherCheminButton,BorderLayout.SOUTH);
		contenuTousLesCheminsPanel.add(nord,BorderLayout.NORTH);

	
		contenuTousLesCheminsPanel.setBorder(BorderFactory.createTitledBorder("contenu chemin"));
		initContainerChemin();
		
		contenuAutrePanel.add(contenuFonctionnalitePanel,BorderLayout.NORTH);
		//contenuAutrePanel.add(contenuTousLesCheminsPanel,BorderLayout.CENTER);


		contenuInfoSommetPanel.add(contenuAutrePanel, BorderLayout.CENTER);
		//contenuInfoSommetPanel.setBorder(BorderFactory.createTitledBorder("contenu information"));
	}
	
	/**
	 *
	 */
	private void initContainerInfosVoisins(){

		String[] colonneAttribut = {"Destination", "Distance", "Durée", "Fiabilité"};
		modelInfosVoisins = new DefaultTableModel(colonneAttribut, 0);
		tableInfosVoisins = new JTable(modelInfosVoisins);
		
		affichageVoisinScrollPane = new JScrollPane(tableInfosVoisins);
		affichageVoisinPanel = new JPanel(new BorderLayout());
		affichageVoisinPanel.add(affichageVoisinScrollPane, BorderLayout.CENTER);
		//affichageVoisinPanel.setBorder(BorderFactory.createTitledBorder("infos des voisins"));
	}
	private void initContainerChemin() {
		
		String[] colonne = {"Chemin", "Distance"};
		String choixChemin = (String) getChoixTypeCheminComboBox().getSelectedItem();
		if(choixChemin.equals("Fiabilité")) {
			colonne[1] = "Fiabilité";
			
		}
		infoChemin = new DefaultTableModel(new Object[][]{}, colonne);
		tableInfoChemin = new JTable(infoChemin);
		tableInfoChemin.setModel(InterfaceGraphe.infoChemin);
		// Réinitialisez le nombre de lignes à zéro pour supprimer toutes les données existantes
		infoChemin.setRowCount(0);
		
		// Mettez à jour les colonnes du modèle de tableau
		infoChemin.setColumnIdentifiers(colonne);
		
		// Ajouter une ligne de données avec des valeurs fictives
		
		
		JPanel info = new JPanel(new BorderLayout());
		info.add(new JScrollPane(tableInfoChemin), BorderLayout.CENTER);
		info.setBorder(BorderFactory.createTitledBorder("Chemin"));
		contenuTousLesCheminsPanel.add(info, BorderLayout.CENTER);
		infoChemin.setRowCount(0);
	}
	
	
	
	
	
	
	/**
	 *
	 */

	private void initContainerDessinGraphePanel() {
		graphePanel = new DessinGraphe();
		//contenuGraphePanel.setBorder(BorderFactory.createTitledBorder("Graphe dessiner"));
		contenuGraphePanel.setOpaque(false);
		contenuGraphePanel.add(graphePanel, BorderLayout.CENTER);
	}
	
	/**
	 *
	 */
	private void initBarreDeMenu(){
		initialisationJMenu();
		initialisationJMenuItem();
		menu = new JMenuBar();
		bloquerGraphe = new JRadioButton("Bloquer");
		itemFonctionnalites.add(bloquerGraphe);
		itemFonctionnalites.setFont(new Font("Arial", Font.BOLD, 14));

		itemFichier.add(itemOuvrirFichier);
		itemFichier.setFont(new Font("Arial", Font.BOLD, 14));

		itemFenetre.add(itemChoixTheme);
		itemFenetre.add(itemMenuPrincipale);
		itemFenetre.add(itemFermerFenetre);
		itemFenetre.setFont(new Font("Arial", Font.BOLD, 14));

		itemOptionFonction.add(itemAfficherCheminPlusCourts);
		itemOptionFonction.setFont(new Font("Arial", Font.BOLD, 14));

		ButtonGroup groupeBoutons = new ButtonGroup();
		groupeBoutons.add(modeLight);
		groupeBoutons.add(modeDark);

		itemChoixTheme.add(modeLight);
		itemChoixTheme.add(modeDark);

		menu.add(itemFichier);
		menu.add(itemFenetre);
		menu.add(itemFonctionnalites);
		menu.add(itemOptionFonction);
	}
	
	/**
	 *
	 */
	private void initialisationJMenu(){
		itemFichier = new JMenu("Fichier");
		itemFenetre = new JMenu("Fenetre");
		itemChoixTheme = new JMenu("Theme");
		itemFonctionnalites = new JMenu("Mode du Graphe");
		itemOptionFonction = new JMenu("Fonctionnalitées");
	}
	
	/**
	 *
	 */
	private void initialisationJMenuItem(){
		itemOuvrirFichier = new JMenuItem("Ouvrir");
		itemFermerFenetre = new JMenuItem("Fermer");
		itemMenuPrincipale = new JMenuItem("Menu Principale");
		itemMenuPrincipale.setEnabled(false);
		itemAfficherCheminPlusCourts = new JMenuItem("Calculer Itineraire");
		itemAfficherCheminPlusCourts.setEnabled(false);
		modeLight = new JRadioButtonMenuItem("Light", true);
		modeDark = new JRadioButtonMenuItem("Dark");
	}
	
	/**
	 *
	 */
	private void initComposantsBarreDeChargement() {
		barreDeChargementPanel = new JPanel(new BorderLayout());
		barreDeChargementPanel.setPreferredSize(new Dimension((int) screenSize.getWidth(), 45));
		cp.add(barreDeChargementPanel, BorderLayout.NORTH);
	}
	
	/**
	 *
	 */
	public void demarrerChargement() {
		progresse = 0;
		timer.start();
	}
	
	/**
	 *
	 */
	private void initialisationBarreDeChargement() {
		barreDeChargementPanel.removeAll();
		barreChargement = new JProgressBar(0, 100);
		barreDeChargementPanel.add(barreChargement);
		barreChargement.setStringPainted(true);
	}
	
	/**
	 *
	 * @param fichierCharge
	 */
	private void initialisationTimer(File fichierCharge) {
		barreDeChargementPanel.setVisible(true);
		contenuGraphePanel.removeAll();
		mettreInvisibleComposantSommet();
		mettreInvisibleComposantGraphe();
		timer = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				progresse++;
				barreChargement.setValue(progresse);
				if (progresse == 100) {
					timer.stop();
					Graphe = new LCGraphe();
					//contenuTousInfosPanel.setBackground(Color.YELLOW);
					barreDeChargementPanel.setVisible(false);
					mettreVisibleComposantGraphe();
					chargerNouveauFichier(fichierCharge);
					initContainerDessinGraphePanel();
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
	}
	
	/**
	 *
	 */
	public void initEventListeners() {
		boutonSuivant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.previous(cardPanelInfos);
			}
		});
		boutonPrecedent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.next(cardPanelInfos);
			}
		});
		itemOuvrirFichier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fenetreOuvertureFichier = new JFileChooser(new File("."));
				File fichier = new File("\\src\\fichiersGraphe\\");
				fenetreOuvertureFichier.setCurrentDirectory(fichier);
				if (fenetreOuvertureFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fichier = fenetreOuvertureFichier.getSelectedFile();
					System.out.println(fichier.getPath());
					if (fichier.getPath().endsWith(".csv")) {
						setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

						initialisationBarreDeChargement();

						initialisationTimer(fichier);
						
						// Appel de demarrerChargement()
						demarrerChargement();
					} else {
						JPanel panelCorrompu = new JPanel();
						int result = showOptionDialog(null, panelCorrompu, "Format fichier invalide ! ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
						
					}
				}
			}
			
		});
		itemFermerFenetre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		itemMenuPrincipale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetrePrincipale.setContentPane(accueilPanel);
			}
		});
		modeLight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Changer le thème en utilisant le nom de classe du look and feel
					UIManager.setLookAndFeel(new FlatMacLightLaf());

					// Mettre à jour les composants de la fenêtre pour refléter le nouveau thème
					SwingUtilities.updateComponentTreeUI(fenetrePrincipale);
					if(graphePanel != null){
						graphePanel.setCouleurTexteSommet(Color.WHITE);
						graphePanel.setDefautCouleurSommet(Color.BLACK);
						graphePanel.setCouleurSommetSelect(Color.BLUE);
						graphePanel.setCouleurArete(Color.BLACK);
						graphePanel.miseAJourDessin();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		modeDark.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Changer le thème en utilisant le nom de classe du look and feel
					UIManager.setLookAndFeel(new FlatMacDarkLaf());

					// Mettre à jour les composants de la fenêtre pour refléter le nouveau thème
					SwingUtilities.updateComponentTreeUI(fenetrePrincipale);
					if(graphePanel != null){
						graphePanel.setCouleurTexteSommet(Color.BLACK);
						graphePanel.setDefautCouleurSommet(Color.LIGHT_GRAY);
						graphePanel.setCouleurSommetSelect(Color.DARK_GRAY);
						graphePanel.setCouleurArete(Color.DARK_GRAY);
						graphePanel.miseAJourDessin();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
	
	/**
	 *
	 * @param file
	 */

	private void chargerNouveauFichier(File file) {
		Graphe.chargementFichier(file.getPath());
	}
	
	/**
	 *
	 */
	public static void mettreInvisibleComposantSommet(){
		nomSommetSelectionneLabel.setText("");
		typeSommetSelectionneLabel.setText("");

		afficherCheminButton.setVisible(false);
		choixTypeCheminComboBox.setVisible(false);
		choixDestinationComboBox.setVisible(false);
		boutonSuivant.setVisible(false);
		boutonPrecedent.setVisible(false);
		affichageVoisinPanel.setVisible(false);
		contenuTousLesCheminsPanel.setVisible(false);
	}
	
	/**
	 *
	 */
	private void mettreInvisibleComposantGraphe(){
		itemFonctionnalites.setEnabled(false);
		itemOptionFonction.setEnabled(false);
		itemChoixTheme.setEnabled(false);
	}
	
	/**
	 *
	 */
	private void mettreVisibleComposantGraphe(){
		itemFonctionnalites.setEnabled(true);
		itemOptionFonction.setEnabled(true);
		itemChoixTheme.setEnabled(true);
	}
	
	/**
	 *
	 * @param nom
	 * @param type
	 */
	public static void mettreVisibleComposantSommet(String nom, String type){
		nomSommetSelectionneLabel.setText("Dispensaire "+nom);
		typeSommetSelectionneLabel.setText(type);
		itemFonctionnalites.setEnabled(true);
		itemOptionFonction.setEnabled(true);
		itemChoixTheme.setEnabled(true);
		boutonSuivant.setVisible(true);
		boutonPrecedent.setVisible(true);
		afficherCheminButton.setVisible(true);
		choixTypeCheminComboBox.setVisible(true);
		choixDestinationComboBox.setVisible(true);
		affichageVoisinPanel.setVisible(true);
	}
	
	/**
	 *
	 * @return
	 */
	public static JButton getAfficherCheminButton() {
		return afficherCheminButton;
	}
	
	/**
	 *
	 * @return
	 */
	public static JComboBox<String> getChoixTypeCheminComboBox() {
		return choixTypeCheminComboBox;
	}
	
	/**
	 *
	 * @return
	 */
	public static JComboBox<String> getChoixDestinationComboBox() {
		return choixDestinationComboBox;
	}
}
