import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class DessinGrapheRouge extends DessinGraphe {
    protected static final Color SELECTED_LABEL_COLOR = Color.RED;

    public DessinGrapheRouge() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int radius;
       // g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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

            g.setColor(Color.RED);
            g.fillOval(x1 - radius, y1 - radius, radius * 2, radius * 2);

            for (int j = 0; j < size; j++) {
                sommet2 = sommetsList.get(j);
                p2 = sommets.get(sommet2);
                x2 = p2.getX() + p2.getWidth() / 2;
                y2 = p2.getY() + p2.getHeight() / 2;

                if (sommetSelectionne == sommet2) {
                    g.setColor(SELECTED_LABEL_COLOR);
                    g.fillOval(x2 - radius, y2 - radius, radius * 2, radius * 2);
                } else {
                    g.setColor(BACKGROUND_COLOR);
                    g.fillOval(x2 - radius, y2 - radius, radius * 2, radius * 2);
                }

                if (sommet1.estVoisin(sommet2.getNom())) {
                    g.setColor(Color.BLACK);
                    g.drawLine(x1, y1, x2, y2);
                }
            }
        }
    }
}
