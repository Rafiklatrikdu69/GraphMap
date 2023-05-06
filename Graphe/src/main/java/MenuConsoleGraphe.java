import java.io.IOException;
import java.lang.String;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Scanner;

public class MenuConsoleGraphe{
    private static LCGraphe newGraphe = new LCGraphe();

    public static void main(String[] args){
        InterfaceGraphe interfaceGraphe = new InterfaceGraphe();
        
        newGraphe.chargementFichier();
      
       

        Scanner scanner = new Scanner(System.in);
        int option;
        boolean sortie = false;
            do {
                clearScreen();
                System.out.println("===========================================");
                System.out.println("|              MENU PRINCIPAL             |");
                System.out.println("============================================");
                System.out.println("|  " + ConsoleColors.YELLOW_BOLD + "1. Afficher le contenu du graphe       " + ConsoleColors.RESET + "|");
                System.out.println("|  " + ConsoleColors.PURPLE_BOLD + "2. Afficher toutes les arêtes          " + ConsoleColors.RESET + "|");
                System.out.println("|  " + ConsoleColors.GREEN_BOLD + "3. Sélectionner un dispensaire         " + ConsoleColors.RESET +"|");
                System.out.println("|  " + ConsoleColors.BLUE_BOLD + "4. Option 4                            " + ConsoleColors.RESET + "|");
                System.out.println("|  " + ConsoleColors.CYAN_BOLD + "5. Option 5                            " + ConsoleColors.RESET + "|");
                System.out.println("|  " + ConsoleColors.RED_BOLD + "6. Option 6                            " + ConsoleColors.RESET + "|");
                System.out.println("|  " + ConsoleColors.GRAY_BOLD + "7. Quitter                             " + ConsoleColors.RESET + "|");
                System.out.println("============================================");
                System.out.print("Saisir votre choix : ");

                if (scanner.hasNextInt()) {
                    option = scanner.nextInt();
                    scanner.nextLine();
                    switch (option) {
                        case 1 -> {
                            LinkedList<LCGraphe.MaillonGraphe> tousLesCentresList = newGraphe.tousLesCentresToList();
                            tousLesCentresList.forEach(Centre -> {
                                StringBuilder stringVoisin = new StringBuilder();
                                LinkedList<LCGraphe.MaillonGrapheSec> voisinsCentre = Centre.voisinsToList();
                                stringVoisin.append("[");
                                for (int i = 0; i < voisinsCentre.size(); i++) {
                                    if (i == voisinsCentre.size() - 1) {
                                        stringVoisin.append(voisinsCentre.get(i).getDestination());
                                    } else {
                                        stringVoisin.append(voisinsCentre.get(i).getDestination()).append(",");
                                    }
                                }
                                stringVoisin.append("]");
                                System.out.println(Centre.getNom() + " [" + Centre.getType() + "], Voisins : " + stringVoisin.toString());
                            });
                            pressEnterToContinue(scanner);
                        }
                        case 2 -> {
                            System.out.println(newGraphe);
                            pressEnterToContinue(scanner);
                        }
                        case 3 -> {
                            System.out.print("Veillez saisir un dispensaire (entre S1 et S30) : ");
                            String optionDispensaire;
                            Scanner scannerDispensaire = new Scanner(System.in);
                            optionDispensaire = scannerDispensaire.next();
                            scannerDispensaire.nextLine();
                            boolean sortieAnnexe = false;
                            if (!newGraphe.existeCentre(optionDispensaire)) {
                                System.out.println("Le dispensaire " + optionDispensaire + " n'existe pas !");
                            } else {
                                LCGraphe.MaillonGraphe dispensaire = newGraphe.getCentre(optionDispensaire);
                                int optionFonctionDispensaire;
                                Scanner scannerFonctionDispensaire = new Scanner(System.in);
                                do {
                                    clearScreen();
                                    System.out.println("============================================");
                                    System.out.println("|              Dispensaire " + optionDispensaire + "              |");
                                    System.out.println("=============================================");
                                    System.out.println("|  " + ConsoleColors.YELLOW_BOLD + "1. " + ConsoleColors.RESET + "Afficher le type de dispensaire      " + ConsoleColors.RESET + "|");
                                    System.out.println("|  " + ConsoleColors.PURPLE_BOLD + "2. " + ConsoleColors.RESET + "Afficher ses voisins direct          " + ConsoleColors.RESET + "|");
                                    System.out.println("|  " + ConsoleColors.GREEN_BOLD + "3. " + ConsoleColors.RESET + "Afficher un voisin direct            " + ConsoleColors.RESET + "|");
                                    System.out.println("|  " + ConsoleColors.BLUE_BOLD + "4. " + ConsoleColors.RESET + "Afficher le chemin le plus fiable    " + ConsoleColors.RESET + "|");
                                    System.out.println("|  " + ConsoleColors.GRAY_BOLD + "5. " + ConsoleColors.RESET + "Retourner au menu principal          " + ConsoleColors.RESET + "|");
                                    System.out.println("=============================================");
                                    System.out.print("Saisir votre choix : ");
                                    if (scannerFonctionDispensaire.hasNextInt()) {
                                        optionFonctionDispensaire = scannerFonctionDispensaire.nextInt();
                                        scannerFonctionDispensaire.nextLine();
                                        switch (optionFonctionDispensaire) {
                                            case 1 -> {
                                                System.out.println("\nType : " + dispensaire.getType() + "\n");
                                                pressEnterToContinue(scannerFonctionDispensaire);
                                            }
                                            case 2 -> {
                                                System.out.println("\n" + dispensaire.voisinsToString());
                                                pressEnterToContinue(scannerFonctionDispensaire);
                                            }
                                            case 3 -> {
                                                System.out.print("Veillez saisir un dispensaire qui est voisin à "+dispensaire.getNom()+" (entre S1 et S30) : ");
                                                String optionDispensaire2;
                                                Scanner scannerDispensaire2 = new Scanner(System.in);
                                                optionDispensaire2 = scannerDispensaire2.next();
                                                scannerDispensaire2.nextLine();
                                                if (!newGraphe.existeCentre(optionDispensaire2)) {
                                                    System.out.println("Le dispensaire " + optionDispensaire2 + " n'existe pas !");
                                                } else if (dispensaire.estVoisin(optionDispensaire2)) {
                                                    LCGraphe.MaillonGrapheSec voisinOfDispensaire = dispensaire.getVoisin(optionDispensaire2);
                                                    System.out.println(dispensaire.getNom()+"-"+voisinOfDispensaire.toString());
                                                } else {
                                                    System.out.println(dispensaire.getNom()+" n'est pas voisin avec "+ optionDispensaire2);
                                                }
                                                pressEnterToContinue(scannerFonctionDispensaire);
                                            }
                                            case 4 -> {
                                                System.out.print("Veillez saisir un autre dispensaire (entre S1 et S30) : ");
                                                String optionDispensaire2;
                                                Scanner scannerDispensaire2 = new Scanner(System.in);
                                                optionDispensaire2 = scannerDispensaire2.next();
                                                scannerDispensaire2.nextLine();
                                                if (!newGraphe.existeCentre(optionDispensaire2)) {
                                                    System.out.println("Le dispensaire " + optionDispensaire2 + " n'existe pas !");
                                                } else {
                                                    LinkedHashMap<String, Double> plusCourtChemin = newGraphe.plusCourtCheminDijkstraFiabilite(dispensaire.getNom(),optionDispensaire2);
                                                    StringBuilder affichage = new StringBuilder();
                                                    affichage.append("\n");
                                                    int compteur = 0;
                                                    for (Map.Entry<String, Double> entry : plusCourtChemin.entrySet()) {
                                                        if (compteur == plusCourtChemin.size()-1){
                                                            affichage.append(entry.getKey());
                                                            affichage.append("\nLe chemin a une fiabilité de ");
                                                            double fiab = entry.getValue()*100;
                                                            DecimalFormat df = new DecimalFormat("#.##");
                                                            double nombreArrondi = Double.parseDouble(df.format(fiab).replace(",", "."));
                                                            affichage.append(nombreArrondi).append("%");
                                                        } else {
                                                            affichage.append(entry.getKey()).append(" -> ");
                                                        }
                                                        compteur++;
                                                    }
                                                    affichage.append("\n");
                                                    System.out.println(affichage.toString());
                                                }
                                                pressEnterToContinue(scannerFonctionDispensaire);
                                            }
                                            case 5 -> sortieAnnexe = true;
                                        }
                                    } else {
                                        scannerFonctionDispensaire.nextLine();
                                        System.out.println(ConsoleColors.RED_BOLD + "Erreur : entrée invalide" + ConsoleColors.RESET);
                                        pressEnterToContinue(scannerFonctionDispensaire);
                                    }
                                } while (!sortieAnnexe);
                            }
                        }
                        case 4 -> {
                            System.out.println("Vous avez choisi l'option 4");
                            // Insérer ici le code pour l'option 4
                            pressEnterToContinue(scanner);
                        }
                        case 5 -> {
                            System.out.println("Vous avez choisi l'option 5");
                            // Insérer ici le code pour l'option 5
                            pressEnterToContinue(scanner);
                        }
                        case 6 -> {
                            System.out.println("Vous avez choisi l'option 6");
                            // Insérer ici le code pour l'option 6
                            pressEnterToContinue(scanner);
                        }
                        case 7 -> {
                            System.out.println("Merci d'avoir utilisé notre programme !");
                            sortie = true;
                        }
                        default -> {
                            System.out.println(ConsoleColors.RED_BOLD + "Erreur : choix invalide" + ConsoleColors.RESET);
                            pressEnterToContinue(scanner);
                        }
                    }
                } else {
                    scanner.nextLine();
                    System.out.println(ConsoleColors.RED_BOLD + "Erreur : entrée invalide" + ConsoleColors.RESET);
                    pressEnterToContinue(scanner);
                }
        } while (!sortie);
    }
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {

        }
    }

    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print("Appuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

public static class ConsoleColors {
    // Définir les codes de couleurs pour les messages de la console
    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";
    public static final String BLACK_UNDERLINED = "\033[4;30m";
    public static final String RED_UNDERLINED = "\033[4;31m";
    public static final String GREEN_UNDERLINED = "\033[4;32m";
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    public static final String BLUE_UNDERLINED = "\033[4;34m";
    public static final String PURPLE_UNDERLINED = "\033[4;35m";
    public static final String CYAN_UNDERLINED = "\033[4;36m";
    public static final String WHITE_UNDERLINED = "\033[4;37m";
    public static final String BLACK_BACKGROUND = "\033[40m";
    public static final String RED_BACKGROUND = "\033[41m";
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND = "\033[46m";
    public static final String WHITE_BACKGROUND = "\033[47m";
    public static final String GRAY_BOLD = "\033[1;90m";
}
}


