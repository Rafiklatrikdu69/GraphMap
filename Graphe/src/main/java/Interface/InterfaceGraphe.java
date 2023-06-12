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


import static javax.swing.JOptionPane.showOptionDialog;

public class InterfaceGraphe extends JFrame {
	private CardLayout cardLayout;
	private InterfaceGraphe fenetrePrincipale; // la fenetre
	
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
	private Integer nombreRoutes;
	private Integer nombreCentre;
	private JPanel contenuGraphePanel;


	
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
		cp.setLayout(new BorderLayout());
		
		// Le panel qui contient le graphe.
		contenuGraphePanel = new JPanel();
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
		panelInfoGraphe = new JPanel(new BorderLayout());
		panelToutesInfos = new JPanel(new BorderLayout());
		paneInfoSommet = new JPanel(new BorderLayout()); // ce panel contient le card layout, le nom du sommet et son type.
		//On met un minimum de dimension de ce panel pour qu'il est la place d'afficher le contenu
		panelToutesInfos.setPreferredSize(new Dimension((int) screenSize.getWidth() / 6, (int) screenSize.getHeight()));
		
		cardLayout = new CardLayout(); // ce card layout sert à changer de fenetre entre "Afficher les Voisins" et "Faire un Chemin"
		cardPanelInfos = new JPanel(cardLayout); // on met le card layout dans son panel
		
		initContainerNomTypeSommet();
		
		//Panel Bouton
		contenuButtonSuivPrec = new JPanel(); // ce panel contient les boutons Suivant et Précédent pour changer de fenetre.
		boutonSuivant = new JButton("Suivant"); // Suivant
		boutonPrecedent = new JButton("Précédent"); // Précédent
		contenuButtonSuivPrec.add(boutonPrecedent); // Ajout
		contenuButtonSuivPrec.add(boutonSuivant); // Ajout
		
		initContainersInfoSommet(); // Panel
		initContainersInfoGraphe();
		
		cardPanelInfos.add(contenuInfoSommetPanel, "panel de base");
		cardPanelInfos.add(contenuTousLesCheminsPanel, "panel Suivant");
		paneInfoSommet.add(contenuNomTypeSommetPanel, BorderLayout.NORTH);
		paneInfoSommet.add(contenuButtonSuivPrec, BorderLayout.SOUTH);
		paneInfoSommet.add(cardPanelInfos, BorderLayout.CENTER);

		panelToutesInfos.add(panelInfoGraphe, BorderLayout.NORTH);
		panelToutesInfos.add(paneInfoSommet, BorderLayout.CENTER);

		cp.add(panelToutesInfos, BorderLayout.EAST);
	}

	private void initContainersInfoGraphe(){
		Dimension tmp = panelToutesInfos.getPreferredSize();
		panelInfoGraphe.setPreferredSize(new Dimension((int) tmp.getWidth(), (int) (tmp.getHeight()/8)));
		titre = new JLabel("Graphe");
		titre.setHorizontalAlignment(JLabel.CENTER);
		titre.setFont(new Font("Arial", Font.PLAIN, 25));
		nombreRouteLabel = new JLabel("");
		nombreSommetLabel = new JLabel("");
		nombreMatLabel = new JLabel("");
		nombreCentreNutriLabel = new JLabel("");
		nombreOpLabel = new JLabel("");
		nombreCentre = 0;
		nombreRoutes = 0;
		JPanel mid = new JPanel(new GridLayout(5,1));
		mid.add(nombreSommetLabel);
		mid.add(nombreRouteLabel);
		mid.add(nombreOpLabel);
		mid.add(nombreMatLabel);
		mid.add(nombreCentreNutriLabel);
		panelInfoGraphe.add(titre, BorderLayout.NORTH);
		panelInfoGraphe.add(mid,BorderLayout.CENTER);


	}

	private void initContainerNomTypeSommet() {
		nomSommetSelectionneLabel = new JLabel("");
		Font font = new Font("Arial", Font.PLAIN, 25);
		nomSommetSelectionneLabel.setFont(font);
		typeSommetSelectionneLabel = new JLabel("");
		
		contenuNomTypeSommetPanel = new JPanel(new GridBagLayout());
		
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
		initContainerInfosVoisins();
		initContainerChemin();
	}
	
	/**
	 *
	 */
	private void initContainerInfosVoisins() {
		String[] colonneAttribut = {"Destination", "Distance", "Durée", "Fiabilité"};
		labelTitreVoisin = new JLabel("Liste des voisins");
		labelTitreVoisin.setHorizontalAlignment(SwingConstants.CENTER);
		modelInfosVoisins = new DefaultTableModel(colonneAttribut, 0);
		tableInfosVoisins = new JTable(modelInfosVoisins);
		
		affichageVoisinScrollPane = new JScrollPane(tableInfosVoisins);
		
		affichageVoisinPanel = new JPanel(new BorderLayout());
		affichageVoisinPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		affichageVoisinPanel.add(labelTitreVoisin, BorderLayout.NORTH);
		affichageVoisinPanel.add(affichageVoisinScrollPane, BorderLayout.CENTER);
		
		contenuInfoSommetPanel.add(affichageVoisinPanel, BorderLayout.CENTER);
	}
	
	private void initContainerChemin() {
		tableCheminsPanel = new AfficherCheminPanel();
		afficherCheminButton = new JButton("Afficher chemin");
		
		choixTypeCheminComboBox = new JComboBox<>();
		choixTypeCheminComboBox.addItem("Distance");
		choixTypeCheminComboBox.addItem("Durée");
		choixTypeCheminComboBox.addItem("Fiabilité");
		
		choixDestinationComboBox = new JComboBox<>();
		
		
		String choixChemin = (String) getChoixTypeCheminComboBox().getSelectedItem();
		if (choixChemin != null) {
			tableCheminsPanel.updateColonne(choixChemin);
		}
		
		nord = new JPanel(new GridLayout(2, 1));
		
		nord.add(choixTypeCheminComboBox, BorderLayout.WEST);
		nord.add(choixDestinationComboBox, BorderLayout.EAST);
		nord.add(afficherCheminButton, BorderLayout.SOUTH);
		
		contenuTousLesCheminsPanel = new JPanel(new BorderLayout());
		contenuTousLesCheminsPanel.add(nord, BorderLayout.NORTH);
		contenuTousLesCheminsPanel.add(tableCheminsPanel, BorderLayout.CENTER);
	}
	
	/**
	 *
	 */
	private void initContainerDessinGraphePanel() {
		contenuGraphePanel.removeAll();
		if (graphePanel == null) {
			graphePanel = new DessinGraphe(graphe, this);
			graphePanel.setBorder(BorderFactory.createTitledBorder("Graphe"));
			panelToutesInfos.setBorder(BorderFactory.createTitledBorder("Infos"));
		} else {
			graphePanel.changerGraphe(graphe);
		}
		contenuGraphePanel.add(graphePanel, BorderLayout.CENTER);
	}
	
	/**
	 *
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
	 *
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
	 *
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
	
	///////////////////////////////////////////////////////////////////////////////////
	
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
					graphe.chargementFichier(fichierCharge.getPath());
					mettreVisibleComposantGraphe();
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
				setOuvrirFichierActions();
			}
			
		});
		itemFermerFenetre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		modeLight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Changer le thème en utilisant le nom de classe du look and feel
					UIManager.setLookAndFeel(new FlatMacLightLaf());
					
					// Mettre à jour les composants de la fenêtre pour refléter le nouveau thème
					
					updateInterface();
					if (graphePanel != null) {
						graphePanel.setCouleurTexteSommet(Color.WHITE);
						graphePanel.setDefautCouleurSommet(Color.BLACK);
						graphePanel.setCouleurSommetSelect(Color.BLUE);
						graphePanel.setCouleurArete(Color.BLACK);
						graphePanel.miseAJourDessin();
						
						if (graphePanel.getMisAjourAutoriseFloydWarsall() == true) {
							graphePanel.setMisAjourAutoriseFloydWarshall(false);
							graphePanel.colorCheminFiabilite();
							
						}
						else if(graphePanel.getMisAjourAutoriseDjikstra()==true){
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
					UIManager.setLookAndFeel(new FlatMacDarkLaf());
					
					// Mettre à jour les composants de la fenêtre pour refléter le nouveau thème
					
					updateInterface();
					if (graphePanel != null) {
						graphePanel.setCouleurTexteSommet(Color.BLACK);
						graphePanel.setDefautCouleurSommet(new Color(225,225,225));
						graphePanel.setCouleurSommetSelect(new Color(98,98,98));
						graphePanel.setCouleurArete(new Color(154,141,141));
						graphePanel.miseAJourDessin();
						if (graphePanel.getMisAjourAutoriseFloydWarsall()) {
							graphePanel.setMisAjourAutoriseFloydWarshall(false);
							graphePanel.colorCheminFiabilite();
							
						}
						else if(graphePanel.getMisAjourAutoriseDjikstra()){
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
				colonneIdentifiersChemin = new String[]{"Chemin", (String) choixTypeCheminComboBox.getSelectedItem()};
			}
		});
		afficherCheminButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (graphePanel.getListeArreteChemin() != null && graphePanel.getListeSommetChemin() != null) {
					
					graphePanel.resetColorArreteChemin();
					graphePanel.resetColorSommetChemin();
					
					
					repaint();
				}
				setActionListenerToAfficherChemins();
				
			}
		});
		
		
		//////////////////////////////////////////////////////////////////////////////////////
		
		
	}

	public void setOuvrirFichierActions(){
		JFileChooser fenetreOuvertureFichier = new JFileChooser(new File("."));
		fenetreOuvertureFichier.setFileFilter(new FileNameExtensionFilter("Fichiers", "csv"));
		File fichier = new File("..\\src\\fichiersGraphe\\");
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

	private void updateInterface() {
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	/**
	 * Methode pour ajouter arete et sommet
	 **/
	private void ajoutSommet(ActionEvent a) {
	
	}
	
	private void ajoutArete(ActionEvent a) {
	
	}
	
	
	private void setActionListenerToAfficherChemins() {
		String selectChoixChemin = (String) choixTypeCheminComboBox.getSelectedItem();
		Graphe.MaillonGraphe sommetSelect = graphePanel.getSommetSelectionne();
		String destination = (String) choixDestinationComboBox.getSelectedItem();
		
		if (selectChoixChemin.equals((ChoixTypeChemin.FIABILITE.getAttribut()))) {
			graphe.rechercheChemin(sommetSelect.getNom(), destination);
			tableCheminsPanel.resetTable();
			tableCheminsPanel.updateColonne(selectChoixChemin);
			tableCheminsPanel.addDataInTable("Depart", String.valueOf(graphe.getSommetDonnees().get(0) * 100 + " %"));
			double fiabiliteTotale = 1.0;
			
			for (String i : graphe.getListeSommetCheminGraphe()) {
				//System.out.println("sommet : " + i);
				
				if (!i.equals(sommetSelect.getNom())) {
					
					Double donneeSommet = graphe.getSommetDonnees().get(graphe.getListeSommetCheminGraphe().indexOf(i));
					tableCheminsPanel.addDataInTable(i, String.valueOf(donneeSommet * 100) + " %");
					fiabiliteTotale *= donneeSommet;
				}
			}
			
			tableCheminsPanel.addDataInTable("Fiabilité totale", String.valueOf(Math.round(fiabiliteTotale * 100 * 100) / 100) + " %");
			
			graphePanel.colorCheminFiabilite();
			repaint();
			
		}
		
		if (selectChoixChemin.equals((ChoixTypeChemin.DISTANCE.getAttribut()))) {
			tableCheminsPanel.resetTable();
			listeSommetDjikstraChemin = new ArrayList<>();
			listeSommetDjikstraChemin.add(sommetSelect.getNom());
			tableCheminsPanel.updateColonne(selectChoixChemin);
			LinkedHashMap<String, Double> chemin = graphe.getCheminDijkstra().get(sommetSelect.getNom()).getCheminsDistanceTo(destination);
			if (chemin != null) {
				int distanceTotale = 0;
				
				for (Map.Entry<String, Double> entry : chemin.entrySet()) {
					String nomSommet = entry.getKey();
					double distance = entry.getValue();
					distanceTotale += (int) distance;
					if (!nomSommet.equals(sommetSelect.getNom())) {
						listeSommetDjikstraChemin.add(nomSommet);
						tableCheminsPanel.addDataInTable(nomSommet, distanceTotale + "Km");
					} else {
						tableCheminsPanel.addDataInTable("Départ", distanceTotale + "Km");
					}
					
				}
				tableCheminsPanel.addDataInTable("Distance Totale", distanceTotale + "Km");
			} else {
				tableCheminsPanel.addDataInTable(sommetSelect.getNom() + " -> " + destination, "Aucun Chemin");
			}
			graphePanel.colorCheminDjikstra();
			repaint();
			
		} else if (selectChoixChemin.equals((ChoixTypeChemin.DUREE.getAttribut()))) {
			
			tableCheminsPanel.resetTable();
			listeSommetDjikstraChemin = new ArrayList<>();
			listeSommetDjikstraChemin.add(sommetSelect.getNom());
			tableCheminsPanel.updateColonne(selectChoixChemin);
			LinkedHashMap<String, Double> chemin = graphe.getCheminDijkstra().get(sommetSelect.getNom()).getCheminsDureeTo(destination);
			if (chemin != null) {
				int dureeTotale = 0;
				
				for (Map.Entry<String, Double> entry : chemin.entrySet()) {
					String nomSommet = entry.getKey();
					double duree = entry.getValue();
					dureeTotale += (int) duree;
					if (!nomSommet.equals(sommetSelect.getNom())) {
						listeSommetDjikstraChemin.add(nomSommet);
						tableCheminsPanel.addDataInTable(nomSommet, dureeTotale + " min");
					} else {
						tableCheminsPanel.addDataInTable("Départ", dureeTotale + " min");
					}
					
				}
				tableCheminsPanel.addDataInTable("Durée Totale", dureeTotale + " min");
			} else {
				tableCheminsPanel.addDataInTable(sommetSelect.getNom() + " -> " + destination, "Aucun Chemin");
			}
			graphePanel.colorCheminDjikstra();
			repaint();
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
		titre.setVisible(true);
		itemModeDuGraphe.setEnabled(true);
		itemFonctionnalite.setEnabled(true);
		itemChoixTheme.setEnabled(true);

		nombreSommetLabel.setText("Dispensaires : "+nombreCentre);
		nombreRouteLabel.setText("Routes : "+nombreRoutes);
		nombreOpLabel.setText("Opératoires : "+graphe.getNombreOperatoire());
		nombreMatLabel.setText("Maternités : "+graphe.getNombreMaternite());
		nombreCentreNutriLabel.setText("Centres de nutrition : "+graphe.getNombreCentreNutrition());
	}
	
	/**
	 * @param nom
	 * @param type
	 */
	public void mettreVisibleComposantSommet(String nom, String type) {
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
	public  List<String> getListeSommetDjikstraChemin(){
		return this.listeSommetDjikstraChemin;
	}

	public JMenuItem getItemOuvrirFichier(){
		return itemOuvrirFichier;
	}
	
}
