import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

public class DessinGraphe extends JPanel {

    private Map<LCGraphe.MaillonGraphe, JLabel> sommets;
    private JLabel sommetEnDeplacement;
    private int xPos, yPos;
    private LCGraphe graphe;

    /**
     * Constructeur de la classe DessinGraphe
     */
    DessinGraphe() {
        super();
        setLayout(null);
        sommets = new HashMap<>();


        LCGraphe.MaillonGraphe tmp = grapheConstant.Graphe.getPremier();
        int LargeurPanel = getWidth() / 2;//largeur de la panel

        


        int hauteurPanel = getHeight() / 2;
        int tailleCadre = (int) (Math.sqrt(30) * 30);

        int i = 1;

        while (tmp != null) {
            double angle = 2 * Math.PI * i / 30;
            int x = LargeurPanel + (int) (tailleCadre / 2 * Math.cos(angle));
            int y = hauteurPanel + (int) (tailleCadre / 2 * Math.cos(angle));
            ajouterSommet(tmp, 30, 30);
            tmp = tmp.getSuivant();
            i++;
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

        label.setBounds(x, y, 50, 30);


        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                sommetEnDeplacement = label;
                xPos = e.getXOnScreen();
                yPos = e.getYOnScreen();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                sommetEnDeplacement = null;
            }
        });

        label.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (!InterfaceGraphe.bloquerGraphe.isSelected()) {
                    if (sommetEnDeplacement != null) {
                        int x = e.getXOnScreen() - xPos + sommetEnDeplacement.getX();//modifie l'emplacement du jlabel
                        int y = e.getYOnScreen() - yPos + sommetEnDeplacement.getY();
                        //recupere les dimension du panel
                        int LargeurPanel = getWidth();
                        int hauteurPanel = getHeight();
                        //recupere les dimension du label sommet
                        int labelLargeur = sommetEnDeplacement.getWidth();
                        int labelhauteur = sommetEnDeplacement.getHeight();

                        if (x < 0) {
                            x = 0;
                        } else if (x > LargeurPanel - labelLargeur) {//detecte si les coordonnes de x sont superieurs
                            // a la largeur du panel moins la largeur du Jlabel
                            x = LargeurPanel - labelLargeur;
                        }

                        if (y < 0) {
                            y = 0;
                        } else if (y > hauteurPanel - labelhauteur) {//meme principe
                            y = hauteurPanel - labelhauteur;
                        }
                        sommetEnDeplacement.setLocation(x, y);//reaffecte les nouvelles coordonnes pour bouger le sommet

                        xPos = e.getXOnScreen();
                        yPos = e.getYOnScreen();

                        repaint();
                    }
                } else {
                    System.out.println("cibler ");
                }
            }
        });

        sommets.put(m, label);
        add(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // g2d.drawOval(sommetEnDeplacement.getX(), sommetEnDeplacement.getY(), 100, 100);


        for (LCGraphe.MaillonGraphe sommet1 : sommets.keySet()) {
            JLabel p1 = sommets.get(sommet1);
            int x1 = p1.getX() + p1.getWidth() / 2;
            int y1 = p1.getY() + p1.getHeight() / 2;
            int radius = p1.getWidth() / 2;

            // Dessiner un cercle au lieu d'un carré
            g2d.setColor(Color.BLACK);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fill(new Ellipse2D.Double(x1 - radius, y1 - radius, radius * 2, radius * 2));//dessiner un rond avec un rayon

            for (LCGraphe.MaillonGraphe sommet2 : sommets.keySet()) {
                JLabel p2 = sommets.get(sommet2);
                int x2 = p2.getX() + p2.getWidth() / 2;
                int y2 = p2.getY() + p2.getHeight() / 2;

                if (sommet1.estVoisin(sommet2.getNom())) {
                    g2d.drawLine(x1, y1, x2, y2);//trace la ligne
                }
            }
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 300);
    }
}