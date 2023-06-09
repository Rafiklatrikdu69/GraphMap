package Interface;

import Interface.InfosSommetPanel.AfficherCheminPanel;
import LCGraphe.FloydWarshall;
import LCGraphe.Graphe;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static javax.swing.JOptionPane.showOptionDialog;

public class InterfaceGraphe extends JFrame {
	private CardLayout cardLayout;
	private InterfaceGraphe fenetrePrincipale; // la fenetre
	
	private String[] colonneIdentifiersChemin;
	
	public JPanel cp;
	
	private AccueilPanel accueilPanel;
	private JPanel barreDeChargementPanel, nord;
	private DessinGraphe graphePanel;
	
	private JLabel labelTitreVoisin;
	private DefaultTableModel modelInfosVoisins;
	private JTable tableInfosVoisins;
	
	
	private DefaultTableModel infoChemin;
	
	private JScrollPane affichageVoisinScrollPane;
	
	private JButton boutonSuivant, boutonPrecedent;
	
	private JPanel panelAllInfos;
	
	
	private JPanel contenuNomTypeSommetPanel;
	private JPanel contenuInfoSommetPanel;
	private JPanel affichageVoisinPanel;
	
	private JPanel contenuTousLesCheminsPanel;
	private AfficherCheminPanel tableCheminsPanel;
	private JPanel contenuButtonSuivPrec;
	
	private JPanel cardPanelInfos;
	
	private JLabel nombreRouteLabel, nombreSommetLabel, nomSommetSelectionneLabel, typeSommetSelectionneLabel;
	private JButton afficherCheminButton;
	private JComboBox<String> choixTypeCheminComboBox, choixDestinationComboBox;
	private JMenuBar menu;
	private JRadioButtonMenuItem modeLight, modeDark;
	private JMenu itemFichier;
	private JMenu itemFenetre;
	private JMenu itemFonctionnalites;
	private JMenu itemOptionFonction;
	private JMenu itemChoixTheme;
	private JMenuItem itemFermerFenetre, itemMenuPrincipale, itemOuvrirFichier;
	private JMenuItem itemAfficherCheminPlusCourts;
	static JRadioButton bloquerGraphe;
	private JProgressBar barreChargement;
	private boolean end;
	private int progress;
	private Timer timer;
	private Dimension screenSize;
	private File fichierCharge;
	private LCGraphe.Graphe graphe;
	private Integer nombreRoutes;
	private Integer nombreCentre;
	private JPanel contenuGraphePanel;
	
	public InterfaceGraphe() {
		super();
		
		accueilPanel = new AccueilPanel(this);
		setContentPane(accueilPanel);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		setSize(new Dimension(screenWidth / 2 + 300, screenHeight / 2 + 200));
		
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
		panelAllInfos = new JPanel(new BorderLayout()); // ce panel contient le card layout, le nom du sommet et son type.
		//On met un minimum de dimension de ce panel pour qu'il est la place d'afficher le contenu
		panelAllInfos.setPreferredSize(new Dimension((int) screenSize.getWidth() / 6, (int) screenSize.getHeight()));
		
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
		
		cardPanelInfos.add(contenuInfoSommetPanel, "panel de base");
		cardPanelInfos.add(contenuTousLesCheminsPanel, "panel Suivant");
		
		panelAllInfos.setBorder(BorderFactory.createTitledBorder("Panel Infos"));
		panelAllInfos.add(contenuNomTypeSommetPanel, BorderLayout.NORTH);
		panelAllInfos.add(contenuButtonSuivPrec, BorderLayout.SOUTH);
		panelAllInfos.add(cardPanelInfos, BorderLayout.CENTER);
		cp.add(panelAllInfos, BorderLayout.EAST);
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
		
		contenuTousLesCheminsPanel.setBorder(BorderFactory.createTitledBorder("contenu chemin"));
	}
	
	/**
	 *
	 */
	private void initContainerDessinGraphePanel() {
		contenuGraphePanel.removeAll();
		if (graphePanel == null) {
			graphePanel = new DessinGraphe(graphe, this);
		} else {
			graphePanel.changerGraphe(graphe);
		}
		//contenuGraphePanel.setBorder(BorderFactory.createTitledBorder("Graphe dessiner"));
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
		itemFonctionnalites.add(bloquerGraphe);
		itemFonctionnalites.setFont(fontBarreMenu);
		
		itemFichier.add(itemOuvrirFichier);
		itemFichier.setFont(fontBarreMenu);
		
		itemFenetre.add(itemChoixTheme);
		itemFenetre.add(itemMenuPrincipale);
		itemFenetre.add(itemFermerFenetre);
		itemFenetre.setFont(fontBarreMenu);
		
		itemOptionFonction.add(itemAfficherCheminPlusCourts);
		itemOptionFonction.setFont(fontBarreMenu);
		
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
	private void initialisationJMenu() {
		itemFichier = new JMenu("Fichier");
		itemFenetre = new JMenu("Fenetre");
		itemChoixTheme = new JMenu("Theme");
		itemFonctionnalites = new JMenu("Mode du Graphe");
		itemOptionFonction = new JMenu("Fonctionnalitées");
	}
	
	/**
	 *
	 */
	private void initialisationJMenuItem() {
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
		progress = 0;
		end = false;
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
					if (graphePanel != null) {
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
					if (graphePanel != null) {
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
		choixTypeCheminComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colonneIdentifiersChemin = new String[]{"Chemin", (String) choixTypeCheminComboBox.getSelectedItem()};
			}
		});
		afficherCheminButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setActionListenerToAfficherChemins();
				
			}
		});
		
	}
	
	private void setActionListenerToAfficherChemins() {
		String selectChoixChemin = (String) choixTypeCheminComboBox.getSelectedItem();
		Graphe.MaillonGraphe sommetSelect = graphePanel.getSommetSelectionne();
		String destination = (String) choixDestinationComboBox.getSelectedItem();
		
		if (selectChoixChemin.equals((ChoixTypeChemin.FIABILITE.getAttribut()))) {
			graphe.rechercheChemin(sommetSelect.getNom(), destination);
			tableCheminsPanel.resetTable();
			tableCheminsPanel.updateColonne(selectChoixChemin);
			tableCheminsPanel.addDataInTable("Depart", String.valueOf(graphe.getSommetDonnees().get(0)));
			double fiabiliteTotale = 1.0;
			
			for (String i : graphe.getSommet()) {
				System.out.println("sommet : " + i);
				
				if (!i.equals(sommetSelect.getNom())) {
	
					Double donneeSommet = graphe.getSommetDonnees().get(graphe.getSommet().indexOf(i));
					tableCheminsPanel.addDataInTable(i, String.valueOf(donneeSommet*100)+" %");
					fiabiliteTotale *= donneeSommet;
				}
			}
			
			tableCheminsPanel.addDataInTable("Fiabilité totale", String.valueOf(Math.round(fiabiliteTotale*100*100)/100) +" %");
			
			
		}
		
		 if(selectChoixChemin.equals((ChoixTypeChemin.DISTANCE.getAttribut())))
	
	{
		tableCheminsPanel.resetTable();
		tableCheminsPanel.updateColonne(selectChoixChemin);
		LinkedHashMap<String, Double> chemin = graphe.getCheminDijkstra().get(sommetSelect.getNom()).getCheminsDistanceTo(destination);
		if (chemin != null) {
			int distanceTotale = 0;
			
			for (Map.Entry<String, Double> entry : chemin.entrySet()) {
				String nomSommet = entry.getKey();
				double distance = entry.getValue();
				distanceTotale += (int) distance;
				if (!nomSommet.equals(sommetSelect.getNom())) {
					tableCheminsPanel.addDataInTable(nomSommet, distanceTotale + "Km");
				} else {
					tableCheminsPanel.addDataInTable("Départ", distanceTotale + "Km");
				}
				
			}
			tableCheminsPanel.addDataInTable("Distance Totale", distanceTotale + "Km");
		} else {
			tableCheminsPanel.addDataInTable(sommetSelect.getNom() + " -> " + destination, "Aucun Chemin");
		}
		
		
	}
		else if(selectChoixChemin.equals((ChoixTypeChemin.DUREE.getAttribut())))
	
	{
		tableCheminsPanel.resetTable();
		tableCheminsPanel.updateColonne(selectChoixChemin);
		LinkedHashMap<String, Double> chemin = graphe.getCheminDijkstra().get(sommetSelect.getNom()).getCheminsDureeTo(destination);
		if (chemin != null) {
			int dureeTotale = 0;
			
			for (Map.Entry<String, Double> entry : chemin.entrySet()) {
				String nomSommet = entry.getKey();
				double duree = entry.getValue();
				dureeTotale += (int) duree;
				if (!nomSommet.equals(sommetSelect.getNom())) {
					tableCheminsPanel.addDataInTable(nomSommet, dureeTotale + " min");
				} else {
					tableCheminsPanel.addDataInTable("Départ", dureeTotale + " min");
				}
				
			}
			tableCheminsPanel.addDataInTable("Durée Totale", dureeTotale + " min");
		} else {
			tableCheminsPanel.addDataInTable(sommetSelect.getNom() + " -> " + destination, "Aucun Chemin");
		}
	}
	
}
	//////////////////////////////////////////////////////////////////////////////////////
	
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
		itemFonctionnalites.setEnabled(false);
		itemOptionFonction.setEnabled(false);
		itemChoixTheme.setEnabled(false);
	}
	
	/**
	 *
	 */
	private void mettreVisibleComposantGraphe() {
		itemFonctionnalites.setEnabled(true);
		itemOptionFonction.setEnabled(true);
		itemChoixTheme.setEnabled(true);
	}
	
	/**
	 * @param nom
	 * @param type
	 */
	public void mettreVisibleComposantSommet(String nom, String type) {
		nomSommetSelectionneLabel.setText("Dispensaire " + nom);
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
}
