import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static javax.swing.JOptionPane.showOptionDialog;

public class InterfaceGraphe extends JFrame {
	private JFrame fenetrePrincipale; // la fenetre
	
	public static JPanel cp;
	private JPanel graphePanel;
	private AccueilPanel accueilPanel;
	private JPanel barreDeChargementPanel;
	
	private static JPanel contenuInfoSommetPanel;
	
	 static JMenuBar menu;
	private JMenu itemFichier, itemFenetre, itemFonctionnalites, itemOptionFonction;
	private  JMenuItem itemOuvrirFichier;
    private JMenuItem itemFermerFenetre;
    private JMenuItem itemMenuPrincipale;
    private static JMenuItem itemFonction;
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
	 static boolean  selected = false; ;
	
	public InterfaceGraphe() {
		super();
		Graphe = new LCGraphe();
		fenetrePrincipale = this;
		accueilPanel = new AccueilPanel(fenetrePrincipale);
		setContentPane(accueilPanel);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		setSize(new Dimension(screenWidth / 2, screenHeight / 2 + 200));
		
		initComponents();
		
		setTitle("Graphe");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void initContainerDessinGraphePanel() {
		graphePanel = new DessinGraphe();
		contenuGraphePanel.setBorder(BorderFactory.createTitledBorder("Graphe dessiner"));
		contenuGraphePanel.add(graphePanel, BorderLayout.CENTER);
	}
	
	private void initComposantsInfoSommetPanel() {
		contenuInfoSommetPanel = new JPanel();
		contenuInfoSommetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		contenuInfoSommetPanel.setBorder(BorderFactory.createTitledBorder("Info"));
		contenuInfoSommetPanel.setPreferredSize(new Dimension((int) screenSize.getWidth(), 45));
		cp.add(contenuInfoSommetPanel, BorderLayout.NORTH);
	}
	
	private void initComposantsBarreDeChargement() {
		barreDeChargementPanel = new JPanel();
		barreDeChargementPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		barreDeChargementPanel.setPreferredSize(new Dimension((int) screenSize.getWidth(), 45));
		barreDeChargementPanel.setBorder(BorderFactory.createTitledBorder("Barre de chargement"));
		cp.add(barreDeChargementPanel, BorderLayout.SOUTH);
	}
	
	
	private void initComponents() {
		cp = new JPanel();
		cp.setLayout(new BorderLayout());
		
		menu = new JMenuBar();
		itemFichier = new JMenu("Fichier");
		itemFenetre = new JMenu("Fenetre");
		itemFonctionnalites = new JMenu("Mode du Graphe");
		itemOptionFonction = new JMenu("Fonctionnalitées");
		
		// Menu
		itemOuvrirFichier = new JMenuItem("Ouvrir");
		itemFermerFenetre = new JMenuItem("Fermer");
		itemMenuPrincipale = new JMenuItem("Menu Principale");
		itemFonction = new JMenuItem("Information Sommet");
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
		itemOptionFonction.add(itemFonction);
  
		menu.add(itemFichier);
		menu.add(itemFenetre);
		menu.add(itemFonctionnalites);
		menu.add(itemOptionFonction);
		contenuGraphePanel = new JPanel();
		contenuGraphePanel.setLayout(new BorderLayout());
		contenuGraphePanel.setBorder(BorderFactory.createTitledBorder("Graphe"));
		cp.add(contenuGraphePanel, BorderLayout.CENTER);
		
		initComposantsBarreDeChargement();
		initComposantsInfoSommetPanel();
		initEventListeners();
	}
	
	public void demarrerChargement() {
		progresse = 0;
		timer.start();
	}
	private  void initialisationBarreDeChargement(){
		barreDeChargementPanel.removeAll();
		barreChargement = new JProgressBar(0, 100);
		barreDeChargementPanel.add(barreChargement);
		barreChargement.setStringPainted(true);
	}
	private void initialisationTimer(File fichierCharge){
		timer = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				progresse++;
				barreChargement.setValue(progresse);
				
				if (progresse == 100) {
					timer.stop();
					
					Graphe = new LCGraphe();
					contenuGraphePanel.removeAll();
					System.out.println(Graphe.existeCentre("S1"));
					chargerNouveauFichier(fichierCharge);
					initContainerDessinGraphePanel();
					
					
					barreChargement.setVisible(false);
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
					if (fichier.getPath().endsWith(".csv")) {
						setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						initialisationBarreDeChargement();
						File finalFichier = fichier;
						initialisationTimer(finalFichier);
						demarrerChargement();
					} else {
						JPanel panelMauvaisFormat = new JPanel();
						int result = showOptionDialog(null, panelMauvaisFormat, "Format de fichier interdit !", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
						
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
	static boolean getOptionFonction() {
		itemFonction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = itemFonction.isSelected();
				System.out.println(selected);
				selected = true;
				
			}
		});
		return selected;
	}
	static void setContenuInfoSommetPanel(JLabel nom,JLabel type){
		contenuInfoSommetPanel.add(nom,BorderLayout.NORTH);
		contenuInfoSommetPanel.add(type,BorderLayout.SOUTH);
	}
	static JPanel getPanelInfoSommet(){
		return  contenuInfoSommetPanel;
	}

	
}
