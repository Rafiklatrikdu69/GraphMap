package Interface;

import LCGraphe.Graphe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DessinGraphe extends JPanel {
	
	private final InterfaceGraphe interfaceGraphe;
	private boolean misAjourAutoriseFloydWarshall = false;
	private boolean misAjourAutorise = false;
	private List<AreteVisuel> listeAreteChemin;
	private List<SommetVisuel> listeSommetChemin;
	private Graphe graphe;
	private Map<Graphe.MaillonGraphe, SommetVisuel> sommets;
	private List<AreteVisuel> listAretes;
	private SommetVisuel sommetSelectionne;
	private SommetVisuel sommetEnDeplacement;
	//Initialise les couleurs ainsi que les taille par defaut
	private static final int SOMMET_WIDTH = 40;
	private static final int SOMMET_HEIGHT = 40;
	private Theme themeActuel = Theme.LIGHT;

	private Color couleurRectangle; // Couleur par défaut du rectangle
	private Color couleurBordure; // Couleur par défaut du rectangle

	private Map<String, Point> positionSommets;
	private Map<Dimension, Map<String, Point>> positionSommetsDimensions;

	/**
	 * Constructeur de la classe DessinGraphe
	 */
	public DessinGraphe(Graphe graphe, InterfaceGraphe interfaceGraphe) {
		super();
		this.graphe = graphe;
		this.interfaceGraphe = interfaceGraphe;
		positionSommets = new HashMap<>();
		setPositionSommets();
		couleurRectangle = themeActuel.getCouleurRectangleFiab();
		couleurBordure = themeActuel.getCouleurBordureRectFiab();
		listeSommetChemin = new ArrayList<>();
		listeAreteChemin = new ArrayList<>();
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

	private void setPositionSommets(){
		positionSommets.put("S1", new Point(103,270));
		positionSommets.put("S2", new Point(20,363));
		positionSommets.put("S3", new Point(244,207));
		positionSommets.put("S4", new Point(266,408));
		positionSommets.put("S5", new Point(300,295));
		positionSommets.put("S6", new Point(400,131));
		positionSommets.put("S7", new Point(498,20));
		positionSommets.put("S8", new Point(530,170));
		positionSommets.put("S9", new Point(689,366));
		positionSommets.put("S10", new Point(621,484));
		positionSommets.put("S11", new Point(646,284));
		positionSommets.put("S12", new Point(746,442));
		positionSommets.put("S13", new Point(960,230));
		positionSommets.put("S14", new Point(644,127));
		positionSommets.put("S15", new Point(860,209));
		positionSommets.put("S16", new Point(950,365));
		positionSommets.put("S17", new Point(835,370));
		positionSommets.put("S18", new Point(835,463));
		positionSommets.put("S19", new Point(929,583));
		positionSommets.put("S20", new Point(686,564));
		positionSommets.put("S21", new Point(263,587));
		positionSommets.put("S22", new Point(61,512));
		positionSommets.put("S23", new Point(459,580));
		positionSommets.put("S24", new Point(451,514));
		positionSommets.put("S25", new Point(456,363));
		positionSommets.put("S26", new Point(420,244));
		positionSommets.put("S27", new Point(164,86));
		positionSommets.put("S28", new Point(304,63));
		positionSommets.put("S29", new Point(784,239));
		positionSommets.put("S30", new Point(820,90));

	}

	public void setCouleurRectangle(Color couleur) {
		this.couleurRectangle = couleur;
		repaint();
	}


	public void setCouleurBordure(Color couleur) {
		this.couleurBordure = couleur;
		repaint();
	}
	
	private void dessinerArete(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// Dessiner les arêtes
		listAretes.forEach(areteVisuel -> {
			AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, areteVisuel.getOpacity());
			g2d.setComposite(alphaComposite);
			g2d.setColor(areteVisuel.getCouleurLigne());
			g2d.setStroke(new BasicStroke(areteVisuel.getTailleTrait()));
			int ax = areteVisuel.getSommetVisuel1().getCentreDuCercle().x;
			int bx = areteVisuel.getSommetVisuel2().getCentreDuCercle().x;
			int ay = areteVisuel.getSommetVisuel1().getCentreDuCercle().y;
			int by = areteVisuel.getSommetVisuel2().getCentreDuCercle().y;
			int textX = (ax + bx) / 2;
			int textY = (ay + by) / 2;
			g2d.drawLine(ax, ay, bx, by);
			
			// Dessiner le rectangle avec fond bleu
			g2d.setStroke(new BasicStroke(3)); // Épaisseur de la bordure
			g2d.setColor(couleurBordure); // Couleur de la bordure
			g2d.drawRect(textX - 10, textY - 10, 20, 20);
			
			g2d.setColor(couleurRectangle); // Couleur du rectangle
			g2d.fillRect(textX - 9, textY - 9, 18, 18);
			
			// Écrire le nombre à l'intérieur du rectangle
			g2d.setColor(themeActuel.getCouleurTexte()); // Couleur du texte
			String fiabilite = (int) areteVisuel.getSommetVisuel1().getSommetGraphe().getVoisin(areteVisuel.getSommetVisuel2().getSommet()).getFiabilite() + "";
			Font font = new Font("Arial", Font.BOLD, 11);
			g2d.setFont(font);
			g2d.drawString(fiabilite, textX - 4, textY + 5); // Coordonnées du texte (à adapter selon vos besoins)
		});
		
		repaint();
	}
	
	
	
	/**
	 * Méthode pour initialiser le graphe avec les sommets
	 */
	private void initialisationGraphe() {
		
		Graphe.MaillonGraphe tmp = graphe.getPremier();
		
		interfaceGraphe.getChoixDestinationComboBox().removeAllItems();
		interfaceGraphe.getChoixDestinationComboBox().addItem("Maternité");
		interfaceGraphe.getChoixDestinationComboBox().addItem("Centre de nutrition");
		interfaceGraphe.getChoixDestinationComboBox().addItem("Opératoire");
		while (tmp != null) {
			if(interfaceGraphe.getFichierCharge().getName().equals("graphe30Som74Arete.csv")){
				int x = positionSommets.get(tmp.getNom()).x;
				int y = positionSommets.get(tmp.getNom()).y;
				initialisationPlacementSommet(tmp, x, y);
			} else {
				int[] pos = getRandomPositionPourSommet(interfaceGraphe.getContenuGraphePanel());
				initialisationPlacementSommet(tmp, pos[0], pos[1]);
			}
			//place le sommet a une position Random x et y
			interfaceGraphe.getChoixDestinationComboBox().addItem(tmp.getNom());
			tmp = tmp.getSuivant();//Passe au maillon suivant
		}
		
		initialisationPlacementArete();

	}
	
	/**
	 *
	 */
	private void initialisationPlacementArete() {
		sommets.forEach((sommet, sommetVisuel) -> {
			Graphe.MaillonGrapheSec voisins = sommet.getVoisin();//recupere le voisin de la liste
			while (voisins != null) {
				AreteVisuel arete = new AreteVisuel(sommetVisuel, sommets.get(voisins.getDestination()));/*creer un nouvelle arrete entre le sommet
                de depart et le voisin*/
				arete.setCouleurLigne(themeActuel.getCouleurArete());
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
	public void initialisationPlacementSommet(Graphe.MaillonGraphe m, int x, int y) {
		// Création d'un Panel de dessin pour représenter le sommet visuellement
		SommetVisuel dessinPanel = new SommetVisuel(m, SOMMET_HEIGHT);
		dessinPanel.setCouleurCentre(themeActuel.getCouleurSommet());
		dessinPanel.setCouleurTexte(themeActuel.getCouleurTexte());
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
				setOpacityToAllAretes(1.0F);
				setOpacityToAllSommet(1.0F);
				
				
				if (!listeAreteChemin.isEmpty() && !listeSommetChemin.isEmpty()) {
					setCouleurRectangle(themeActuel.getCouleurRectangleFiab());
					setCouleurBordure(themeActuel.getCouleurBordureRectFiab());
					resetColorArreteChemin();
					resetColorSommetChemin();
				}

				actionPerformedClickDessinPanel(dessinPanel);
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
	
	/**
	 * @param e
	 */
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
	
	/**
	 * @param m
	 */
	public void actionPerformedClickDessinPanel(SommetVisuel m) {
		resetTailleTraits();
		setOpacityToAllAretes(1.0F);
		setOpacityToAllSommet(1.0F);
		interfaceGraphe.getComparaisonPanel().removeAll();
		if (sommetSelectionne != null) {
			sommetSelectionne.setCouleurBordureRond(themeActuel.getCouleurBordureSommet());
			sommetSelectionne.setCouleurCentre(themeActuel.getCouleurSommet());
		}
		if (sommetSelectionne != null && sommetSelectionne.equals(m)) {
			// Si le sommet sélectionné est déjà sélectionné à nouveau, on le désélectionne
			interfaceGraphe.mettreInvisibleComposantSommet();
			interfaceGraphe.setNotSelectAuNom();
			sommetSelectionne = null;
		} else {
			// Selectionne un nouveau sommet et afficher ses voisins
			interfaceGraphe.getCardLayout().first(interfaceGraphe.getCardPanelInfos());
			interfaceGraphe.updateIndicateur(0);
			sommetSelectionne = m;
			ArrayList<Object[]> listInfosVoisins = new ArrayList<>();
			AtomicInteger nombreMaternite = new AtomicInteger();
			AtomicInteger nombreOperatoire = new AtomicInteger();
			AtomicInteger nombreCentreDeNutri = new AtomicInteger();
			// parcourt les voisins du sommet sélectionné et les ajouter au modèle d'informations des voisins
			sommetSelectionne.getSommetGraphe().voisinsToList().forEach(voisin -> {
				if(voisin.getDestination().getType().equals("Maternité")){
					nombreMaternite.getAndIncrement();
				} else if (voisin.getDestination().getType().equals("Opératoire")) {
					nombreOperatoire.getAndIncrement();
				} else if (voisin.getDestination().getType().equals("Centre de nutrition")) {
					nombreCentreDeNutri.getAndIncrement();
				}
				listInfosVoisins.add(new Object[]{voisin.getDestination().getNom(), voisin.getDestination().getType(), (int) voisin.getDistance() + "Km", (int) voisin.getDuree() + " min", (int) voisin.getFiabilite() * 10 + "%"});
			});
			interfaceGraphe.getComparaisonPanel().add(new JLabel("Nombre de maternité "+ nombreMaternite));
			interfaceGraphe.getComparaisonPanel().add(new JLabel("Nombre d'opératoire "+ nombreOperatoire));
			interfaceGraphe.getComparaisonPanel().add(new JLabel("Nombre de centre de nutrition "+ nombreCentreDeNutri));
			
			setContenuDansTableVoisins(listInfosVoisins);
			
			// Met en évidence le sommet sélectionné visuellement
			sommetSelectionne.setCouleurCentre(themeActuel.getCouleurSommetSelect());
			sommetSelectionne.setCouleurBordureRond(themeActuel.getCouleurBordureSommetChemin());
			//listAretes.get(1).setCouleurLigne(Color.BLUE);
			interfaceGraphe.mettreVisibleComposantSommet(sommetSelectionne.getSommetGraphe().getNom(), sommetSelectionne.getSommetGraphe().getType());
			
		}
	}
	
	public void setContenuDansTableVoisins(List<Object[]> listInfosVoisins) {
		DefaultTableModel modelInfosVoisins = interfaceGraphe.getModelInfosVoisins();
		
		modelInfosVoisins.setRowCount(0);
		
		listInfosVoisins.forEach(modelInfosVoisins::addRow);
	}


	
	
	/**
	 * Cette methode tire au hasard une position pour placer le sommet dans le Jpanel en recuperant ca largeur et ca hauteur
	 *
	 * @param container
	 * @return
	 */
	public int[] getRandomPositionPourSommet(JPanel container) {
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
	
	/**
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean sommetExisteDansZone(int x, int y) {
		for (Map.Entry<Graphe.MaillonGraphe, SommetVisuel> entry : sommets.entrySet()) {
			SommetVisuel jPanel = entry.getValue();
            /*Si le sommet touche  une zone qui possede deja un sommet
            alors on renvoie true pour dire que le sommet ne peux pas etre placer ici*/
			if (jPanel.getBounds().intersects(new Rectangle(x-30, y-30, SOMMET_WIDTH+60, SOMMET_HEIGHT+60))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *
	 */
	public void miseAJourDessin() {
		sommets.forEach((maillonGraphe, sommetVisuel) -> {
			if (sommetVisuel.equals(sommetSelectionne)) {
				//le sommet selectionner change de couleur
				sommetVisuel.setCouleurCentre(themeActuel.getCouleurSommetSelect());
				sommetVisuel.setCouleurBordureRond(themeActuel.getCouleurBordureSommetChemin());
			} else {
				//sinon la couleur est mise par defaut
				sommetVisuel.setCouleurBordureRond(themeActuel.getCouleurBordureSommet());
				sommetVisuel.setCouleurCentre(themeActuel.getCouleurSommet());
			}
			sommetVisuel.setCouleurTexte(themeActuel.getCouleurTexte());
		});
		listAretes.forEach(areteVisuel -> {
			//met la couleur de l'arrete
			if(listeAreteChemin.contains(areteVisuel)){
				areteVisuel.setCouleurLigne(themeActuel.getCouleurAreteChemin());
				areteVisuel.getSommetVisuel1().setCouleurCentre(themeActuel.getCouleurSommetSelect());
				areteVisuel.getSommetVisuel2().setCouleurCentre(themeActuel.getCouleurSommetSelect());
			} else {
				areteVisuel.setCouleurLigne(themeActuel.getCouleurArete());
			}
		});
		setCouleurRectangle(themeActuel.getCouleurRectangleFiab());
		setCouleurBordure(themeActuel.getCouleurBordureRectFiab());

	}
	
	public void setOpacityToAllAretes(float opacity) {
		listAretes.forEach(areteVisuel -> {
			areteVisuel.setOpacity(opacity);
			repaint();
		});
	}
	
	public void setOpacityToAreteVisuel(AreteVisuel areteVisuel, float opacity) {
		areteVisuel.setOpacity(opacity);
		repaint();
	}
	
	public void setOpacityToAllSommet(float opacity) {
		sommets.forEach((maillonGraphe, sommetVisuel) -> {
			sommetVisuel.setOpacity(opacity);
			repaint();
		});
	}
	
	public void setOpacityToSommetVisuel(SommetVisuel sommetVisuel, float opacity) {
		sommetVisuel.setOpacity(opacity);
		repaint();
	}
	
	public void setOpacityToSommetVisuel(Graphe.MaillonGraphe sommet, float opacity) {
		sommets.get(sommet).setOpacity(opacity);
		repaint();
	}
	
	/**
	 * @param nouveauGraphe
	 */
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
	/**Getters/Setters**/
	/**
	 * @param defautCouleurSommet
	 */
	public void setDefautCouleurSommet(Color defautCouleurSommet) {
		themeActuel.setCouleurSommet(defautCouleurSommet);//Couleur par defaut du sommet
		repaint();
	}
	
	/**
	 * @param couleurArete
	 */
	public void setCouleurArete(Color couleurArete) {
		themeActuel.setCouleurArete(couleurArete);
		repaint();
	}
	
	/**
	 * @param couleurTexteSommet
	 */
	public void setCouleurTexteSommet(Color couleurTexteSommet) {
		themeActuel.setCouleurTexte(couleurTexteSommet);
		repaint();
	}
	
	/**
	 * @param nomSommet
	 * @param nomSommetDestination
	 * @return AreteVisuel
	 */
	AreteVisuel getArete(String nomSommet, String nomSommetDestination) {
		AreteVisuel areteVisuel = null;
		AreteVisuel tmp;
		int i = 0;
		while (areteVisuel == null && i < listAretes.size()) {
			tmp = listAretes.get(i);
			if ((tmp.getSommetVisuel1().getSommetGraphe().getNom().equals(nomSommet) && tmp.getSommetVisuel2().getSommetGraphe().getNom().equals(nomSommetDestination)) || (tmp.getSommetVisuel2().getSommetGraphe().getNom().equals(nomSommet) && tmp.getSommetVisuel1().getSommetGraphe().getNom().equals(nomSommetDestination))) {
				areteVisuel = tmp;
			}
			i++;
		}
		return areteVisuel;
	}
	
	public SommetVisuel getSommetSelectionne() {
		return sommetSelectionne;
	}
	
	
	/**
	 * Dessin du chemin
	 **/
	
	public void colorCheminFiabilite() {
		List<String> sommetsList = graphe.getListeSommetCheminGraphe(); // Récupérer la liste des sommets
		setOpacityToAllSommet(0.1F);
		setOpacityToAllAretes(0.1F);
		sommets.get(graphe.getSommet(sommetsList.get(0))).setCouleurCentre(Theme.LIGHT.getCouleurSommetSelect());
		sommets.get(graphe.getSommet(sommetsList.get(0))).setOpacity(1.0F);
		sommets.get(graphe.getSommet(sommetsList.get(0))).setCouleurBordureRond(Theme.LIGHT.getCouleurBordureSommetChemin());
		for (int i = 0; i < sommetsList.size() - 1; i++) { // Parcourt les sommets du chemin
			String sommetCourant = sommetsList.get(i); // Récupère le sommet courant
			String sommetSuivant = sommetsList.get(i + 1); // Récupère le sommet suivant
			
			SommetVisuel sommetVisuelCourant = sommets.get(graphe.getSommet(sommetCourant)); // Récupère le sommet visuel correspondant au sommet courant
			SommetVisuel sommetVisuelSuivant = sommets.get(graphe.getSommet(sommetSuivant)); // Récupère le sommet visuel correspondant au sommet suivant
			
			listeSommetChemin.add(sommetVisuelCourant); // Ajoute le sommet courant à la liste des sommets du chemin
			listeSommetChemin.add(sommetVisuelSuivant); // Ajoute le sommet suivant à la liste des sommets du chemin
			AreteVisuel areteVisuel = getArete(sommetVisuelCourant.getSommet(), sommetVisuelSuivant.getSommet()); // Récupère l'arête visuelle entre les deux sommets
			
			listAretes.remove(areteVisuel); // Supprime l'arête de la liste des arêtes
			listAretes.add(areteVisuel); // Ajoute l'arête à la fin de la liste pour la mettre en premier plan
			listeAreteChemin.add(areteVisuel); // Ajoute l'arête à la liste des arêtes du chemin
			
			sommetVisuelCourant.setOpacity(1.0F);
			sommetVisuelSuivant.setOpacity(1.0F);
			areteVisuel.setOpacity(1.0F);
			areteVisuel.setTailleTrait(3);
			sommetVisuelSuivant.setCouleurCentre(themeActuel.getCouleurSommetSelect()); // Change la couleur du sommet suivant
			sommetVisuelCourant.setCouleurCentre(themeActuel.getCouleurSommetSelect()); // Change la couleur du sommet courant
			
			sommetVisuelCourant.setCouleurBordureRond(themeActuel.getCouleurBordureSommetChemin());
			sommetVisuelSuivant.setCouleurBordureRond(themeActuel.getCouleurBordureSommetChemin());
			areteVisuel.setCouleurLigne(themeActuel.getCouleurAreteChemin()); // Change la couleur de l'arête
		}
		
		misAjourAutoriseFloydWarshall = true; // Indique que la mise à jour du graphe de Floyd-Warshall est autorisée
		repaint(); // Redessine le graphe
	}
	
	public void colorCheminDjikstra() {
		listeAreteChemin = new ArrayList<>(); // Liste pour stocker les arêtes du chemin
		listeSommetChemin = new ArrayList<>(); // Liste pour stocker les sommets du chemin
		setOpacityToAllSommet(0.1F);
		setOpacityToAllAretes(0.1F);
		int longueur = interfaceGraphe.getListeSommetDjikstraChemin().size();
		if(longueur == 1){
			String sommetCourant = interfaceGraphe.getListeSommetDjikstraChemin().get(0); // Récupère le sommet courant

			SommetVisuel sommetVisuelCourant = sommets.get(graphe.getSommet(sommetCourant)); // Récupère le sommet visuel correspondant au sommet courant
			listeSommetChemin.add(sommetVisuelCourant); // Ajoute le sommet courant à la liste des sommets du chemin
			sommetVisuelCourant.setOpacity(1.0F);
			sommetVisuelCourant.setCouleurCentre(themeActuel.getCouleurSommetSelect()); // Change la couleur du sommet courant
			sommetVisuelCourant.setCouleurBordureRond(themeActuel.getCouleurBordureSommetChemin());
		} else  {
			for (int i = 0; i < longueur - 1; i++) { // Parcourt les sommets du chemin
				String sommetCourant = interfaceGraphe.getListeSommetDjikstraChemin().get(i); // Récupère le sommet courant
				String sommetSuivant = interfaceGraphe.getListeSommetDjikstraChemin().get(i + 1); // Récupère le sommet suivant

				SommetVisuel sommetVisuelCourant = sommets.get(graphe.getSommet(sommetCourant)); // Récupère le sommet visuel correspondant au sommet courant
				SommetVisuel sommetVisuelSuivant = sommets.get(graphe.getSommet(sommetSuivant)); // Récupère le sommet visuel correspondant au sommet suivant
				listeSommetChemin.add(sommetVisuelCourant); // Ajoute le sommet courant à la liste des sommets du chemin
				listeSommetChemin.add(sommetVisuelSuivant); // Ajoute le sommet suivant à la liste des sommets du chemin
				AreteVisuel areteVisuel = getArete(sommetVisuelCourant.getSommet(), sommetVisuelSuivant.getSommet()); // Récupère l'arête visuelle entre les deux sommets

				listAretes.remove(areteVisuel); // Supprime l'arête de la liste des arêtes
				listAretes.add(areteVisuel); // Ajoute l'arête à la fin de la liste pour la mettre en premier plan
				listeAreteChemin.add(areteVisuel); // Ajoute l'arête à la liste des arêtes du chemin
				sommetVisuelCourant.setOpacity(1.0F);
				sommetVisuelSuivant.setOpacity(1.0F);
				areteVisuel.setOpacity(1.0F);
				areteVisuel.setTailleTrait(3);
				sommetVisuelCourant.setCouleurCentre(themeActuel.getCouleurSommetSelect()); // Change la couleur du sommet courant
				sommetVisuelSuivant.setCouleurCentre(themeActuel.getCouleurSommetSelect()); // Change la couleur du sommet suivant
				sommetVisuelCourant.setCouleurBordureRond(themeActuel.getCouleurBordureSommetChemin());
				sommetVisuelSuivant.setCouleurBordureRond(themeActuel.getCouleurBordureSommetChemin());
				areteVisuel.setCouleurLigne(themeActuel.getCouleurAreteChemin()); // Change la couleur de l'arête

			}
		}
		misAjourAutorise = true; // Indique que la mise à jour est autorisée
		repaint(); // Redessine le graphe
	}
	
	public void resetTailleTraits() {
		listAretes.forEach(areteVisuel -> {
			areteVisuel.setTailleTrait(2);
		});
	}
	
	public void resetColorArreteChemin() {
		if (listeAreteChemin != null) {
			listeAreteChemin.forEach(areteVisuel -> {
				areteVisuel.setTailleTrait(2);
				//met la couleur de l'arrete
				areteVisuel.setCouleurLigne(themeActuel.getCouleurArete());
				areteVisuel.getSommetVisuel1().setCouleurBordureRond(themeActuel.getCouleurBordureSommet());
				areteVisuel.getSommetVisuel2().setCouleurBordureRond(themeActuel.getCouleurBordureSommet());
			});
		}
		listeAreteChemin = new ArrayList<>();
	}
	
	public void resetColorSommetChemin() {
		if (listeSommetChemin != null) {
			listeSommetChemin.forEach(areteVisuel -> {
				//met la couleur de l'arrete
				areteVisuel.setCouleurCentre(themeActuel.getCouleurSommet());
			});
		}
		listeSommetChemin = new ArrayList<>();
	}
	
	
	/**
	 * @return
	 * @return
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(100, 100);
	}
	
	public List<SommetVisuel> getListeSommetChemin() {
		return this.listeSommetChemin;
	}
	
	public List<AreteVisuel> getListeAreteChemin() {
		return this.listeAreteChemin;
	}
	
	public boolean getMisAjourAutoriseFloydWarsall() {
		return misAjourAutoriseFloydWarshall;
	}
	
	public void setMisAjourAutoriseFloydWarshall(Boolean b) {
		misAjourAutoriseFloydWarshall = b;
	}
	
	public boolean getMisAjourAutoriseDjikstra() {
		return misAjourAutorise;
	}
	
	public void setMisAjourAutoriseDjikstra(Boolean b) {
		misAjourAutorise = b;
	}
	
	public Map<Graphe.MaillonGraphe, SommetVisuel> getListeSommetVisuel() {
		return sommets;
	}
	
	public void setThemeActuel(Theme themeActuel) {
		this.themeActuel = themeActuel;
	}
	
	public Theme getThemeActuel() {
		return themeActuel;
	}
}

