package Interface;

import LCGraphe.Graphe;

import javax.swing.*;
import java.awt.*;

public class SommetVisuel extends JPanel {

    private float opacity;

    private Graphe.MaillonGraphe centre;
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
    public SommetVisuel(Graphe.MaillonGraphe centre, int rayon) {
        opacity = 1.0F;
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
        Graphics2D g2d = (Graphics2D) g.create();
        super.paintComponent(g2d);
        dessinerRond(g2d);
        g2d.dispose();
    }
    
    /**
     * Cette methode permet de dessiner un rond avec fillOval
     *
     * @param g
     */
    private void dessinerRond(Graphics2D g2d) {
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaComposite);
        x = getWidth() / 2;
        y = getHeight() / 2;
        setOpaque(false);
        g2d.setColor(couleurCentre);
        g2d.fillOval(x - rayon, y - rayon, rayon * 2, rayon * 2);//dessine le cercle
        g2d.setColor(couleurTexte);
        Font font = new Font("Arial", Font.BOLD, 11);
        g2d.setFont(font);
     
        int largeurNom = g2d.getFontMetrics().stringWidth(centre.getNom());
        int xNom = x - largeurNom / 2;
        int yNom = y + 5;
        g2d.drawString(centre.getNom(), xNom, yNom);//ecrit le nom du sommet
    }
    
    /**
     *
     * @param couleur
     */

    public void setCouleurCentre(Color couleur) {
        this.couleurCentre = couleur;
        repaint();
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    /**
     *
     * @return
     */
    public Graphe.MaillonGraphe getSommetGraphe() {
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
    public String getSommet(){
        return this.centre.getNom();
    }
}
