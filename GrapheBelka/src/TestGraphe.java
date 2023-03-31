import java.util.Scanner;
import java.io.*;

public class TestGraphe {
    private static LCGraphe newGraphe = new LCGraphe();
    public static void main(String [] args ) throws ExistEdgeException , NotExistMainException{
        newGraphe.ajoutSommet("Lyon", "Bloc");
        newGraphe.ajoutSommet("Paris", "JSP");
        newGraphe.ajoutSommet("Pagnoz", "Clinique");
        newGraphe.ajoutVoisin("Lyon", "Paris", 4, 6, 5);
        
        try{
            // newGraphe.ajoutVoisin("Lyon", "ZEUB", 4, 6, 5);
            newGraphe.ajoutVoisin("Lyon", "Pagnoz", 3, 3, 2);
            newGraphe.ajoutVoisin("Lyon", "Paris", 4, 6, 5);
        } catch (ExistEdgeException e){
            System.out.println(e.getMessage());
        }
        // newGraphe.modifVoisin("Paris", "Lyon", 0, 0, 0);
        System.out.println(newGraphe.existeVoisin("Lyon", "Paris"));
        System.out.println(newGraphe.getVoisins("Pagnoz").get(0).getDestination());
        // System.out.println();
        System.out.println(newGraphe.getCentre("Lyon").afficherVoisins());
        // System.out.println(newGraphe.tousLesCentres());
    }
}
