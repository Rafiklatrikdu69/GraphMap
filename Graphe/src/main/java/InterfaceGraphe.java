import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static javax.swing.JOptionPane.showOptionDialog;

public class InterfaceGraphe extends JFrame {
	private JFrame fenetrePrincipale; // la fenetre
	
	public static JPanel cp;
	
	private AccueilPanel accueilPanel;
	private JPanel barreDeChargementPanel, graphePanel;

	private JPanel contenuTousInfosPanel, contenutInfoGraphePanel,contenuInfoSommetPanel;
	private JPanel contenuNomTypeSommetPanel, contenuFonctionnalitePanel, contenuTousLesCheminsPanel, contenuAutrePanel;

	private static JLabel nombreRouteLabel, nombreSommetLabel, nomSommetSelectionneLabel, typeSommetSelectionneLabel;
	private static JButton afficherCheminButton, afficherVoisinsButton;
	private static JComboBox<String> choixTypeCheminComboBox, choixDestinationComboBox;



	
	static JMenuBar menu;
	private JRadioButtonMenuItem modeLight, modeDark;
	private JMenu itemFichier, itemFenetre, itemFonctionnalites, itemOptionFonction, itemChoixTheme;
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
	;
	
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
		
		setTitle("Graphe");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void initContainerTousInfos(){
		contenuTousInfosPanel = new JPanel(new BorderLayout());
		contenuTousInfosPanel.setOpaque(false);
		contenuTousInfosPanel.setPreferredSize(new Dimension((int) screenSize.getWidth()/6, (int) screenSize.getHeight()));

		contenutInfoGraphePanel = new JPanel();
		contenutInfoGraphePanel.setBorder(BorderFactory.createTitledBorder("Info Graphe"));
		contenutInfoGraphePanel.setOpaque(false);
		//contenutInfoGraphePanel.setBorder(BorderFactory.createTitledBorder("info Graphe"));

		initContainerInfoSommet();

		contenuTousInfosPanel.add(contenutInfoGraphePanel, BorderLayout.SOUTH);
		contenuTousInfosPanel.add(contenuInfoSommetPanel, BorderLayout.CENTER);
		contenuTousInfosPanel.setBorder(BorderFactory.createTitledBorder("toutes les infos"));
		cp.add(contenuTousInfosPanel, BorderLayout.EAST);
	}

	private void initContainerInfoSommet(){
		contenuInfoSommetPanel = new JPanel(new BorderLayout());
		contenuInfoSommetPanel.setOpaque(false);
		//contenuInfoSommetPanel.setBorder(BorderFactory.createTitledBorder("Info Sommet Select"));

		contenuNomTypeSommetPanel = new JPanel(new GridBagLayout());
		contenuNomTypeSommetPanel.setOpaque(false);
		contenuFonctionnalitePanel = new JPanel(new BorderLayout());
		contenuFonctionnalitePanel.setOpaque(false);
		contenuTousLesCheminsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		contenuTousLesCheminsPanel.setOpaque(false);
		contenuAutrePanel = new JPanel(new BorderLayout());
		contenuAutrePanel.setOpaque(false);




		nombreSommetLabel = new JLabel("");
		nombreRouteLabel = new JLabel("");
		nomSommetSelectionneLabel = new JLabel("");
		Font font = new Font("Arial", Font.PLAIN, 25);
		nomSommetSelectionneLabel.setFont(font);
		typeSommetSelectionneLabel = new JLabel("");

		afficherVoisinsButton = new JButton("Afficher les voisins");
		afficherVoisinsButton.setVisible(false);

		afficherCheminButton = new JButton("Afficher chemin");
		afficherCheminButton.setVisible(false);

		choixTypeCheminComboBox = new JComboBox<>();
		choixTypeCheminComboBox.addItem("Distance");
		choixTypeCheminComboBox.addItem("Durée");
		choixTypeCheminComboBox.addItem("Fiabilité");
		choixTypeCheminComboBox.setVisible(false);

		choixDestinationComboBox = new JComboBox<>();
		choixDestinationComboBox.setVisible(false);
		//choixTypeCheminComboBox.addItem("Tout");

		//contenutInfoGraphePanel.add(nombreSommetLabel);
		//contenutInfoGraphePanel.add(nombreRouteLabel);

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
		contenuNomTypeSommetPanel.setBorder(BorderFactory.createTitledBorder("type sommet"));
		contenuFonctionnalitePanel.add(afficherVoisinsButton,BorderLayout.NORTH);
		JPanel affichageVoisin = new JPanel();
		affichageVoisin.setBorder(BorderFactory.createTitledBorder("infos des voisins"));
		contenuFonctionnalitePanel.add(affichageVoisin,BorderLayout.CENTER);
		contenuFonctionnalitePanel.setBorder(BorderFactory.createTitledBorder("contenu fonction panel"));
		contenuTousLesCheminsPanel.add(choixTypeCheminComboBox);
		contenuTousLesCheminsPanel.add(choixDestinationComboBox);
		contenuTousLesCheminsPanel.add(afficherCheminButton);
		contenuTousLesCheminsPanel.setBorder(BorderFactory.createTitledBorder("contenu chemin"));

		contenuAutrePanel.add(contenuFonctionnalitePanel,BorderLayout.NORTH);
		contenuAutrePanel.add(contenuTousLesCheminsPanel,BorderLayout.CENTER);
		

		contenuAutrePanel.setBorder(BorderFactory.createTitledBorder("contenu autre panel"));

		contenuInfoSommetPanel.add(contenuNomTypeSommetPanel, BorderLayout.NORTH);
		contenuInfoSommetPanel.add(contenuAutrePanel, BorderLayout.CENTER);
		contenuInfoSommetPanel.setBorder(BorderFactory.createTitledBorder("contenu information"));
	}

	private void initContainerDessinGraphePanel() {
		graphePanel = new DessinGraphe();
		//contenuGraphePanel.setBorder(BorderFactory.createTitledBorder("Graphe dessiner"));
		contenuGraphePanel.setOpaque(false);
		contenuGraphePanel.add(graphePanel, BorderLayout.CENTER);
	}
	
	private void initComposantsBarreDeChargement() {
		barreDeChargementPanel = new JPanel(new BorderLayout());
		barreDeChargementPanel.setPreferredSize(new Dimension((int) screenSize.getWidth(), 45));
		//barreDeChargementPanel.setBorder(BorderFactory.createTitledBorder("Barre de chargement"));
		cp.add(barreDeChargementPanel, BorderLayout.NORTH);
	}
	
	private void initialisationJMenu(){
		itemFichier = new JMenu("Fichier");
		itemFenetre = new JMenu("Fenetre");
		itemChoixTheme = new JMenu("Theme");
		itemFonctionnalites = new JMenu("Mode du Graphe");
		itemOptionFonction = new JMenu("Fonctionnalitées");
	}
	private void initialisationJMenuItem(){
		itemOuvrirFichier = new JMenuItem("Ouvrir");
		itemFermerFenetre = new JMenuItem("Fermer");
		itemMenuPrincipale = new JMenuItem("Menu Principale");
		itemMenuPrincipale.setEnabled(false);
		itemAfficherCheminPlusCourts = new JMenuItem("Calculer Itineraire");
		modeLight = new JRadioButtonMenuItem("Light", true);
		modeDark = new JRadioButtonMenuItem("Dark");
	}
	private void initComponents() {
		cp = new JPanel();
		cp.setLayout(new BorderLayout());
		
		menu = new JMenuBar();
		
		initialisationJMenu();
		initialisationJMenuItem();
		bloquerGraphe = new JRadioButton("Bloquer");
		itemFonctionnalites.add(bloquerGraphe);
		menu.add(itemFonctionnalites);
		
		itemFichier.add(itemOuvrirFichier);
		itemFichier.setFont(new Font("Arial", Font.BOLD, 14));
		itemFonctionnalites.setFont(new Font("Arial", Font.BOLD, 14));
		itemFenetre.setFont(new Font("Arial", Font.BOLD, 14));
		itemOptionFonction.setFont(new Font("Arial", Font.BOLD, 14));
		menu.setBackground(Color.GRAY);

		ButtonGroup groupeBoutons = new ButtonGroup();
		groupeBoutons.add(modeLight);
		groupeBoutons.add(modeDark);
		itemChoixTheme.add(modeLight);
		itemChoixTheme.add(modeDark);

		itemFenetre.add(itemChoixTheme);
		itemFenetre.add(itemMenuPrincipale);
		itemFenetre.add(itemFermerFenetre);

		itemOptionFonction.add(itemAfficherCheminPlusCourts);
		
		menu.add(itemFichier);
		menu.add(itemFenetre);
		menu.add(itemFonctionnalites);
		menu.add(itemOptionFonction);
		contenuGraphePanel = new JPanel();
		contenuGraphePanel.setLayout(new BorderLayout());
		//contenuGraphePanel.setBorder(BorderFactory.createTitledBorder("Graphe"));
		cp.add(contenuGraphePanel, BorderLayout.CENTER);
		
		initComposantsBarreDeChargement();
		initContainerTousInfos();
		initEventListeners();
	}
	
	public void demarrerChargement() {
		progresse = 0;
		timer.start();
	}
	
	private void initialisationBarreDeChargement() {
		barreDeChargementPanel.removeAll();
		barreChargement = new JProgressBar(0, 100);
		barreDeChargementPanel.add(barreChargement);
		barreChargement.setStringPainted(true);
	}
	
	private void initialisationTimer(File fichierCharge) {
		barreDeChargementPanel.setVisible(true);
		contenuGraphePanel.removeAll();
		contenuTousInfosPanel.setBorder(BorderFactory.createTitledBorder("contenu info Graphe"));
		//contenuTousInfosPanel.setBackground(null);
		mettreInvisibleComposant();
		timer = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				progresse++;
				barreChargement.setValue(progresse);
				if (progresse == 100) {
					timer.stop();
					Graphe = new LCGraphe();
					//contenuTousInfosPanel.setBackground(Color.YELLOW);
					contenuTousInfosPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
					barreDeChargementPanel.setVisible(false);
					chargerNouveauFichier(fichierCharge);
					initContainerDessinGraphePanel();
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
	}
	
	public void initEventListeners() {
		itemOuvrirFichier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fenetreOuvertureFichier = new JFileChooser(new File("."));
				File fichier = new File("C:\\Users\\Rafik\\Documents\\SAE\\sae_java_outil_aide_a_la_decision\\Graphe\\src\\fichiersGraphe\\");
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
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
	
	
	private void chargerNouveauFichier(File file) {
		Graphe.chargementFichier(file.getPath());
	}
	

	public static void mettreInvisibleComposant(){
		nomSommetSelectionneLabel.setText("");
		typeSommetSelectionneLabel.setText("");
		afficherCheminButton.setVisible(false);
		choixTypeCheminComboBox.setVisible(false);
		choixDestinationComboBox.setVisible(false);
		afficherVoisinsButton.setVisible(false);
		
	}
	public static void mettreVisibleComposant(String nom, String type){
		nomSommetSelectionneLabel.setText("Dispensaire "+nom);
		typeSommetSelectionneLabel.setText(type);
		afficherCheminButton.setVisible(true);
		choixTypeCheminComboBox.setVisible(true);
		choixDestinationComboBox.setVisible(true);
		afficherVoisinsButton.setVisible(true);
	
	}

	public static JButton getAfficherCheminButton() {
		return afficherCheminButton;
	}

	public static JComboBox<String> getChoixTypeCheminComboBox() {
		return choixTypeCheminComboBox;
	}
	public static JComboBox<String> getChoixDestinationComboBox() {
		return choixDestinationComboBox;
	}

	public static JButton getAfficherVoisinsButton() {
		return afficherVoisinsButton;
	}
}
