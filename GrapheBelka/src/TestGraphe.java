import java.util.Scanner;
import java.io.*;

public class TestGraphe {
    private static LCGraphe newGraphe = new LCGraphe();
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
    }
}
