import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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

        LCGraphe.MaillonGraphe tmp = ChargementGraphe.Graphe.getPremier();
        int LargeurPanel = getWidth() / 2;
        int hauteurPanel = getHeight() / 2;
        int tailleCadre = (int) (Math.sqrt(30) * 30);

        int i = 1;

        while (tmp != null) {
            double angle = 2 * Math.PI * i / 30;
            int x = LargeurPanel + (int) (tailleCadre / 2 * Math.cos(angle));
            int y = hauteurPanel + (int) (tailleCadre / 2 * Math.cos(angle));
            ajouterSommet(tmp, 3, 3);
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

        label.setBounds(x, y, 50, 30);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
                        int x = e.getXOnScreen() - xPos + sommetEnDeplacement.getX();
                        int y = e.getYOnScreen() - yPos + sommetEnDeplacement.getY();
                        int LargeurPanel = getWidth();
                        int hauteurPanel = getHeight();
                        int labelLargeur = sommetEnDeplacement.getWidth();
                        int labelhauteur = sommetEnDeplacement.getHeight();

                        if (x < 0) {
                            x = 0;
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

        for (LCGraphe.MaillonGraphe sommet1 : sommets.keySet()) {
            JLabel p1 = sommets.get(sommet1);

            for (LCGraphe.MaillonGraphe sommet2 : sommets.keySet()) {
                JLabel p2 = sommets.get(sommet2);

                if (sommet1.estVoisin(sommet2.getNom())) {
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2,
                            p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 300);
    }
}