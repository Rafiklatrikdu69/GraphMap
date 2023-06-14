package Interface;

import Interface.InfosSommetPanel.AfficherCheminPanel;
import LCGraphe.Graphe;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;


import static javax.swing.JComponent.isLightweightComponent;
import static javax.swing.JOptionPane.showOptionDialog;

public class InterfaceGraphe extends JFrame {
	private CardLayout cardLayout;
	
	
	private String[] colonneIdentifiersChemin;
	
	public JPanel cp;
	
	private JPanel paneInfoSommet, panelInfoGraphe, panelToutesInfos;
	private JLabel titre;
	
	private AccueilPanel accueilPanel;
	private JPanel barreDeChargementPanel, nord;
	private List<String> listeSommetDjikstraChemin;
	
	private DessinGraphe graphePanel;
	
	private JLabel labelTitreVoisin;
	private DefaultTableModel modelInfosVoisins;
	private JTable tableInfosVoisins;
	
	private JScrollPane affichageVoisinScrollPane;
	
	private JButton boutonSuivant, boutonPrecedent;
	
	private JPanel contenuNomTypeSommetPanel;
	private JPanel contenuInfoSommetPanel;
	private JPanel affichageVoisinPanel;
	
	private JPanel contenuTousLesCheminsPanel;
	private AfficherCheminPanel tableCheminsPanel;
	private JPanel contenuButtonSuivPrec;
	
	private JPanel cardPanelInfos;
	
	private JLabel nombreRouteLabel, nombreSommetLabel, nombreMatLabel, nombreOpLabel, nombreCentreNutriLabel, nomSommetSelectionneLabel, typeSommetSelectionneLabel;
	private JButton afficherCheminButton;
	private JComboBox<String> choixTypeCheminComboBox, choixDestinationComboBox;
	private JMenuBar menu;
	private JRadioButtonMenuItem modeLight, modeDark;
	private JMenu itemFichier, itemFenetre, itemModeDuGraphe, itemFonctionnalite, itemChoixTheme, itemAjoutSommetArete;
	private JMenuItem itemFermerFenetre, itemOuvrirFichier, itemEnregisterGraphe;
	private JMenuItem itemAjoutArete, itemAjoutSommet, itemVoisin1Distance, itemVoisin2Distance, itemVoisinPDistance;
	static JRadioButton bloquerGraphe;
	private JProgressBar barreChargement;
	private int progress;
	private Timer timer;
	private Dimension screenSize;
	private LCGraphe.Graphe graphe;
	private Integer nombreRoutes;//pour plus tard
	private Integer nombreCentre;
	private JPanel contenuGraphePanel;
	
	private static final Color couleurFondPanelDarkMode = new Color(40, 40, 48);
	private static final Color couleurFondPanelLightMode = new Color(200, 200, 200);
	
	public InterfaceGraphe() {
		super();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		setSize(new Dimension(screenWidth / 2 + 300, screenHeight / 2 + 200));
		setPreferredSize(new Dimension(screenWidth / 2 + 300, screenHeight / 2 + 200));
		
		initComponents();
		mettreInvisibleComposantSommet();
		mettreInvisibleComposantGraphe();
		
		setTitle("Graphe");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		accueilPanel = new AccueilPanel(this);
		setContentPane(accueilPanel);
	}
	
	/**
	 *
	 */
	private void initComponents() {
		// la panel qui contient tout.
		cp = new JPanel();
		cp.setOpaque(false);
		cp.setLayout(new BorderLayout());
		
		// Le panel qui contient le graphe.
		contenuGraphePanel = new JPanel();
		contenuGraphePanel.setOpaque(false);
		contenuGraphePanel.setLayout(new BorderLayout());
		cp.add(contenuGraphePanel, BorderLayout.CENTER); // ajouter le panel dans le content panel au centre
		initBarreDeMenu(); // On initie la barre du haut avec Fichier, Fenetre, Mode du Graphe, Fonctionnalité
		initComposantsBarreDeChargement(); // On initie la panel qui contient la barre de chargement
		initContainerTousInfos(); // On initie le panel qui contient les infos du sommet selectionné qui est droite
		initEventListeners(); // On initie toutes les actions des boutons...
	}
	
	/**
	 *
	 */
	private void initContainerTousInfos() {
		panelInfoGraphe = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
			}
		};
		panelInfoGraphe.setOpaque(false);
		
		panelToutesInfos = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
			}
		};
		paneInfoSommet = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Dimension arcs = new Dimension(20, 20);
				int width = getWidth();
				int height = getHeight();
				Graphics2D graphics = (Graphics2D) g;
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setColor(couleurFondPanelLightMode);
				graphics.fillRoundRect(10, 10, width - 20, height - 20, arcs.width, arcs.height);
				graphics.drawRoundRect(10, 10, width - 20, height - 20, arcs.width, arcs.height);
			}
		}; // ce panel contient le card layout, le nom du sommet et son type.
		paneInfoSommet.setOpaque(false);
		//On met un minimum de dimension de ce panel pour qu'il est la place d'afficher le contenu
		panelToutesInfos.setPreferredSize(new Dimension((int) screenSize.getWidth() / 6, (int) screenSize.getHeight()));
		
		cardLayout = new CardLayout(); // ce card layout sert à changer de fenetre entre "Afficher les Voisins" et "Faire un Chemin"
		cardPanelInfos = new JPanel(cardLayout); // on met le card layout dans son panel
		cardPanelInfos.setOpaque(false);
		initContainerNomTypeSommet();
		
		//Panel Bouton
		contenuButtonSuivPrec = new JPanel(); // ce panel contient les boutons Suivant et Précédent pour changer de fenetre.
		contenuButtonSuivPrec.setOpaque(false);
		boutonSuivant = new JButton("Suivant"); // Suivant
		boutonPrecedent = new JButton("Précédent"); // Précédent
		contenuButtonSuivPrec.add(boutonPrecedent); // Ajout
		contenuButtonSuivPrec.add(boutonSuivant); // Ajout
		
		initContainersInfoSommet(); // Panel
		initContainersInfoGraphe();
		
		cardPanelInfos.add(contenuInfoSommetPanel, "panel principale");//panel principal
		cardPanelInfos.add(contenuTousLesCheminsPanel, "panel Suivant");//panel suivant pour les chemins
		paneInfoSommet.add(contenuNomTypeSommetPanel, BorderLayout.NORTH);
		paneInfoSommet.add(contenuButtonSuivPrec, BorderLayout.SOUTH);
		paneInfoSommet.add(cardPanelInfos, BorderLayout.CENTER);
		
		panelToutesInfos.add(panelInfoGraphe, BorderLayout.NORTH);
		panelToutesInfos.add(paneInfoSommet, BorderLayout.CENTER);
		
		cp.add(panelToutesInfos, BorderLayout.EAST);
	}
	
	private void initContainersInfoGraphe() {
		Dimension tmp = panelToutesInfos.getPreferredSize();
		panelInfoGraphe.setPreferredSize(new Dimension((int) tmp.getWidth(), (int) (tmp.getHeight() / 8)));
		titre = new JLabel("Graphe");//titre de l'interface
		titre.setHorizontalAlignment(JLabel.CENTER);//position au centre
		titre.setFont(new Font("Arial", Font.PLAIN, 25));
		/*Donnes initiales du graphe */
		nombreRouteLabel = new JLabel("");
		nombreSommetLabel = new JLabel("");
		nombreMatLabel = new JLabel("");
		nombreCentreNutriLabel = new JLabel("");
		nombreOpLabel = new JLabel("");
		nombreCentre = 0;
		nombreRoutes = 0;
		/****************************************************************/
		JPanel panelMilieu = new JPanel(new GridLayout(5, 1));
		panelMilieu.setOpaque(false);
		//ajout des elements dans le panel
		panelMilieu.add(nombreSommetLabel);
		panelMilieu.add(nombreRouteLabel);
		panelMilieu.add(nombreOpLabel);
		panelMilieu.add(nombreMatLabel);
		panelMilieu.add(nombreCentreNutriLabel);
		
		panelInfoGraphe.add(titre, BorderLayout.NORTH);
		panelInfoGraphe.add(panelMilieu, BorderLayout.CENTER);
		
		
	}
	
	private void initContainerNomTypeSommet() {
		nomSommetSelectionneLabel = new JLabel("");
		Font font = new Font("Arial", Font.PLAIN, 25);
		nomSommetSelectionneLabel.setFont(font);
		nomSommetSelectionneLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
		typeSommetSelectionneLabel = new JLabel("");
		
		contenuNomTypeSommetPanel = new JPanel(new GridBagLayout());
		contenuNomTypeSommetPanel.setOpaque(false);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weighty = 1.0;
		contenuNomTypeSommetPanel.add(nomSommetSelectionneLabel, constraints);
		
		constraints.gridy = 1;
		
		contenuNomTypeSommetPanel.add(typeSommetSelectionneLabel, constraints);
	}
	
	/**
	 *
	 */
	private void initContainersInfoSommet() {
		contenuInfoSommetPanel = new JPanel(new BorderLayout());
		contenuInfoSommetPanel.setOpaque(false);
		initContainerInfosVoisins();
		initContainerChemin();
	}
	
	/**
	 *
	 */
	private void initContainerInfosVoisins() {
		String[] colonneAttribut = {"Destination", "Distance", "Durée", "Fiabilité"};//tableau
		labelTitreVoisin = new JLabel("Liste des voisins");
		labelTitreVoisin.setHorizontalAlignment(SwingConstants.CENTER);
		modelInfosVoisins = new DefaultTableModel(colonneAttribut, 0);
		tableInfosVoisins = new JTable(modelInfosVoisins);
		tableInfosVoisins.setBorder(new EmptyBorder(0, 10, 0, 10));
		tableInfosVoisins.setOpaque(false);
		tableInfosVoisins.setGridColor(couleurFondPanelLightMode);
		
		affichageVoisinScrollPane = new JScrollPane(tableInfosVoisins);
		affichageVoisinScrollPane.setOpaque(false);
		
		
		affichageVoisinPanel = new JPanel(new BorderLayout());
		affichageVoisinPanel.setOpaque(false);
		affichageVoisinPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
		affichageVoisinPanel.add(labelTitreVoisin, BorderLayout.NORTH);
		affichageVoisinPanel.add(affichageVoisinScrollPane, BorderLayout.CENTER);
		
		contenuInfoSommetPanel.add(affichageVoisinPanel, BorderLayout.CENTER);
	}
	
	private void initContainerChemin() {
		tableCheminsPanel = new AfficherCheminPanel();
		afficherCheminButton = new JButton("Afficher chemin");
		/*Une combobox pour le choix du calcule de l'itineraire*/
		choixTypeCheminComboBox = new JComboBox<>();
		choixTypeCheminComboBox.addItem("Distance");
		choixTypeCheminComboBox.addItem("Durée");
		choixTypeCheminComboBox.addItem("Fiabilité");
		//Choix de la destination
		choixDestinationComboBox = new JComboBox<>();
		
		
		String choixChemin = (String) getChoixTypeCheminComboBox().getSelectedItem();
		if (choixChemin != null) {
			tableCheminsPanel.updateColonne(choixChemin);
		}
		
		nord = new JPanel(new GridLayout(2, 1));
		nord.setOpaque(false);
		
		nord.add(choixTypeCheminComboBox, BorderLayout.WEST);
		nord.add(choixDestinationComboBox, BorderLayout.EAST);
		nord.add(afficherCheminButton, BorderLayout.SOUTH);
		
		contenuTousLesCheminsPanel = new JPanel(new BorderLayout());
		contenuTousLesCheminsPanel.setOpaque(false);
		contenuTousLesCheminsPanel.add(nord, BorderLayout.NORTH);
		contenuTousLesCheminsPanel.add(tableCheminsPanel, BorderLayout.CENTER);
	}
	
	/**
	 * La methode permet d'initialiser le graphe en pernant en compte si il existe deja
	 */
	private void initContainerDessinGraphePanel() {
		contenuGraphePanel.removeAll();//supprime le contenu du graphe du base
		if (graphePanel == null) {
			//si il existe pas alors on le creer
			graphePanel = new DessinGraphe(graphe, this);
		} else {
			//sinon on le charge
			graphePanel.changerGraphe(graphe);
		}
		contenuGraphePanel.add(graphePanel, BorderLayout.CENTER);
	}
	
	/**
	 * Initialisation de la barre de menu avec les items
	 */
	private void initBarreDeMenu() {
		Font fontBarreMenu = new Font("Arial", Font.BOLD, 14);
		initialisationJMenu();
		initialisationJMenuItem();
		menu = new JMenuBar();
		bloquerGraphe = new JRadioButton("Bloquer");
		itemModeDuGraphe.add(bloquerGraphe);
		itemModeDuGraphe.setFont(fontBarreMenu);
		
		itemFichier.add(itemOuvrirFichier);
		itemFichier.add(itemEnregisterGraphe);
		itemFichier.setFont(fontBarreMenu);
		
		itemFenetre.add(itemChoixTheme);
		itemFenetre.add(itemFermerFenetre);
		itemFenetre.setFont(fontBarreMenu);
		
		ButtonGroup groupeEditGraphe = new ButtonGroup();
		groupeEditGraphe.add(itemAjoutSommet); // A FAIRE
		groupeEditGraphe.add(itemAjoutArete); // A FAIRE
		
		itemAjoutSommetArete.add(itemAjoutSommet);
		itemAjoutSommetArete.add(itemAjoutArete);
		
		itemFonctionnalite.add(itemAjoutSommetArete);
		itemFonctionnalite.add(itemVoisin1Distance);
		itemFonctionnalite.add(itemVoisin2Distance);
		itemFonctionnalite.add(itemVoisinPDistance);
		itemFonctionnalite.setFont(fontBarreMenu);
		
		ButtonGroup groupeLightDarkMode = new ButtonGroup();
		groupeLightDarkMode.add(modeLight);
		groupeLightDarkMode.add(modeDark);
		
		itemChoixTheme.add(modeLight);
		itemChoixTheme.add(modeDark);
		
		menu.add(itemFichier);
		menu.add(itemFenetre);
		menu.add(itemModeDuGraphe);
		menu.add(itemFonctionnalite);
	}
	
	/**
	 * Initialisation du menu avec les onglets
	 */
	private void initialisationJMenu() {
		itemFichier = new JMenu("Fichier");
		itemFenetre = new JMenu("Fenetre");
		itemChoixTheme = new JMenu("Theme");
		itemModeDuGraphe = new JMenu("Mode du Graphe");
		itemFonctionnalite = new JMenu("Fonctionnalitées");
		itemAjoutSommetArete = new JMenu("Modifier Graphe");
	}
	
	/**
	 * Initalisation des Items
	 */
	private void initialisationJMenuItem() {
		itemOuvrirFichier = new JMenuItem("Ouvrir");
		itemEnregisterGraphe = new JMenuItem("Enregistrer");
		itemFermerFenetre = new JMenuItem("Fermer");
		
		itemAjoutSommet = new JMenuItem("Ajouter Sommet");
		itemAjoutArete = new JMenuItem("Ajouter Arete");
		itemVoisin1Distance = new JMenuItem("Afficher voisin 1 distance");
		itemVoisin2Distance = new JMenuItem("Afficher voisin 2 distance");
		itemVoisinPDistance = new JMenuItem("Afficher voisin p distance");
		
		modeLight = new JRadioButtonMenuItem("Light", true);
		modeDark = new JRadioButtonMenuItem("Dark");
	}
	
	/**
	 * Initialisation de la barre de chargement
	 */
	private void initComposantsBarreDeChargement() {
		barreDeChargementPanel = new JPanel(new BorderLayout());
		barreDeChargementPanel.setOpaque(false);
		barreDeChargementPanel.setPreferredSize(new Dimension((int) screenSize.getWidth(), 45));
		cp.add(barreDeChargementPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Mise en route du timer
	 */
	public void demarrerChargement() {
		progress = 0;
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
	 * @param fichierCharge
	 */
	private void initialisationTimer(File fichierCharge) {
		barreDeChargementPanel.setVisible(true);
		contenuGraphePanel.removeAll();
		mettreInvisibleComposantSommet();
		mettreInvisibleComposantGraphe();
		graphe = new Graphe();
		timer = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				progress++;
				barreChargement.setValue(progress);
				
				if (progress == 100) {
					timer.stop();
					barreDeChargementPanel.setVisible(false);
					try {
						
						
						graphe.chargementFichier(fichierCharge.getPath());
					} catch (Exception excep) {
					
					}
					mettreVisibleComposantGraphe();
					initContainerDessinGraphePanel();
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
	}
	
	/**
	 * Ecouteurs sur les actions de l'utilisateurs
	 */
	public void initEventListeners() {
		boutonSuivant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.previous(cardPanelInfos);
			}//passe au panel suivant
		});
		boutonPrecedent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.next(cardPanelInfos);
			}//passe au panel precedent
		});
		itemOuvrirFichier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setOuvrirFichierActions();
			}//ouvre le fichier
			
		});
		itemFermerFenetre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}//ferme la fenetre
		});
		modeLight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Changer le thème en utilisant le nom de classe du look and feel
					UIManager.setLookAndFeel(new FlatMacLightLaf());//theme blanc
					
					// Mettre à jour les composants de la fenêtre pour refléter le nouveau thème
					
					updateInterface();
					if (graphePanel != null) {
						graphePanel.setCouleurTexteSommet(Color.WHITE);
						graphePanel.setDefautCouleurSommet(Color.BLACK);
						graphePanel.setCouleurSommetSelect(Color.BLUE);
						graphePanel.setCouleurArete(Color.BLACK);
						graphePanel.miseAJourDessin();
						
						tableInfosVoisins.setGridColor(couleurFondPanelLightMode);
						//bug le chemin s'affiche tout le temps quand on change le theme
						if (graphePanel.getMisAjourAutoriseFloydWarsall() == true) {
							graphePanel.setMisAjourAutoriseFloydWarshall(false);
							graphePanel.colorCheminFiabilite();
							
						} else if (graphePanel.getMisAjourAutoriseDjikstra() == true) {
							graphePanel.setMisAjourAutoriseDjikstra(false);
							graphePanel.colorCheminDjikstra();
						}
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
					UIManager.setLookAndFeel(new FlatMacDarkLaf());//theme noir
					
					// Mettre à jour les composants de la fenêtre pour refléter le nouveau thème
					
					updateInterface();//mise a jour de l'interface
					if (graphePanel != null) {
						tableInfosVoisins.setGridColor(couleurFondPanelDarkMode);
						graphePanel.setCouleurTexteSommet(Color.BLACK);
						graphePanel.setDefautCouleurSommet(new Color(225, 225, 225));
						graphePanel.setCouleurSommetSelect(new Color(98, 98, 98));
						graphePanel.setCouleurArete(new Color(154, 141, 141));
						graphePanel.miseAJourDessin();
						if (graphePanel.getMisAjourAutoriseFloydWarsall()) {
							graphePanel.setMisAjourAutoriseFloydWarshall(false);
							graphePanel.colorCheminFiabilite();
							
						} else if (graphePanel.getMisAjourAutoriseDjikstra()) {
							graphePanel.setMisAjourAutoriseDjikstra(false);
							graphePanel.colorCheminDjikstra();
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		choixTypeCheminComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colonneIdentifiersChemin = new String[]{"Chemin", (String) choixTypeCheminComboBox.getSelectedItem()};//type de recherche de chemin
			}
		});
		afficherCheminButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//affichage du chemin
				if (graphePanel.getListeArreteChemin() != null && graphePanel.getListeSommetChemin() != null) {
					
					graphePanel.resetColorArreteChemin();
					graphePanel.resetColorSommetChemin();
					
					
					repaint();
				}
				setActionListenerToAfficherChemins();
				
			}
		});
		itemAjoutSommet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				creationDuSommet();
			}
		});
		
		
		//////////////////////////////////////////////////////////////////////////////////////
		
		
	}
	
	/**
	 * Creation de la Joption Pane
	 */
	private void creationDuSommet() {
		AjoutSommetVisuelCreation ajout = new AjoutSommetVisuelCreation();
		int choix = JOptionPane.showOptionDialog(this, ajout, "Ajouter Centre", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		if (choix == JOptionPane.OK_OPTION) {
		
		
		}
	}
	
	/**
	 * Ouverture du fichier
	 */
	public void setOuvrirFichierActions() {
		JFileChooser fenetreOuvertureFichier = new JFileChooser(new File("."));
		fenetreOuvertureFichier.setFileFilter(new FileNameExtensionFilter("Fichiers", "csv"));//seul les format csv sont autorises et proposer
		File fichier = new File("..\\src\\fichiersGraphe\\");//tombe directement sur le dossier voulu
		fenetreOuvertureFichier.setCurrentDirectory(fichier);
		if (fenetreOuvertureFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			fichier = fenetreOuvertureFichier.getSelectedFile();//recupere le choix du fichier
			System.out.println(fichier.getPath());//affiche le chemin
			if (fichier.getPath().endsWith(".csv")) {//option en plus pour verifier si on ouvre le bon fichier
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				initialisationBarreDeChargement();
				
				initialisationTimer(fichier);
				
				// Appel de demarrerChargement()
				demarrerChargement();
			} else {
				JPanel panelFormatFichierInvalide = new JPanel();
				int result = showOptionDialog(null, panelFormatFichierInvalide, "Format fichier invalide ! ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				
			}
		}
	}
	
	private void updateInterface() {
		SwingUtilities.updateComponentTreeUI(this);//met a jour l'interface
	}
	
	
	// Cette méthode permet de définir un écouteur d'action pour afficher les chemins.
	private void setActionListenerToAfficherChemins() {
		String selectChoixChemin = (String) choixTypeCheminComboBox.getSelectedItem(); // Récupère le choix du type de chemin sélectionné dans une ComboBox
		Graphe.MaillonGraphe sommetSelect = graphePanel.getSommetSelectionne(); // Obtient le sommet sélectionné à partir du panneau du graphe
		String destination = (String) choixDestinationComboBox.getSelectedItem(); // Récupère la destination sélectionnée dans une ComboBox
		
		if (selectChoixChemin.equals((ChoixTypeChemin.FIABILITE.getAttribut()))) { // Vérifie si le choix du chemin est la fiabilité
			graphe.rechercheChemin(sommetSelect.getNom(), destination); // Effectue la recherche du chemin de plus grande fiabilité entre le sommet sélectionné et la destination
			tableCheminsPanel.resetTable(); // Réinitialise le tableau des chemins
			tableCheminsPanel.updateColonne(selectChoixChemin); // Met à jour la colonne du tableau avec le choix du chemin
			tableCheminsPanel.addDataInTable("Depart", String.valueOf(graphe.getSommetDonnees().get(0) * 100 + " %")); // Ajoute les données de départ (fiabilité du sommet de départ) au tableau
			double fiabiliteTotale = 1.0; // Initialise la fiabilité totale à 1.0
			
			for (String i : graphe.getListeSommetCheminGraphe()) { // Parcourt les sommets du chemin
				//System.out.println("sommet : " + i);
				
				if (!i.equals(sommetSelect.getNom())) { // Vérifie si le sommet n'est pas le sommet de départ
					
					Double donneeSommet = graphe.getSommetDonnees().get(graphe.getListeSommetCheminGraphe().indexOf(i)); // Obtient la fiabilité du sommet
					tableCheminsPanel.addDataInTable(i, String.valueOf(donneeSommet * 100) + " %"); // Ajoute les données de fiabilité du sommet au tableau
					fiabiliteTotale *= donneeSommet; // Calcule la fiabilité totale en multipliant les fiabilités de chaque sommet
				}
			}
			
			tableCheminsPanel.addDataInTable("Fiabilité totale", String.valueOf(Math.round(fiabiliteTotale * 100 * 100) / 100) + " %"); // Ajoute la fiabilité totale au tableau
			graphePanel.colorCheminFiabilite(); // Colorie le chemin de plus grande fiabilité sur le graphe
			repaint(); // Redessine le graphe
		}
		
		if (selectChoixChemin.equals((ChoixTypeChemin.DISTANCE.getAttribut()))) { // Vérifie si le choix du chemin est la distance
			tableCheminsPanel.resetTable(); // Réinitialise le tableau des chemins
			listeSommetDjikstraChemin = new ArrayList<>(); // Initialise la liste des sommets du chemin selon l'algorithme de Dijkstra
			listeSommetDjikstraChemin.add(sommetSelect.getNom()); // Ajoute le sommet de départ à la liste des sommets du chemin
			tableCheminsPanel.updateColonne(selectChoixChemin); // Met à jour la colonne du tableau avec le choix du chemin
			LinkedHashMap<String, Double> chemin = graphe.getCheminDijkstra().get(sommetSelect.getNom()).getCheminsDistanceTo(destination); // Obtient le chemin le plus court en termes de distance entre le sommet de départ et la destination
			
			if (chemin != null) { // Vérifie si un chemin existe
				int distanceTotale = 0; // Initialise la distance totale à 0
				
				for (Map.Entry<String, Double> entry : chemin.entrySet()) { // Parcourt les sommets du chemin
					String nomSommet = entry.getKey(); // Obtient le nom du sommet
					double distance = entry.getValue(); // Obtient la distance entre les sommets
					distanceTotale += (int) distance; // Calcule la distance totale en ajoutant la distance actuelle
					
					if (nomSommet.equals(sommetSelect.getNom())) { // Vérifie si le sommet est le sommet de départ
						tableCheminsPanel.addDataInTable("Départ (" + nomSommet + ")", distanceTotale + "Km"); // Ajoute les données du sommet de départ au tableau
					} else if (nomSommet.equals(destination)) { // Vérifie si le sommet est la destination
						listeSommetDjikstraChemin.add(nomSommet); // Ajoute le sommet à la liste des sommets du chemin
						tableCheminsPanel.addDataInTable("Arrivé (" + nomSommet + ")", distanceTotale + "Km"); // Ajoute les données du sommet d'arrivée au tableau
					} else {
						listeSommetDjikstraChemin.add(nomSommet); // Ajoute le sommet à la liste des sommets du chemin
						tableCheminsPanel.addDataInTable(nomSommet, distanceTotale + "Km"); // Ajoute les données du sommet au tableau
					}
				}
			} else {
				tableCheminsPanel.addDataInTable(sommetSelect.getNom() + " -> " + destination, "Aucun Chemin"); // Ajoute un message indiquant qu'aucun chemin n'existe
			}
			
			graphePanel.colorCheminDjikstra(); // colorie le chemin le plus court sur le graphe selon l'algorithme de Dijkstra
			repaint(); // redessine le graphe
		} else if (selectChoixChemin.equals((ChoixTypeChemin.DUREE.getAttribut()))) { // Vérifie si le choix du chemin est la durée
			
			tableCheminsPanel.resetTable(); // Reinitialise le tableau des chemins
			listeSommetDjikstraChemin = new ArrayList<>(); // Initialise la liste des sommets du chemin selon l'algorithme de Dijkstra
			listeSommetDjikstraChemin.add(sommetSelect.getNom()); // Ajoute le sommet de départ à la liste des sommets du chemin
			tableCheminsPanel.updateColonne(selectChoixChemin); // Met à jour la colonne du tableau avec le choix du chemin
			LinkedHashMap<String, Double> chemin = graphe.getCheminDijkstra().get(sommetSelect.getNom()).getCheminsDureeTo(destination); // Obtient le chemin le plus court en termes de durée entre le sommet de départ et la destination
			
			if (chemin != null) { // Verifie si un chemin existe
				int dureeTotale = 0; // Initialise la durée totale à 0
				
				for (Map.Entry<String, Double> entry : chemin.entrySet()) { // Parcourt les sommets du chemin
					String nomSommet = entry.getKey(); // Obtient le nom du sommet
					double duree = entry.getValue(); // Obtient la durée entre les sommets
					dureeTotale += (int) duree; // Calcule la durée totale en ajoutant la durée actuelle
					
					if (!nomSommet.equals(sommetSelect.getNom())) { // Vérifie si le sommet n'est pas le sommet de départ
						listeSommetDjikstraChemin.add(nomSommet); // Ajoute le sommet à la liste des sommets du chemin
						tableCheminsPanel.addDataInTable(nomSommet, dureeTotale + " min"); // Ajoute les données du sommet au tableau
					} else {
						tableCheminsPanel.addDataInTable("Départ", dureeTotale + " min"); // Ajoute les données du sommet de départ au tableau
					}
				}
				
				tableCheminsPanel.addDataInTable("Durée Totale", dureeTotale + " min"); // Ajoute la durée totale au tableau
			} else {
				tableCheminsPanel.addDataInTable(sommetSelect.getNom() + " -> " + destination, "Aucun Chemin"); // Ajoute un message indiquant qu'aucun chemin n'existe
			}
			
			graphePanel.colorCheminDjikstra(); // Colorie le chemin le plus court sur le graphe selon l'algorithme de Dijkstra
			repaint(); // Redessine le graphe
		}
	}
	
	/**
	 *
	 */
	public void mettreInvisibleComposantSommet() {
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
	private void mettreInvisibleComposantGraphe() {
		//Visbilite des composants
		titre.setVisible(false);
		nombreSommetLabel.setText("");
		nombreRouteLabel.setText("");
		nombreOpLabel.setText("");
		nombreMatLabel.setText("");
		nombreCentreNutriLabel.setText("");
		itemModeDuGraphe.setEnabled(false);
		itemFonctionnalite.setEnabled(false);
		itemChoixTheme.setEnabled(false);
	}
	
	/**
	 *
	 */
	private void mettreVisibleComposantGraphe() {
		//Visibilite des composants lier au graphe
		titre.setVisible(true);
		itemModeDuGraphe.setEnabled(true);
		itemFonctionnalite.setEnabled(true);
		itemChoixTheme.setEnabled(true);
		nombreSommetLabel.setText("Dispensaires : " + graphe.getNombreDispensaire());
		nombreRouteLabel.setText("Routes : " + graphe.getNombreRoute());
		nombreOpLabel.setText("Opératoires : " + graphe.getNombreOperatoire());
		nombreMatLabel.setText("Maternités : " + graphe.getNombreMaternite());
		nombreCentreNutriLabel.setText("Centres de nutrition : " + graphe.getNombreCentreNutrition());
	}
	
	/**
	 * @param nom
	 * @param type
	 */
	public void mettreVisibleComposantSommet(String nom, String type) {
		//initialisation des composants lier au sommet
		nomSommetSelectionneLabel.setText("Dispensaire " + nom);
		typeSommetSelectionneLabel.setText(type);
		itemModeDuGraphe.setEnabled(true);
		itemFonctionnalite.setEnabled(true);
		itemChoixTheme.setEnabled(true);
		boutonSuivant.setVisible(true);
		boutonPrecedent.setVisible(true);
		afficherCheminButton.setVisible(true);
		choixTypeCheminComboBox.setVisible(true);
		choixDestinationComboBox.setVisible(true);
		affichageVoisinPanel.setVisible(true);
	}
	
	public void setNotSelectAuNom() {
		nomSommetSelectionneLabel.setText("N/A");
	}
	
	/**
	 * @return
	 */
	public JButton getAfficherCheminButton() {
		return afficherCheminButton;
	}
	
	/**
	 * @return
	 */
	public JComboBox<String> getChoixTypeCheminComboBox() {
		return choixTypeCheminComboBox;
	}
	
	/**
	 * @return
	 */
	public JComboBox<String> getChoixDestinationComboBox() {
		return choixDestinationComboBox;
	}
	
	public DefaultTableModel getModelInfosVoisins() {
		return modelInfosVoisins;
	}
	
	public JPanel getContenuGraphePanel() {
		return contenuGraphePanel;
	}
	
	public JPanel getCp() {
		return cp;
	}
	
	public JMenuBar getMenu() {
		return menu;
	}
	
	public boolean getBloquerGraphe() {
		return bloquerGraphe.isSelected();
	}
	
	public List<String> getListeSommetDjikstraChemin() {
		return this.listeSommetDjikstraChemin;
	}
	
	public JMenuItem getItemOuvrirFichier() {
		return itemOuvrirFichier;
	}
	
}
