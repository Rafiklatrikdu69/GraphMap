package Interface;


import java.awt.*;

public class AreteVisuel {
    private Point positionCentre1;//position du centre 1
    private Point positionCentre2;//position du centre 2
    private SommetVisuel sommetVisuel1;//sommet visuel 1
    private SommetVisuel sommetVisuel2;//sommet visuel 2
    private Color couleurLigne;//couleur de la ligne
    private float opacity;
    private int tailleTrait;
    
    /**
     *Une Arete est composé de deux sommets visuels qui sont reliés
     *
     * @param sommetVisuel1
     * @param sommetVisuel2
     */
    AreteVisuel(SommetVisuel sommetVisuel1, SommetVisuel sommetVisuel2){
        this.tailleTrait = 2;
        this.opacity = 1.0F;
        this.positionCentre1 = sommetVisuel1.getCentreDuCercle();
        this.positionCentre2 = sommetVisuel2.getCentreDuCercle();
        this.sommetVisuel1 = sommetVisuel1;
        this.sommetVisuel2 = sommetVisuel2;
        this.couleurLigne = Color.BLACK;
    }
    /**Getters/Setters**/
    
    /**
     *
     * @return positionCentre1
     */
    public Point getPositionCentre1() {
        return positionCentre1;
    }
    
    /**
     *
     * @return positionCentre2
     */
    public Point getPositionCentre2() {
        return positionCentre2;
    }
    
    /**
     *
     * @return sommetVisuel1
     */
    public SommetVisuel getSommetVisuel1() {
        return sommetVisuel1;
    }
    
    /**
     *
     * @return sommetVisuel2
     */
    public SommetVisuel getSommetVisuel2() {
        return sommetVisuel2;
    }
    
    /**
     *
     * @return couleurLigne
     */
    public Color getCouleurLigne() {
        return couleurLigne;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public float getOpacity() {
        return opacity;
    }

    /**
     *
     * @param positionCentre1
     */
    public void setPositionCentre1(Point positionCentre1) {
        this.positionCentre1 = positionCentre1;
    }
    
    /**
     *
     * @param positionCentre2
     */
    public void setPositionCentre2(Point positionCentre2) {
        this.positionCentre2 = positionCentre2;
    }
    
    /**
     *
     * @param couleurLigne
     */
    public void setCouleurLigne(Color couleurLigne) {
        this.couleurLigne = couleurLigne;
    }

    public int getTailleTrait() {
        return tailleTrait;
    }
    

    public void setTailleTrait(int tailleTrait) {
        this.tailleTrait = tailleTrait;
    }
    

}
