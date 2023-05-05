import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.lang.String;

public class Main extends Exception {
    private static LCGraphe newGraphe = new LCGraphe();

    public static void main(String[] args) {

        newGraphe.LectureFichier();

        System.out.println(newGraphe.toString());

    }
}

