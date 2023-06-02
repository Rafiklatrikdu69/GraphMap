import java.awt.*;

public class AreteVisuel {
    private Point positionCentre1;
    private Point positionCentre2;
    private SommetVisuel sommetVisuel1;
    private SommetVisuel sommetVisuel2;
    private Color colorLine;
    AreteVisuel(SommetVisuel sommetVisuel1, SommetVisuel sommetVisuel2){
        this.positionCentre1 = sommetVisuel1.getCentreDuCercle();
        this.positionCentre2 = sommetVisuel2.getCentreDuCercle();
        this.sommetVisuel1 = sommetVisuel1;
        this.sommetVisuel2 = sommetVisuel2;
        this.colorLine = Color.BLACK;
    }

    public Point getPositionCentre1() {
        return positionCentre1;
    }
    public Point getPositionCentre2() {
        return positionCentre2;
    }
    public SommetVisuel getSommetVisuel1() {
        return sommetVisuel1;
    }
    public SommetVisuel getSommetVisuel2() {
        return sommetVisuel2;
    }
    public Color getColorLine() {
        return colorLine;
    }
    public void setPositionCentre1(Point positionCentre1) {
        this.positionCentre1 = positionCentre1;
    }
    public void setPositionCentre2(Point positionCentre2) {
        this.positionCentre2 = positionCentre2;
    }
    public void setColorLine(Color colorLine) {
        this.colorLine = colorLine;
    }
}
