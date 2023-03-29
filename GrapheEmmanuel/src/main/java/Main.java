import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
        // System.out.println("Les sommets du graphe "+ newGraphe.getNom()+ " sont: "+ newGraphe.getSommets());
        System.out.println("S1 a "+ newGraphe.getNombreVoisins("S1")+" voisins et sont :");
        newGraphe.afficherVoisins("S1");
        System.out.println("Le nombre de sommets du graphe: "+ newGraphe.getNombreSommets());
        System.out.println("Le plus court chemin entre S1 et S3: "+ newGraphe.plusCourtChemin("S1", "S3"));
        
    }
}

class LectureFichierCSV {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("chemin/vers/mon-fichier.csv");
        CSVParser parser = new CSVParser(fileReader, CSVFormat.DEFAULT);
        for (CSVRecord record : parser) {
            String valeurDuChamp1 = record.get(0);
            String valeurDuChamp2 = record.get(1);
            // etc.
            System.out.println("Enregistrement : " + record.getRecordNumber());
            System.out.println("Champ 1 : " + valeurDuChamp1);
            System.out.println("Champ 2 : " + valeurDuChamp2);
            System.out.println();
        }
        parser.close();
        fileReader.close();
    }
}