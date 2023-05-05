import java.lang.String;

public class Main extends Exception {
    private static LCGraphe newGraphe = new LCGraphe();

    public static void main(String[] args) throws NotExistMainException {
        newGraphe.chargementFichier();

        System.out.println(newGraphe.existeVoisin("S1", "S3"));
        System.out.println();
        newGraphe.affiche();

    }
}

