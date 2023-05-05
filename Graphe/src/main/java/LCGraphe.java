import java.util.ArrayList;
import  java.util.HashMap;
import java.util.LinkedList;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.lang.String;

class LCGraphe {


    public class MaillonGrapheSec {
        private double fiab;
        private double dist;
        private double dur;
        private String dest;
        private MaillonGrapheSec suiv;

        private MaillonGrapheSec(double fiabilite, double distance , double duree, String d) {
            fiab = fiabilite;
            dist = distance;
            dur = duree;
            dest = d;
            suiv = null;
        }

        public String getDestination() {
            return dest;
        }

        public double getDistance() {
            return dist;
        }

        public double getDuree() {
            return dur;
        }

        public double getFiabilite() {
            return fiab;
        }


    }

    class MaillonGraphe {
        private String nom;
        private String type;
        private MaillonGrapheSec lVois;
        private MaillonGraphe suiv;

        private boolean listed;

        MaillonGraphe(String n, String t) {
            nom = n;
            type = t;
            lVois = null;
            suiv = null;
            listed = false;
        }

        public String getNom() {
            return nom;
        }
        public String getType() {
            return type;
        }
        public boolean estVide(){
            return !listed;
        }

        public MaillonGrapheSec getVoisins(){
            return this.lVois;
        }

        public ArrayList<MaillonGrapheSec> getArrayVoisins(){
            ArrayList<MaillonGrapheSec> res = new ArrayList<MaillonGrapheSec>();
            MaillonGrapheSec tmp = this.lVois;
            while(tmp!=null){
                res.add(tmp);
                tmp = tmp.suiv;
            }
            return res;
        }

        public String afficherVoisins() {
            MaillonGrapheSec tmp = this.lVois;
            String s = "";
            while(tmp!=null){
                s =s + tmp.dest + " [fiabilite=" + tmp.fiab + ", distance="+tmp.dist + ", durée=" + tmp.dur+"]\n";
                tmp = tmp.suiv;
            }
            return s;
        }

    }

    private MaillonGraphe premier;

    public LCGraphe(){
        premier = null;
    }

    public void ajoutSommet(String ori, String t){
        MaillonGraphe nouv = new MaillonGraphe(ori, t);
        nouv.suiv = this.premier;
        this.premier = nouv;
    }

    public String afficherTousLesCentres(){
        String res  = "";
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            res += tmp.nom +" ["+tmp.type+"]\n";
            tmp = tmp.suiv;
        }
        return res;
    }

    public ArrayList<MaillonGraphe> getCentres(){
        ArrayList<MaillonGraphe> res = new ArrayList<MaillonGraphe>();
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            res.add(tmp);
            tmp = tmp.suiv;
        }
        return res;
    }

    public MaillonGraphe getCentre(String o){
        MaillonGraphe tmp = this.premier;
        MaillonGraphe res = null;
        while(res == null && tmp!=null){
            if(tmp.nom.equals(o)){
                res = tmp;
            }
            tmp = tmp.suiv;
        }
        return res;
    }

    public boolean existeVoisin(String o, String d){
        boolean res = false;
        MaillonGraphe tmp = this.premier;
        MaillonGrapheSec tmp2 = null;
        while(!tmp.nom.equals(o)){
            tmp = tmp.suiv;
        }
        if(tmp != null){
            tmp2 = tmp.lVois;
            while(!res && tmp2!=null){
                if(tmp2.dest.equals(d)){
                    res = true;
                }
                tmp2 = tmp2.suiv;
            }
        }
        return res;
    }

    public void addEdge(String o, String d, double fiab, double dist, double dur){
        MaillonGrapheSec nouv = new MaillonGrapheSec(fiab, dist, dur, d);
        MaillonGraphe tmp = this.premier;
        while (!tmp.nom.equals(o)){
            tmp = tmp.suiv;
        }
        nouv.suiv = tmp.lVois;
        tmp.lVois = nouv;

        MaillonGrapheSec nouv2 = new MaillonGrapheSec(fiab, dist, dur, o);
        tmp = this.premier;
        while (!tmp.nom.equals(d)){
            tmp = tmp.suiv;
        }
        nouv2.suiv = tmp.lVois;
        tmp.lVois = nouv2;
    }

    public void modifVoisin(String o, String d, double fiab, double dist, double dur) throws ExistEdgeException, NotExistMainException {
        MaillonGrapheSec tmp2 = null;
        MaillonGraphe tmp = this.premier;
        MaillonGraphe tmp3 = this.premier;
        boolean check = false;
        while (!tmp.nom.equals(o)){
            tmp = tmp.suiv;
            if(tmp == null){
                throw new NotExistMainException("Le sommet "+o+" n'existe pas!");
            }
        }
        tmp2 = tmp.lVois;
        while(!check && tmp2!=null){
            if(tmp2.dest.equals(d)){
                tmp2.dist = dist;
                tmp2.dur = dur;
                tmp2.fiab = fiab;
                check = true;
            } else {
                tmp2 = tmp2.suiv;
            }
        }

        if(tmp2 == null){
            throw new ExistEdgeException("L'arete n'existe pas !");
        } // regarde si l'arete existe

        while(!tmp3.nom.equals(d)){
            tmp3 = tmp3.suiv;
            if(tmp3 == null){
                throw new NotExistMainException("Le sommet "+d+" n'existe pas!");
            }
        } // Get le sommet dest
        check = false;
        tmp2 = tmp3.lVois;
        while(!check && tmp2!=null){
            if(tmp2.dest.equals(o)){
                tmp2.dist = dist;
                tmp2.dur = dur;
                tmp2.fiab = fiab;
                check = true;
            }
            tmp2 = tmp2.suiv;
        }
    }

    public ArrayList<MaillonGrapheSec> getVoisins(String o) throws NotExistMainException{
        MaillonGraphe tmp = this.premier;
        ArrayList<MaillonGrapheSec> res = new ArrayList<MaillonGrapheSec>();
        while (!tmp.nom.equals(o)){
            tmp = tmp.suiv;
            if(tmp == null){
                throw new NotExistMainException("Le sommet "+o+" n'existe pas!");
            }
        }
        MaillonGrapheSec tmp2 = tmp.lVois;
        while(tmp2!=null){
            res.add(tmp2);
            tmp2 = tmp2.suiv;
        }
        return res;
    }

    public String toString(){
        String s = "";
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            MaillonGrapheSec  tmp2 = tmp.lVois;
            while(tmp2!=null){
                s += tmp.nom+"-"+tmp2.dest + " [fiabilite=" + tmp2.fiab + ", distance="+tmp2.dist + ", durée=" + tmp2.dur+"]\n";
                tmp2 = tmp2.suiv;
            }
            tmp = tmp.suiv;
        }
        return s;

    }

    public String afficherTousLesBlocs(){
        String res  = "";
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            if(tmp.type.equals("Bloc")){
                res = res + tmp.nom + " :";
            }
            tmp = tmp.suiv;
        }
        return res;
    }

    public String tousLesTrajets(){
        String s = "";
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            MaillonGrapheSec  tmp2 = tmp.lVois;
            while(tmp2!=null){
                s += tmp.nom+"-"+tmp2.dest + " [fiabilite=" + tmp2.fiab + ", distance="+tmp2.dist + ", durée=" + tmp2.dur+"]\n";
                tmp2 = tmp2.suiv;
            }
            tmp = tmp.suiv;
        }
        s = s + "\n";
        return s;
    }

    public void LectureFichier(){
        try (Scanner scanner = new Scanner(new File("src/test.txt"))) {
            // Lire la première ligne contenant l'en-tête
            String header = scanner.nextLine();

            chargementGraphe(header);
            // Créer un nouveau graphe


            // Parcourir les lignes du fichier
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                chargementGraphe(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargementGraphe(String ligne) {
        //decompose la chaine en sous chaines -> bloc
        String[] sousChaines = ligne.split(";");

        // je prends le nom et le type de la première sous-chaîne -> premier bloc
        String nom = sousChaines[0].split(",")[0];
        String type = sousChaines[0].split(",")[1];

        // Créer un nouvel objet MaillonGraphe en utilisant le nom et le type extraits
        MaillonGraphe mg = new MaillonGraphe(nom, type);

        // Ajouter le premier sommet au graphe
        ajoutSommet(nom, type);

        // Parcourir toutes les sous-chaînes à partir de la deuxième
        for (int i = 1; i < sousChaines.length; i++) {
            // Extraire les éléments de la sous-chaîne courante
            String[] elements = sousChaines[i].split(",");

            // Ajouter les sommets
            ajoutSommet(elements[3], type);
            ajoutSommet(elements[4], type);

            // Ajouter l'arête au graphe
            addEdge(elements[3], elements[4], Double.parseDouble(elements[0]), Double.parseDouble(elements[1]), Double.parseDouble(elements[2]));
        }
    }



    public void affiche(){
        MaillonGraphe tmp = this.premier;
        while (tmp != null) {
            System.out.println("Nom: " + tmp.nom + " | Type: " + tmp.type);
            MaillonGrapheSec tmp2 = tmp.lVois;
            while (tmp2 != null) {
                System.out.println("Fiabilite: " + tmp2.fiab + " | Distance: " + tmp2.dist + " | Duree: " + tmp2.dur + " | Destination: " + tmp2.dest);
                tmp2 = tmp2.suiv;
            }
            tmp = tmp.suiv;
        }

    }
}