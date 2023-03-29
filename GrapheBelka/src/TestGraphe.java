import java.io. * ;
import java.util.Scanner;
public class TestGraphe {
    private static LCGraphe newGraphe = new LCGraphe();
    private static File file = new File("fileTest.csv");
    public static void main(String [] args ) throws ExistEdgeException {
        newGraphe.addMain("Lyon", "Bloc");
        newGraphe.addMain("Paris", "M");
        newGraphe.addEdge("Lyon", "Paris", 4, 6, 5);
        try{
            newGraphe.addEdge("Lyon", "Paris", 4, 6, 5);
        } catch (ExistEdgeException e){
            System.out.println(e.getMessage());
        }
        
        System.out.println(newGraphe.existEdge("Lyon", "Paris"));
        System.out.println(newGraphe);
        Scanner ent = new Scanner(file);

        // String fichierCSV = "../../Fichiers pour Etudiants/liste-adjacence-jeuEssai.csv";
        // String ligne = "";
        // String separateur = ";";

        // try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV))) {

        //     while ((ligne = br.readLine()) != null) {

        //         String[] donnees = ligne.split(separateur);

        //         // Utilisez les données lues ici
        //         System.out.println("Colonne 1 : " + donnees[0] + " | Colonne 2 : " + donnees[1]);

        //     }

        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
}
