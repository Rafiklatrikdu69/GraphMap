import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.Map;

public class DessinGraphe extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        myPaintComponent(g);
    }

    private Map<LCGraphe.MaillonGraphe, JLabel> sommets;
    private JLabel sommetEnDeplacement;
    private int xPos, yPos;

    /**
     *
     */
    DessinGraphe() {
        super();

        setLayout(null);
        sommets = new HashMap<>();


    }

    /**
     * @param m
     * @param x
     * @param y
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
                xPos = e.getX();
                yPos = e.getY();

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
                //  if (bloquerGraphe.isSelected()) {

                if (sommetEnDeplacement != null) {
                    int x = e.getXOnScreen() - xPos;
                    int y = e.getYOnScreen() - yPos;
                    sommetEnDeplacement.setLocation(x, y);

                    repaint();
                }
                // }
            }
        });

        sommets.put(m, label);
        add(label);
    }

    /**
     * @param g the <code>Graphics</code> object to protect
     */

    protected void myPaintComponent(Graphics g) {
        Graphe();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        //double[][] predecesseurs = Graphe.floydWarshall("S1", "S8");

        // while (!f.estVide()) {
        //   String def = f.defiler();

        for (LCGraphe.MaillonGraphe sommet1 : sommets.keySet()) {
            JLabel p1 = sommets.get(sommet1);

            for (LCGraphe.MaillonGraphe sommet2 : sommets.keySet()) {
                JLabel p2 = sommets.get(sommet2);

                if (sommet1.estVoisin(sommet2.getNom())/*sommet1.getNom().equals("S1") && sommet2.getNom().equals("S8")*/) {
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2, p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2);
                } /*else {
                            g2d.setColor(Color.BLACK);
                            g2d.setStroke(new BasicStroke(1));
                            g2d.drawLine(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2, p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2);
                        }*/
            }
        }
    }

    public void Graphe() {

        LCGraphe.MaillonGraphe tmp = ChargementGraphe.Graphe.getPremier();

        int tailleCadre = (int) (Math.sqrt(30) * 100);
        int xCentre = 600 / 2;
        int yCentre = 50 / 2;
        int i = 1;
        while (tmp != null) {

            double angle = 2 * Math.PI * i / 30;
            int x = xCentre + (int) (tailleCadre / 2 * Math.cos(angle));
            int y = yCentre + (int) (tailleCadre / 2 * Math.sin(angle));

            ajouterSommet(tmp, x, y);
            //System.out.println("test");
            tmp = tmp.getSuivant();
            i++;
        }
        int preferredWidth = tailleCadre;
        int preferredHeight = tailleCadre;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        revalidate();
    }


}