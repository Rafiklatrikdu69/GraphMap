import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static javax.swing.JOptionPane.showOptionDialog;

public class DessinGraphe extends JPanel {

    private Map<LCGraphe.MaillonGraphe, SommetVisuel> sommets;
    private ArrayList<AreteVisuel> listAretes;

    private LCGraphe.MaillonGraphe sommetSelectionne;

    private SommetVisuel sommetEnDeplacement;

    private static final int SOMMET_WIDTH = 40;
    private static final int SOMMET_HEIGHT = 40;
    public Color DEFAUT_COULEUR_SOMMET = Color.BLACK;
    public Color COULEUR_ARETE = Color.BLACK;
    public Color COULEUR_TEXTE_SOMMET = Color.WHITE;
    public Color COULEUR_SOMMET_SELECT = Color.BLUE;

    /**
     * Constructeur de la classe DessinGraphe
     */
    public DessinGraphe() {
        super();
        listAretes = new ArrayList<>();
        sommets = new HashMap<>();
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

    private void dessinerArete(Graphics g){
        listAretes.forEach(areteVisuel -> {
            g.setColor(areteVisuel.getColorLine());
            g.drawLine(areteVisuel.getSommetVisuel1().getCentreDuCercle().x, areteVisuel.getSommetVisuel1().getCentreDuCercle().y, areteVisuel.getSommetVisuel2().getCentreDuCercle().x, areteVisuel.getSommetVisuel2().getCentreDuCercle().y);
            repaint();
        });
    }

    /**
     * Méthode pour initialiser le graphe avec les sommets
     */
    private void initialisationGraphe() {
        LCGraphe.MaillonGraphe tmp = InterfaceGraphe.Graphe.getPremier();
        InterfaceGraphe.getChoixDestinationComboBox().removeAllItems();
        while (tmp != null) {
            int[] pos = getRandomPositionPourSommet(InterfaceGraphe.contenuGraphePanel);
            initialisationPlacementSommet(tmp, pos[0], pos[1]);
            InterfaceGraphe.getChoixDestinationComboBox().addItem(tmp.getNom());
            tmp = tmp.getSuivant();
        }
        initialisationPlacementArete();
    }

    private void initialisationPlacementArete(){
        sommets.forEach((sommet, sommetVisuel) -> {
            LCGraphe.MaillonGrapheSec voisins = sommet.getVoisin();
            while (voisins != null){
                AreteVisuel arete = new AreteVisuel(sommetVisuel, sommets.get(InterfaceGraphe.Graphe.getCentre(voisins.getDestination())));
                arete.setColorLine(COULEUR_ARETE);
                listAretes.add(arete);
                voisins = voisins.getSuivant();
            }
        });
    }

    /**
     * @param m
     * @param x
     * @param y
     */
    private void initialisationPlacementSommet(LCGraphe.MaillonGraphe m, int x, int y) {

        SommetVisuel dessinPanel = new SommetVisuel(m, SOMMET_HEIGHT/2);
        dessinPanel.setPreferredSize(new Dimension(SOMMET_WIDTH, SOMMET_HEIGHT));
        dessinPanel.setCouleurCentre(DEFAUT_COULEUR_SOMMET);
        dessinPanel.setCouleurTexte(COULEUR_TEXTE_SOMMET);
        dessinPanel.setBounds(x, y, SOMMET_WIDTH, SOMMET_HEIGHT);

        dessinPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                sommetEnDeplacement = dessinPanel;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(sommetSelectionne != null){
                    sommets.get(sommetSelectionne).setCouleurCentre(DEFAUT_COULEUR_SOMMET);
                }
                if(sommetSelectionne != null && sommetSelectionne.equals(m)){
                    InterfaceGraphe.mettreInvisibleComposantSommet();
                    sommetSelectionne = null;
                } else {
                    sommetSelectionne = m;

                    InterfaceGraphe.modelInfosVoisins.setRowCount(0);

                    sommetSelectionne.voisinsToList().forEach(voisin -> {
                        InterfaceGraphe.modelInfosVoisins.addRow(new Object[]{voisin.getDestination(), (int) voisin.getDistance() + "Km", (int)voisin.getDuree()+" min", (int) voisin.getFiabilite()*10+"%"});
                    });
                    sommets.get(sommetSelectionne).setCouleurCentre(COULEUR_SOMMET_SELECT);
                    InterfaceGraphe.mettreVisibleComposantSommet(sommetSelectionne.getNom(), sommetSelectionne.getType());
                    calculerCheminPlusCourt();
                }
            }


            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                sommetEnDeplacement = null;
            }
        });

        dessinPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (!InterfaceGraphe.bloquerGraphe.isSelected() && sommetEnDeplacement != null) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    int dx = e.getX() - sommetEnDeplacement.getWidth() / 2;
                    int dy = e.getY() - sommetEnDeplacement.getHeight() / 2;
                    int newPosX = sommetEnDeplacement.getX() + dx;
                    int newPosY = sommetEnDeplacement.getY() + dy;
                    int maxX = getParent().getWidth() - (sommetEnDeplacement.getWidth());
                    int maxY = getParent().getHeight() - (sommetEnDeplacement.getHeight());
                    newPosX = Math.max(0, Math.min(newPosX, maxX));
                    newPosY = Math.max(0, Math.min(newPosY, maxY));
                    sommetEnDeplacement.setLocation(newPosX, newPosY);
                }
            }
        });

        add(dessinPanel);
        sommets.put(m, dessinPanel);
    }

    private void calculerCheminPlusCourt() {
        if (InterfaceGraphe.cheminValide) {
            System.out.println("Chemin valide ");
            double[][] predecesseur = InterfaceGraphe.Graphe.floydWarshallDistance();
            AlgoPlusCourtsChemins algoPlusCourtsChemins = new AlgoPlusCourtsChemins();
            rechercherChemin(sommetSelectionne.getNom(), algoPlusCourtsChemins.getChoixSommet());
            InterfaceGraphe.cheminValide = false;
        }
    }

    public void rechercherChemin(String source, String destination) {


        // Vérifie si les sommets saisis existent dans le graphe
        if (!InterfaceGraphe.Graphe.indexSommet().containsKey(source) || !InterfaceGraphe.Graphe.indexSommet().containsKey(destination)) {
            System.out.println("Les sommets saisis ne sont pas valides.");
            return;
        }

        int indexSource = InterfaceGraphe.Graphe.indexSommet().get(source);
        int indexDestination = InterfaceGraphe.Graphe.indexSommet().get(destination);

        // Vérifie si un chemin existe entre les sommets saisis
        if (InterfaceGraphe.Graphe.getPredecesseurs()[indexSource][indexDestination] == -1) {
            JPanel panelAucunChemin = new JPanel();
            JLabel chemin = new JLabel("Aucun chemin trouver !");
            panelAucunChemin.add(chemin);
            int result = showOptionDialog(null, panelAucunChemin, "Chemin Court", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            return;

        }


        InterfaceGraphe.Graphe.afficherChemin(indexSource, indexDestination);


        System.out.println("Distance : " + InterfaceGraphe.Graphe.getMatrice()[indexSource][indexDestination]);
        String chemin = String.valueOf(InterfaceGraphe.Graphe.getMatrice()[indexSource][indexDestination]);
        //AfficherCheminPanel a = new AfficherCheminPanel(chemin, sommets, this);

    }

    /**
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
        for (Map.Entry<LCGraphe.MaillonGraphe, SommetVisuel> entry : sommets.entrySet()) {
            SommetVisuel jPanel = entry.getValue();
            if (jPanel.getBounds().intersects(new Rectangle(x, y, SOMMET_WIDTH, SOMMET_HEIGHT))) {
                return true;
            }
        }
        return false;
    }

    public void miseAJourDessin(){
        sommets.forEach((maillonGraphe, sommetVisuel) -> {
            if(maillonGraphe.equals(sommetSelectionne)){
                sommetVisuel.setCouleurCentre(COULEUR_SOMMET_SELECT);
            } else {
                sommetVisuel.setCouleurCentre(DEFAUT_COULEUR_SOMMET);
            }
            sommetVisuel.setCouleurTexte(COULEUR_TEXTE_SOMMET);
        });
        listAretes.forEach(areteVisuel -> {
            areteVisuel.setColorLine(COULEUR_ARETE);
        });
    }

    public void setDefautCouleurSommet(Color defautCouleurSommet) {
        DEFAUT_COULEUR_SOMMET = defautCouleurSommet;
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


    /**
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

}