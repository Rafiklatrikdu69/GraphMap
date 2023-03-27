public class Main {
    private static Graphe newGraphe = new Graphe("G1");
    public static void main(String [] args ){
        newGraphe.ajoutSommet("S1", "M");
        newGraphe.ajoutSommet("S2", "O");
        newGraphe.ajoutSommet("S3", "O");
        newGraphe.ajoutSommet("S4", "M");
        newGraphe.ajoutSuccesseur("S1","S2", 4, 6, 5);
        newGraphe.ajoutSuccesseur("S1","S2", 4, 6, 5);
        newGraphe.ajoutSuccesseur("S1","S3", 8, 9, 10);
        newGraphe.ajoutSuccesseur("S1","S4", 4, 6, 3);
        System.out.print("Les sommets du graphe "+ newGraphe.getNom()+ " sont: ");
        newGraphe.afficherSommets();
        System.out.println("S1 a "+ newGraphe.getNombreVoisins("S1")+" voisins et sont :");
        newGraphe.afficherSuccesseur("S1");
        System.out.println("Le nombre de sommets du graphe: "+ newGraphe.getNombreSommets());
        // System.out.println(newGraphe.getSommet("S1"));
        System.out.println(newGraphe.plusCourtChemin("S1", "S2"));
    }
}
