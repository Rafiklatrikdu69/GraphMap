import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Map;

public class AffichagePlusCourtsChemin extends DessinGraphe {
    protected static final Color SELECTED_LABEL_COLOR = Color.RED;
    private Map<LCGraphe.MaillonGraphe, JLabel> sommets;

    public AffichagePlusCourtsChemin(Map<LCGraphe.MaillonGraphe, JLabel> sommets) {
        super();
        this.sommets = sommets;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int radius;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (LCGraphe.MaillonGraphe sommet : sommets.keySet()) {
            JLabel label = sommets.get(sommet);
            int x = label.getLocation().x + label.getWidth() / 2;
            int y = label.getLocation().y + label.getHeight() / 2;
            radius = label.getWidth() / 2;

            g2d.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
        }

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.0f));

        for (LCGraphe.MaillonGraphe sommet1 : sommets.keySet()) {
            JLabel p1 = sommets.get(sommet1);
            int x1 = p1.getLocation().x + p1.getWidth() / 2;
            int y1 = p1.getLocation().y + p1.getHeight() / 2;

            for (LCGraphe.MaillonGraphe sommet2 : sommets.keySet()) {
                JLabel p2 = sommets.get(sommet2);
                int x2 = p2.getLocation().x + p2.getWidth() / 2;
                int y2 = p2.getLocation().y + p2.getHeight() / 2;

                if (sommet1.estVoisin(sommet2.getNom())) {
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2.0f));

        for (int i = 0; i < grapheConstant.graphe.chemin.size() - 1; i++) {
            int sommetCourant = grapheConstant.graphe.chemin.get(i);
            int sommetSuivant = grapheConstant.graphe.chemin.get(i + 1);
            System.out.println(grapheConstant.graphe.getNomSommet(sommetCourant));
            JLabel p3 = sommets.get(grapheConstant.graphe.getCentre(grapheConstant.graphe.getNomSommet(sommetCourant)));
            JLabel p4 = sommets.get(grapheConstant.graphe.getCentre(grapheConstant.graphe.getNomSommet(sommetSuivant)));
            System.out.println(sommets.get(grapheConstant.graphe.getNomSommet(sommetSuivant)));

            int x1 = p3.getLocation().x + p3.getWidth() / 2;
            int y1 = p3.getLocation().y + p3.getHeight() / 2;
            int x2 = p4.getLocation().x + p4.getWidth() / 2;
            int y2 = p4.getLocation().y + p4.getHeight() / 2;

            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}
