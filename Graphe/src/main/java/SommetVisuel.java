import javax.swing.*;
import java.awt.*;

public class SommetVisuel extends JPanel {

    private LCGraphe.MaillonGraphe centre;
    private int rayon;
    private int x;
    private int y;
    private Color couleurCentre;
    private Color couleurTexte;
    
    /**
     *
     * @param centre
     * @param rayon
     */
    public SommetVisuel(LCGraphe.MaillonGraphe centre, int rayon) {
        this.rayon = rayon;
        this.centre = centre;
        setOpaque(false);
    }
    
    /**
     *
     * @param g the <code>Graphics</code> object to protect
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dessinerRond(g);
    }
    
    /**
     * Cette methode permet de dessiner un rond avec fillOval
     *
     * @param g
     */
    private void dessinerRond(Graphics g) {
        x = getWidth() / 2;
        y = getHeight() / 2;
        setOpaque(false);
        g.setColor(couleurCentre);
        g.fillOval(x - rayon, y - rayon, rayon * 2, rayon * 2);//dessine le cercle
        g.setColor(couleurTexte);
        Font font = new Font("Arial", Font.BOLD, 11);
        g.setFont(font);
        int largeurNom = g.getFontMetrics().stringWidth(centre.getNom());
        int xNom = x - largeurNom / 2;
        int yNom = y + 5;
        g.drawString(centre.getNom(), xNom, yNom);//ecrit le nom du sommet
    }
    
    /**
     *
     * @param couleur
     */

    public void setCouleurCentre(Color couleur) {
        this.couleurCentre = couleur;
        repaint();
    }
    
    /**
     *
     * @return
     */
    public LCGraphe.MaillonGraphe getSommetGraphe() {
        return centre;
    }
    
    /**
     *
     * @return
     */
    public Color getCouleurCentre() {
        return couleurCentre;
    }
    
    /**
     * Cette methode recupere le centre du cercle
     *
     * @return centre du cercle
     */
    public Point getCentreDuCercle() {
        Point location = this.getLocation();
        Dimension size = this.getSize();
        return new Point(location.x + size.width / 2, location.y + size.height / 2);
    }

    public void setCouleurTexte(Color couleurTexte) {
        this.couleurTexte = couleurTexte;
    }
}
