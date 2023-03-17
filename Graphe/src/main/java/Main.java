public class Main {
    private static Graphe newGraphe = new Graphe("G1");
    public static void main(String [] args ){
        newGraphe.ajoutSommet("S1", "M");
        newGraphe.ajoutSommet("S2", "O");
        newGraphe.ajoutSommet("S3", "O");
        newGraphe.ajoutSommet("S4", "M");
        newGraphe.ajoutSuccesseur("S1","S2", 4, 6, 5);
        newGraphe.ajoutSuccesseur("S1","S3", 8, 9, 10);
        newGraphe.ajoutSuccesseur("S1","S4", 4, 6, 3);
        System.out.println("Les sommets du graphe "+ newGraphe.getNom()+ " sont :");
        newGraphe.afficherSommet();
        System.out.println("Les successeurs du sommet S1 sont :");
        newGraphe.afficherSuccesseur("S1");
    }
}
