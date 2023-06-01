import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class InterfaceGraphe extends JFrame {
	private JFrame fenetrePrincipale; // la fenetre
	
	public static JPanel cp;
	
	private AccueilPanel accueilPanel;
	private JPanel barreDeChargementPanel, graphePanel;

	private JPanel contenuTousInfosPanel, contenutInfoGraphePanel, contenuInfoSommetPanel;
	private JPanel contenuNomTypeSommetPanel, contenuFonctionnalitePanel, contenuTousLesCheminsPanel, contenuAutrePanel;

	private static JLabel nombreRouteLabel, nombreSommetLabel, nomSommetSelectionneLabel, typeSommetSelectionneLabel;
	private static JButton afficherCheminButton, afficherVoisinsButton;
	private static JComboBox<String> choixTypeCheminComboBox, choixDestinationComboBox;

	private static JScrollPane contenuInfosJScrollPane;
	private static JTextArea contenuInfosTextArea;
	
	static JMenuBar menu;
	private JMenu itemFichier, itemFenetre, itemFonctionnalites, itemOptionFonction;
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
		//contenutInfoGraphePanel.setBorder(BorderFactory.createTitledBorder("Info Graphe"));
		contenutInfoGraphePanel.setOpaque(false);

		initContainerInfoSommet();

		contenuTousInfosPanel.add(contenutInfoGraphePanel, BorderLayout.NORTH);
		contenuTousInfosPanel.add(contenuInfoSommetPanel, BorderLayout.CENTER);

		cp.add(contenuTousInfosPanel, BorderLayout.EAST);
	}

	private void initContainerInfoSommet(){
		contenuInfoSommetPanel = new JPanel(new BorderLayout());
		contenuInfoSommetPanel.setOpaque(false);
		//contenuInfoSommetPanel.setBorder(BorderFactory.createTitledBorder("Info Sommet Select"));

		contenuNomTypeSommetPanel = new JPanel(new GridBagLayout());
		contenuNomTypeSommetPanel.setOpaque(false);
		contenuFonctionnalitePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		contenuFonctionnalitePanel.setOpaque(false);
		contenuTousLesCheminsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		contenuTousLesCheminsPanel.setOpaque(false);
		contenuAutrePanel = new JPanel(new GridLayout(10,1));
		contenuAutrePanel.setOpaque(false);

		contenuInfosTextArea = new JTextArea();
		contenuInfosTextArea.setText("smldfkjghdiuyfgbhfdsghsdfjvngbsdkhn,gbkrdeuhbgdkbvsdjkbvdfsjhkbgdskjubngdskjhnbvgfcdskvbgfckjhbdskjhugfb");
		contenuInfosTextArea.setEditable(false);
		contenuInfosJScrollPane = new JScrollPane(contenuInfosTextArea);
		contenuInfosJScrollPane.setVisible(false);


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

		contenutInfoGraphePanel.add(nombreSommetLabel);
		contenutInfoGraphePanel.add(nombreRouteLabel);

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

		contenuFonctionnalitePanel.add(afficherVoisinsButton);

		contenuTousLesCheminsPanel.add(choixTypeCheminComboBox);
		contenuTousLesCheminsPanel.add(choixDestinationComboBox);
		contenuTousLesCheminsPanel.add(afficherCheminButton);

		contenuAutrePanel.add(contenuFonctionnalitePanel);
		contenuAutrePanel.add(contenuTousLesCheminsPanel);
		contenuAutrePanel.add(contenuInfosJScrollPane);

		contenuInfoSommetPanel.add(contenuNomTypeSommetPanel, BorderLayout.NORTH);
		contenuInfoSommetPanel.add(contenuAutrePanel, BorderLayout.CENTER);
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
		itemFonctionnalites = new JMenu("Mode du Graphe");
		itemOptionFonction = new JMenu("Fonctionnalitées");
	}
	private void initialisationJMenuItem(){
		itemOuvrirFichier = new JMenuItem("Ouvrir");
		itemFermerFenetre = new JMenuItem("Fermer");
		itemMenuPrincipale = new JMenuItem("Menu Principale");
		itemMenuPrincipale.setEnabled(false);
		itemAfficherCheminPlusCourts = new JMenuItem("Calculer Itineraire");
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
		
		itemFenetre.add(itemFermerFenetre);
		itemFenetre.add(itemMenuPrincipale);
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
		contenuTousInfosPanel.setBorder(null);
		contenuTousInfosPanel.setBackground(null);
		mettreInvisibleComposant();
		timer = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				progresse++;
				barreChargement.setValue(progresse);
				if (progresse == 100) {
					timer.stop();
					Graphe = new LCGraphe();
					contenuTousInfosPanel.setBackground(Color.YELLOW);
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
				File fichier = new File("src/fichiersGraphe");
				fenetreOuvertureFichier.setCurrentDirectory(fichier);
				if (fenetreOuvertureFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fichier = fenetreOuvertureFichier.getSelectedFile();
					if (fichier.getPath().endsWith(".csv")) {
						setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						initialisationBarreDeChargement();
						initialisationTimer(fichier);
						demarrerChargement();
					} else {
						String messageErreur = "Format invalide !";
						JOptionPane.showMessageDialog(cp, messageErreur, "Erreur", JOptionPane.ERROR_MESSAGE);
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
		contenuInfosJScrollPane.setVisible(false);
	}
	public static void mettreVisibleComposant(String nom, String type){
		nomSommetSelectionneLabel.setText("Dispensaire "+nom);
		typeSommetSelectionneLabel.setText(type);
		afficherCheminButton.setVisible(true);
		choixTypeCheminComboBox.setVisible(true);
		choixDestinationComboBox.setVisible(true);
		afficherVoisinsButton.setVisible(true);
		contenuInfosJScrollPane.setVisible(true);
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
