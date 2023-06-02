import javax.swing.*;
import java.awt.*;

public class SommetVisuel extends JPanel {

    private LCGraphe.MaillonGraphe centre;
    private int rayon;
    private int x;
    private int y;
    private Color couleurCentre;
    private Color couleurTexte;
    public SommetVisuel(LCGraphe.MaillonGraphe centre, int rayon) {
        this.rayon = rayon;
        this.centre = centre;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dessinerRond(g);
    }
    private void dessinerRond(Graphics g) {
        x = getWidth() / 2;
        y = getHeight() / 2;
        setOpaque(false);
        g.setColor(couleurCentre);
        g.fillOval(x - rayon, y - rayon, rayon * 2, rayon * 2);
        g.setColor(couleurTexte);
        Font font = new Font("Arial", Font.BOLD, 11);
        g.setFont(font);
        int largeurNom = g.getFontMetrics().stringWidth(centre.getNom());
        int xNom = x - largeurNom / 2;
        int yNom = y + 5;
        g.drawString(centre.getNom(), xNom, yNom);
    }

    public void setCouleurCentre(Color couleur) {
        this.couleurCentre = couleur;
        repaint();
    }
    public LCGraphe.MaillonGraphe getCentreGraphe() {
        return centre;
    }
    public Color getCouleurCentre() {
        return couleurCentre;
    }
    public Point getCentreDuCercle() {
        Point location = this.getLocation();
        Dimension size = this.getSize();
        return new Point(location.x + size.width / 2, location.y + size.height / 2);
    }

    public void setCouleurTexte(Color couleurTexte) {
        this.couleurTexte = couleurTexte;
    }
}
