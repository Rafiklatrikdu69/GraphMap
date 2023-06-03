import java.awt.*;

public class AreteVisuel {
    private Point positionCentre1;
    private Point positionCentre2;
    private SommetVisuel sommetVisuel1;
    private SommetVisuel sommetVisuel2;
    private Color couleurLigne;
    AreteVisuel(SommetVisuel sommetVisuel1, SommetVisuel sommetVisuel2){
        this.positionCentre1 = sommetVisuel1.getCentreDuCercle();
        this.positionCentre2 = sommetVisuel2.getCentreDuCercle();
        this.sommetVisuel1 = sommetVisuel1;
        this.sommetVisuel2 = sommetVisuel2;
        this.couleurLigne = Color.BLACK;
    }
    
    /**
     *
     * @return
     */
    public Point getPositionCentre1() {
        return positionCentre1;
    }
    
    /**
     *
     * @return
     */
    public Point getPositionCentre2() {
        return positionCentre2;
    }
    
    /**
     *
     * @return
     */
    public SommetVisuel getSommetVisuel1() {
        return sommetVisuel1;
    }
    
    /**
     *
     * @return
     */
    public SommetVisuel getSommetVisuel2() {
        return sommetVisuel2;
    }
    
    /**
     *
     * @return
     */
    public Color getCouleurLigne() {
        return couleurLigne;
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
}
