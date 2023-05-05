import java.lang.String;

public class Main extends Exception {
    private static LCGraphe newGraphe = new LCGraphe();

    public static void main(String[] args) throws NotExistMainException {
        newGraphe.chargementFichier();
        System.out.println(newGraphe.plusCourtCheminDijkstrat("S1", "S6"));

        System.out.println(newGraphe.getCentre("S6").voisinsToString());
    }
}

