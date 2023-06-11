package Interface;

import Interface.InfosSommetPanel.AfficherCheminPanel;
import LCGraphe.Graphe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.showOptionDialog;

public class DessinGraphe extends JPanel {
	
	private InterfaceGraphe interfaceGraphe;
	
	public Map<String, Map<String, Double>> predecesseur;
	private Map<Integer, Boolean> verifePresenceChemin;
	private List<AreteVisuel> listeArreteChemin;
	private List<SommetVisuel> listeSommetChemin;
	private Graphe graphe;
	private Map<Graphe.MaillonGraphe, SommetVisuel> sommets;
	private List<AreteVisuel> listAretes;
	
	private Graphe.MaillonGraphe sommetSelectionne;
	
	private SommetVisuel sommetEnDeplacement;
	//Initialise les couleurs ainsi que les taille par defaut
	private static final int SOMMET_WIDTH = 40;
	private static final int SOMMET_HEIGHT = 40;
	public Color DEFAUT_COULEUR_SOMMET = Color.BLACK;
	public Color COULEUR_ARETE = Color.BLACK;
	public Color COULEUR_TEXTE_SOMMET = Color.WHITE;
	public Color COULEUR_SOMMET_SELECT = Color.BLUE;
	
	/**
	 * Constructeur de la classe DessinGraphe
	 */
	public DessinGraphe(Graphe graphe, InterfaceGraphe interfaceGraphe) {
		super();
		this.graphe = graphe;
		this.interfaceGraphe = interfaceGraphe;
		listAretes = new ArrayList<>();
		sommets = new HashMap<>();//Map pour associe chaque maillon de la liste avec un Jpanel
		setLayout(null); // Utiliser un layout null pour positionner les sommets manuellement
		initialisationGraphe();
		
	}
	
	
	/**
	 * @param g the <code>Graphics</code> object to protect
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		dessinerArete(g);
		
		
	}
	
	private void dessinerArete(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		listAretes.forEach(areteVisuel -> {
			g2d.setColor(areteVisuel.getCouleurLigne());
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(areteVisuel.getSommetVisuel1().getCentreDuCercle().x, areteVisuel.getSommetVisuel1().getCentreDuCercle().y, areteVisuel.getSommetVisuel2().getCentreDuCercle().x, areteVisuel.getSommetVisuel2().getCentreDuCercle().y);
		
			
		});
	}
	
	/**
	 * Méthode pour initialiser le graphe avec les sommets
	 */
	private void initialisationGraphe() {
		
		Graphe.MaillonGraphe tmp = graphe.getPremier();
		interfaceGraphe.getChoixDestinationComboBox().removeAllItems();
		while (tmp != null) {
			int[] pos = getRandomPositionPourSommet(interfaceGraphe.getContenuGraphePanel());
			//place le sommet a une position Random x et y
			initialisationPlacementSommet(tmp, pos[0], pos[1]);
			interfaceGraphe.getChoixDestinationComboBox().addItem(tmp.getNom());
			tmp = tmp.getSuivant();//Passe au maillon suivant
		}
		initialisationPlacementArete();
	}
	
	private void initialisationPlacementArete() {
		sommets.forEach((sommet, sommetVisuel) -> {
			Graphe.MaillonGrapheSec voisins = sommet.getVoisin();//recupere le voisin de la liste
			while (voisins != null) {
				AreteVisuel arete = new AreteVisuel(sommetVisuel, sommets.get(graphe.getCentre(voisins.getDestination())));/*creer un nouvelle arrete entre le sommet
                de depart et le voisin*/
				arete.setCouleurLigne(COULEUR_ARETE);
				listAretes.add(arete);
				
				voisins = voisins.getSuivantMaillonSec();//passe au maillon suivant
			}
		});
	}
	
	/**
	 * @param m
	 * @param x
	 * @param y
	 */
	private void initialisationPlacementSommet(Graphe.MaillonGraphe m, int x, int y) {
		// Création d'un Panel de dessin pour représenter le sommet visuellement
		SommetVisuel dessinPanel = new SommetVisuel(m, SOMMET_HEIGHT / 2);
		dessinPanel.setPreferredSize(new Dimension(SOMMET_WIDTH, SOMMET_HEIGHT));
		dessinPanel.setCouleurCentre(DEFAUT_COULEUR_SOMMET);
		dessinPanel.setCouleurTexte(COULEUR_TEXTE_SOMMET);
		dessinPanel.setBounds(x, y, SOMMET_WIDTH, SOMMET_HEIGHT);
		
		dessinPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				sommetEnDeplacement = dessinPanel;
				repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				if (listeArreteChemin != null&& listeSommetChemin!=null) {
					resetColorArreteChemin();
					resetColorSommetChemin();
				}
				
				actionPerformedClickDessinPanel(m);
				//calculerCheminPlusCourt();
				repaint();
				
			}
			
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				sommetEnDeplacement = null;
				repaint();
			}
		});
		
		dessinPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				actionPerformedDragged(e);
				repaint();
			}
		});
		
		add(dessinPanel);
		sommets.put(m, dessinPanel);
	}
	
	private void actionPerformedDragged(MouseEvent e) {
		if (!interfaceGraphe.getBloquerGraphe() && sommetEnDeplacement != null) {
			//calcule les nouvelles coordonnes du sommet deplacer
			setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			int dx = e.getX() - sommetEnDeplacement.getWidth() / 2;
			int dy = e.getY() - sommetEnDeplacement.getHeight() / 2;
			int newPosX = sommetEnDeplacement.getX() + dx;
			int newPosY = sommetEnDeplacement.getY() + dy;
			int maxX = getParent().getWidth() - (sommetEnDeplacement.getWidth());
			int maxY = getParent().getHeight() - (sommetEnDeplacement.getHeight());
			newPosX = Math.max(0, Math.min(newPosX, maxX));
			newPosY = Math.max(0, Math.min(newPosY, maxY));
			sommetEnDeplacement.setLocation(newPosX, newPosY);//reaffecte les positions du sommet
		}
	}
	
	private void actionPerformedClickDessinPanel(Graphe.MaillonGraphe m) {
		if (sommetSelectionne != null) {
			sommets.get(sommetSelectionne).setCouleurCentre(DEFAUT_COULEUR_SOMMET);
		}
		if (sommetSelectionne != null && sommetSelectionne.equals(m)) {
			// Si le sommet sélectionné est déjà sélectionné à nouveau, on le  désélectionne
			interfaceGraphe.mettreInvisibleComposantSommet();
			sommetSelectionne = null;
		} else {
			// Selectionne un nouveau sommet et afficher ses voisins
			sommetSelectionne = m;
			DefaultTableModel modelInfosVoisins = interfaceGraphe.getModelInfosVoisins();
			
			modelInfosVoisins.setRowCount(0);
			
			// parcourt les voisins du sommet sélectionné et les ajouter au modèle d'informations des voisins
			sommetSelectionne.voisinsToList().forEach(voisin -> {
				modelInfosVoisins.addRow(new Object[]{voisin.getDestination(), (int) voisin.getDistance() + "Km", (int) voisin.getDuree() + " min", (int) voisin.getFiabilite() * 10 + "%"});
			});
			
			// Met en évidence le sommet sélectionné visuellement
			sommets.get(sommetSelectionne).setCouleurCentre(COULEUR_SOMMET_SELECT);
			listAretes.get(1).setCouleurLigne(Color.BLUE);
			interfaceGraphe.mettreVisibleComposantSommet(sommetSelectionne.getNom(), sommetSelectionne.getType());
			
		}
	}
	
	
	/**
	 * Cette methode tire au hasard une position pour placer le sommet dans le Jpanel en recuperant ca largeur et ca hauteur
	 *
	 * @param container
	 * @return
	 */
	private int[] getRandomPositionPourSommet(JPanel container) {
		Random random = new Random();
		int minX = 5;
		int maxX = container.getWidth() - 30;
		int randomInRangeX = random.nextInt(maxX - minX + 1) + minX;
		int minY = 15;
		int maxY = container.getHeight() - 30;
		int randomInRangeY = random.nextInt(maxY - minY + 1) + minY;
		while (sommetExisteDansZone(randomInRangeX, randomInRangeY)) {
			randomInRangeX = random.nextInt(maxX - minX + 1) + minX;
			randomInRangeY = random.nextInt(maxY - minY + 1) + minY;
		}
		return new int[]{randomInRangeX, randomInRangeY};
	}
	
	private boolean sommetExisteDansZone(int x, int y) {
		for (Map.Entry<Graphe.MaillonGraphe, SommetVisuel> entry : sommets.entrySet()) {
			SommetVisuel jPanel = entry.getValue();
            /*Si le sommet touche  une zone qui possede deja un sommet
            alors on renvoie true pour dire que le sommet ne peux pas etre placer ici*/
			if (jPanel.getBounds().intersects(new Rectangle(x, y, SOMMET_WIDTH, SOMMET_HEIGHT))) {
				return true;
			}
		}
		return false;
	}
	
	public void miseAJourDessin() {
		sommets.forEach((maillonGraphe, sommetVisuel) -> {
			if (maillonGraphe.equals(sommetSelectionne)) {
				//le sommet selectionner change de couleur
				sommetVisuel.setCouleurCentre(COULEUR_SOMMET_SELECT);
			} else {
				//sinon la couleur est mise par defaut
				sommetVisuel.setCouleurCentre(DEFAUT_COULEUR_SOMMET);
			}
			sommetVisuel.setCouleurTexte(COULEUR_TEXTE_SOMMET);
		});
		listAretes.forEach(areteVisuel -> {
			//met la couleur de l'arrete
			areteVisuel.setCouleurLigne(COULEUR_ARETE);
		});
	}
	
	public void setDefautCouleurSommet(Color defautCouleurSommet) {
		DEFAUT_COULEUR_SOMMET = defautCouleurSommet;//Couleur par defaut du sommet
		repaint();
	}
	
	public void setCouleurArete(Color couleurArete) {
		COULEUR_ARETE = couleurArete;
		repaint();
	}
	
	public void setCouleurTexteSommet(Color couleurTexteSommet) {
		COULEUR_TEXTE_SOMMET = couleurTexteSommet;
		repaint();
	}
	
	public void setCouleurSommetSelect(Color couleurSommetSelect) {
		COULEUR_SOMMET_SELECT = couleurSommetSelect;
		repaint();
	}
	
	public void changerGraphe(Graphe nouveauGraphe) {
		this.graphe = nouveauGraphe;
		//predecesseur = nouveauGraphe.floydWarshallFiabilite();
		sommetSelectionne = null;
		listAretes = new ArrayList<>();
		this.removeAll();
		sommetEnDeplacement = null;
		sommets = new HashMap<>();
		initialisationGraphe();
	}
	
	AreteVisuel getArete(String nomSommet, String nomSommetDestination) {
		AreteVisuel areteVisuel = null;
		AreteVisuel tmp;
		int i = 0;
		while (areteVisuel == null && i < listAretes.size()) {
			tmp = listAretes.get(i);
			if ((tmp.getSommetVisuel1().getSommetGraphe().getNom().equals(nomSommet) && tmp.getSommetVisuel2().getSommetGraphe().getNom().equals(nomSommetDestination)) || (tmp.getSommetVisuel2().getSommetGraphe().getNom().equals(nomSommet) && tmp.getSommetVisuel1().getSommetGraphe().getNom().equals(nomSommetDestination))) {
				areteVisuel = tmp;
				System.out.println("arret entre deux sommets");
			}
			i++;
		}
		return areteVisuel;
	}
	
	public Graphe.MaillonGraphe getSommetSelectionne() {
		return sommetSelectionne;
	}
	
	
	public void colorChemin() {
		listeArreteChemin = new ArrayList<>();
		listeSommetChemin = new ArrayList<>();
		List<String> sommetsList = graphe.getSommet(); // Récupérer la liste des sommets
		
		for (int i = 0; i < sommetsList.size() - 1; i++) {
			String sommetCourant = sommetsList.get(i);
			String sommetSuivant = sommetsList.get(i + 1);
			
			SommetVisuel sommetVisuelCourant = sommets.get(graphe.getCentre(sommetCourant));
			SommetVisuel sommetVisuelSuivant = sommets.get(graphe.getCentre(sommetSuivant));
			listeSommetChemin.add(sommetVisuelCourant);
			listeSommetChemin.add(sommetVisuelSuivant);
			AreteVisuel areteVisuel = getArete(sommetVisuelCourant.getSommet(), sommetVisuelSuivant.getSommet());
			listAretes.remove(areteVisuel);
			listAretes.add(areteVisuel);
			if (areteVisuel == null) {
				System.out.println("arrete null");
			}
			
			listeArreteChemin.add(areteVisuel);
			sommetVisuelCourant.setCouleurCentre(Color.RED);
			sommetVisuelSuivant.setCouleurCentre(Color.RED);
			areteVisuel.setCouleurLigne(Color.RED);
			
		}
		
		repaint();
	}
	
	public void resetColorArreteChemin() {
		listeArreteChemin.forEach(areteVisuel -> {
			//met la couleur de l'arrete
			areteVisuel.setCouleurLigne(COULEUR_ARETE);
		});
		
	}
	public void resetColorSommetChemin() {
		listeSommetChemin.forEach(areteVisuel -> {
			//met la couleur de l'arrete
			areteVisuel.setCouleurCentre(DEFAUT_COULEUR_SOMMET);
		});
		
	}
	
	
	/**
	 * @return
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(100, 100);
	}
	public List<SommetVisuel> getListeSommetChemin(){
		return this.listeSommetChemin;
	}
	public List<AreteVisuel> getListeArreteChemin(){
		return this.listeArreteChemin;
	}
}

