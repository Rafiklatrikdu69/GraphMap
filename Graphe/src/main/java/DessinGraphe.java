import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DessinGraphe extends JPanel {

    private Map<LCGraphe.MaillonGraphe, JLabel> sommets;
    private  double dist;
    private LCGraphe.MaillonGraphe sommetSelectionne;
    private String s;

    private JLabel sommetEnDeplacement;
    private int xPos, yPos;

    private JPanel panelInfoSommet;
    private  double[][] predecesseur;

    /**
     * Constructeur de la classe DessinGraphe
     */
    DessinGraphe() {
        super();
        setLayout(null);
        sommets = new HashMap<>();
        panelInfoSommet = new JPanel();
        initGraphe();
    }

    /**
     *
     */
    private  void initGraphe(){
        LCGraphe.MaillonGraphe tmp = grapheConstant.Graphe.getPremier();

        int LargeurPanel = getWidth() + 1250 / 2;//largeur de la panel
        int hauteurPanel = getHeight() + 700 / 2;
        int tailleCadre = (int) (Math.sqrt(30) * 30);
        int i = 1;


        while (tmp != null) {

            double angle = 2 * Math.PI * i / 30;
            int x = LargeurPanel + (int) (tailleCadre * Math.cos(angle));
            int y = hauteurPanel + (int) (tailleCadre * Math.sin(angle));

            ajouterSommet(tmp, x, y);
            i++;
            tmp = tmp.getSuivant();

        }
    }

    /**
     * Méthode pour ajouter un sommet avec ses coordonnées
     *
     * @param m Le nom du sommet
     * @param x La coordonnée X du sommet
     * @param y La coordonnée Y du sommet
     */
    private void ajouterSommet(LCGraphe.MaillonGraphe m, int x, int y) {
        JLabel label = new JLabel(m.getNom());

        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 30, 20);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                super.mousePressed(e);
                sommetEnDeplacement = label;
                xPos = e.getXOnScreen();
                yPos = e.getYOnScreen();
                repaint();

            }

            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                sommetSelectionne = m;
                panelInfoSommet.setLayout(new BoxLayout(panelInfoSommet, BoxLayout.Y_AXIS));
                panelInfoSommet.removeAll();


                AlgoPlusCourtsChemins algoPlusCourtsChemins = new AlgoPlusCourtsChemins();
                predecesseur = grapheConstant.Graphe.floydWarshall();
                System.out.println("sommet selectionner : "+sommetSelectionne.getNom());
                System.out.println("sommet choisis : "+algoPlusCourtsChemins.getChoixSommet());
                rechercherChemin(sommetSelectionne.getNom(),algoPlusCourtsChemins.getChoixSommet());


                JLabel nom = new JLabel("Nom du sommet : " + sommetSelectionne.getNom());
                JLabel type = new JLabel("Type : " + sommetSelectionne.getType());
                System.out.println("Le nom du dispensaire " + sommetSelectionne.getNom() + " Son type : " + sommetSelectionne.getType());


                panelInfoSommet.add(nom);
                panelInfoSommet.add(type);
                JLabel infoSommet = new JLabel("Info des sommets voisins:");
                panelInfoSommet.add(infoSommet);

                Iterator<LCGraphe.MaillonGraphe> iterator = sommets.keySet().iterator();
                while (iterator.hasNext()) {
                    LCGraphe.MaillonGraphe sommet = iterator.next();
                    String nomSommet = sommet.getNom();

                    if (sommetSelectionne.estVoisin(nomSommet)) {
                        JLabel sommetVoisin = new JLabel("Nom : " + nomSommet + " Type : " + sommet.getType());
                        panelInfoSommet.add(sommetVoisin);

                        LCGraphe.MaillonGrapheSec sommetVoisi = sommet.lVois;
                        String nomSommetSelectionne = sommetSelectionne.getNom();
                        while (sommetVoisi != null) {
                            if (sommetVoisi.getDestination().equals(nomSommetSelectionne)) {
                                String infoText = "Distance: " + sommetVoisi.getDistance() + " Durée: " + sommetVoisi.getDuree() + " Fiabilité: " + sommetVoisi.getFiabilite();
                                JLabel info = new JLabel(infoText);
                                panelInfoSommet.add(info);
                            }
                            sommetVoisi = sommetVoisi.suiv;
                        }
                    }
                }



                panelInfoSommet.setBorder(BorderFactory.createTitledBorder("info"));


                add(panelInfoSommet);
                panelInfoSommet.setBounds(10, 20, 300, 700);

                panelInfoSommet.validate();
                panelInfoSommet.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                sommetEnDeplacement = null;
                repaint();
            }
        });

        label.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (!InterfaceGraphe.bloquerGraphe.isSelected()) {
                    if (sommetEnDeplacement != null) {
                        int x = e.getXOnScreen() - xPos + sommetEnDeplacement.getX();
                        int y = e.getYOnScreen() - yPos + sommetEnDeplacement.getY();
                        int LargeurPanel = getWidth();
                        int hauteurPanel = getHeight();
                        int labelLargeur = sommetEnDeplacement.getWidth();
                        int labelhauteur = sommetEnDeplacement.getHeight();
                        int infoSommetWidth = panelInfoSommet.getWidth();


                        if (x < infoSommetWidth) {
                            x = infoSommetWidth;
                        } else if (x > LargeurPanel - labelLargeur) {
                            x = LargeurPanel - labelLargeur;
                        }

                        if (y < 0) {
                            y = 0;
                        } else if (y > hauteurPanel - labelhauteur) {
                            y = hauteurPanel - labelhauteur;
                        }


                        sommetEnDeplacement.setLocation(x, y);

                        xPos = e.getXOnScreen();
                        yPos = e.getYOnScreen();
                    }
                } else {
                    // ...
                }

                revalidate();

            }
        });

        sommets.put(m, label);
        add(label);
        repaint();
    }

    /**
     *
     * @param g the <code>Graphics</code> object to protect
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int cptArrete = 0;
        int radius;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<LCGraphe.MaillonGraphe> sommetsList = new ArrayList<>(sommets.keySet());
        int size = sommetsList.size();

        LCGraphe.MaillonGraphe sommet1;
        LCGraphe.MaillonGraphe sommet2;
        JLabel p1;
        JLabel p2;
        int x1, y1, x2, y2;

        for (int i = 0; i < size; i++) {
            sommet1 = sommetsList.get(i);
            p1 = sommets.get(sommet1);
            x1 = p1.getX() + p1.getWidth() / 2;
            y1 = p1.getY() + p1.getHeight() / 2;
            radius = p1.getWidth() / 2;

            g2d.fill(new Ellipse2D.Double(x1 - radius, y1 - radius, radius * 2, radius * 2));

            for (int j = 0; j < size; j++) {
                sommet2 = sommetsList.get(j);
                p2 = sommets.get(sommet2);
                x2 = p2.getX() + p2.getWidth() / 2;
                y2 = p2.getY() + p2.getHeight() / 2;

                if (sommetSelectionne == sommet2) {
                    g2d.setColor(Color.RED);
                    g2d.fill(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
                } else {
                    g2d.setColor(Color.BLACK);
                    g2d.fill(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
                }

                if (sommet1.estVoisin(sommet2.getNom())) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(x1, y1, x2, y2);
                    cptArrete++;
                }
            }
        }

        repaint();
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 500);
    }




    public void rechercherChemin(String source ,String destination) {


        // Vérifie si les sommets saisis existent dans le graphe
        if (!grapheConstant.Graphe.indexSommet().containsKey(source) || !grapheConstant.Graphe.indexSommet().containsKey(destination)) {
            System.out.println("Les sommets saisis ne sont pas valides.");
            return;
        }

        int indexSource = grapheConstant.Graphe.indexSommet().get(source);
        int indexDestination = grapheConstant.Graphe.indexSommet().get(destination);

        // Vérifie si un chemin existe entre les sommets saisis
        if (grapheConstant.Graphe.getPredecesseurs()[indexSource][indexDestination] == -1) {
            System.out.println("Aucun chemin trouvé entre " + source + " et " + destination);
            return;
        }

        System.out.println("Chemin de " + source + " à " + destination + ":");
       grapheConstant.Graphe.afficherChemin(indexSource, indexDestination);
        System.out.println("Distance : " + grapheConstant.Graphe.getMatrice()[indexSource][indexDestination]);
         s =""+ grapheConstant.Graphe.getMatrice()[indexSource][indexDestination];

        AfficherChemin a = new AfficherChemin(s);
    }
    public  String getDistances(){
        return this.s;
    }




}
