import java.lang.String;

public class MainMenuConsole extends Exception {
    private static LCGraphe newGraphe = new LCGraphe();

    public static void main(String[] args){
        newGraphe.chargementFichier();

        System.out.println(newGraphe.plusCourtCheminDijkstraFiabilite("S1", "S2"));
    }
}

