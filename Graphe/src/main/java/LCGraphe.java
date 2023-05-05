import java.io.FileNotFoundException;
import java.util.*;
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
        public ArrayList<MaillonGrapheSec> getArrayVoisins(){
            ArrayList<MaillonGrapheSec> res = new ArrayList<>();
            MaillonGrapheSec tmp = this.lVois;
            while(tmp!=null){
                res.add(tmp);
                tmp = tmp.suiv;
            }
            return res;
        }
        public String voisinsToString() {
            MaillonGrapheSec tmp = this.lVois;
            StringBuilder s = new StringBuilder();
            while(tmp!=null){
                s.append("Destination : ").append(tmp.dest).append(" [fiabilite=").append(tmp.fiab).append(", distance=").append(tmp.dist).append(", durée=").append(tmp.dur).append("]\n");
                tmp = tmp.suiv;
            }
            return s.toString();
        }
    }
    private MaillonGraphe premier;

    public LCGraphe(){
        premier = null;
    }

    public void ajoutCentre(String nomCentre, String typeCentre){
        MaillonGraphe nouv = new MaillonGraphe(nomCentre, typeCentre);
        nouv.suiv = this.premier;
        this.premier = nouv;
    }
    public void ajoutVoisin(String nomCentre, String nomDestinataire, Double fiab, Double dist, Double dur){
        MaillonGrapheSec nouv = new MaillonGrapheSec(fiab, dist, dur, nomDestinataire);
        MaillonGraphe tmp = this.premier;
        while (!tmp.nom.equals(nomCentre)){
            tmp = tmp.suiv;
        }
        nouv.suiv = tmp.lVois;
        tmp.lVois = nouv;

        MaillonGrapheSec nouv2 = new MaillonGrapheSec(fiab, dist, dur, nomCentre);
        tmp = this.premier;
        while (!tmp.nom.equals(nomDestinataire)){
            tmp = tmp.suiv;
        }
        nouv2.suiv = tmp.lVois;
        tmp.lVois = nouv2;
    }
    public void modifVoisin(String nomCentre, String nomDestinataire, double fiab, double dist, double dur) throws ExistEdgeException, NotExistMainException {
        MaillonGrapheSec tmp2 = null;
        MaillonGraphe tmp = this.premier;
        MaillonGraphe tmp3 = this.premier;
        boolean check = false;
        while (!tmp.nom.equals(nomCentre)){
            tmp = tmp.suiv;
            if(tmp == null){
                throw new NotExistMainException("Le centre "+nomCentre+" n'existe pas!");
            }
        }
        tmp2 = tmp.lVois;
        while(!check && tmp2!=null){
            if(tmp2.dest.equals(nomDestinataire)){
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

        while(!tmp3.nom.equals(nomDestinataire)){
            tmp3 = tmp3.suiv;
            if(tmp3 == null){
                throw new NotExistMainException("Le sommet "+nomDestinataire+" n'existe pas!");
            }
        } // Get le sommet dest
        check = false;
        tmp2 = tmp3.lVois;
        while(!check && tmp2!=null){
            if(tmp2.dest.equals(nomCentre)){
                tmp2.dist = dist;
                tmp2.dur = dur;
                tmp2.fiab = fiab;
                check = true;
            }
            tmp2 = tmp2.suiv;
        }
    }
    public boolean existeVoisin(String nomCentre, String nomDestinataire){
        boolean res = false;
        MaillonGraphe tmp = this.premier;
        MaillonGrapheSec tmp2 = null;
        while(!tmp.nom.equals(nomCentre)){
            tmp = tmp.suiv;
        }
        tmp2 = tmp.lVois;
        while(!res && tmp2!=null){
            if(tmp2.dest.equals(nomDestinataire)){
                res = true;
            }
            tmp2 = tmp2.suiv;
        }
        return res;
    }
    public boolean existeCentre(String nomCentre){
        boolean res = false;

        return res;
    }
    public String tousLesCentresToString(){
        String res  = "";
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            res += tmp.nom +" ["+tmp.type+"]\n";
            tmp = tmp.suiv;
        }
        return res;
    }
    public String tousLesBlocsToString(){
        StringBuilder res  = new StringBuilder();
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            if(tmp.type.equals("O")){
                res.append(tmp.nom).append("\n");
            }
            tmp = tmp.suiv;
        }
        return res.toString();
    }
    public MaillonGraphe getCentre(String nomCentre){
        MaillonGraphe tmp = this.premier;
        MaillonGraphe res = null;
        while(res == null && tmp!=null){
            if(tmp.nom.equals(nomCentre)){
                res = tmp;
            }
            tmp = tmp.suiv;
        }
        return res;
    }
    public void chargementFichier(){
        try {
            File file = new File("src/fichiersGraphe/liste-adjacence-jeuEssai.csv");
            Scanner scanner = new Scanner(file);
            int lineCounter = 0;
            HashMap<Integer, List> hashMapArreteTraite = new HashMap<>();
            List<String> listArreteDonnee;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(lineCounter >= 5){ // permet de pas prendre les premiere lignes d'explications
                    String[] parts = line.split(";");

                    String nom = parts[0]; // prends le nom du sommet
                    String type = parts[1];// prends le type du sommet
                    ajoutCentre(nom, type); // et on ajoute le sommet au Graphe

                    for (int i = 2; i < parts.length; i++) { // parcours toutes les arretes du sommet actuel
                        if(!parts[i].equals("0")){
                            listArreteDonnee = new LinkedList<>();
                            if(hashMapArreteTraite.containsKey(i)){ // cette ligne permet de voir si ya une arrete complete a été détecté
                                ajoutVoisin(hashMapArreteTraite.get(i).get(0).toString(), "S"+(lineCounter - 4), Double.parseDouble((String) hashMapArreteTraite.get(i).get(1)) ,Double.parseDouble((String) hashMapArreteTraite.get(i).get(2)),Double.parseDouble((String) hashMapArreteTraite.get(i).get(3)));
                                hashMapArreteTraite.remove(i);
                            } else {
                                String[] edgeValues = parts[i].split(",");
                                listArreteDonnee.add(nom);
                                listArreteDonnee.addAll(Arrays.asList(edgeValues));
                                hashMapArreteTraite.put(i, listArreteDonnee);
                            }

                        }
                    }
                }
                lineCounter++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String toString(){
        StringBuilder res = new StringBuilder();
        MaillonGraphe tmp = this.premier;
        while (tmp != null) {
            res.append("Nom: ").append(tmp.nom).append(" | Type: ").append(tmp.type).append("\n");
            MaillonGrapheSec tmp2 = tmp.lVois;
            while (tmp2 != null) {
                res.append("Fiabilité: ").append(tmp2.fiab).append(" | Distance: ").append(tmp2.dist).append(" | Durée: ").append(tmp2.dur).append(" | Destination: ").append(tmp2.dest).append("\n");
                tmp2 = tmp2.suiv;
            }
            tmp = tmp.suiv;
        }
    return res.toString();
    }

    private ArrayList<MaillonGraphe> getCentres(){
        ArrayList<MaillonGraphe> res = new ArrayList<>();
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            res.add(tmp);
            tmp = tmp.suiv;
        }
        return res;
    }










    public ArrayList<String> plusCourtCheminDijkstra(String centre1, String centre2){
        HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
        HashMap<String, Boolean> marquage = new HashMap<String, Boolean>();
        List<MaillonGraphe> listCentres = this.getCentres();
        res.put(centre1, new ArrayList<>());
        res.get(centre1).add(centre1);
        for (MaillonGraphe listCentre : listCentres) {
            marquage.put(listCentre.getNom(), false);
        }
        marquage.put(centre1, true);
        FileFIFO<String> newFile = new FileFIFO<>();
        newFile.enfiler(centre1);
        while(!newFile.estVide()){
            String centre = newFile.defiler();
            ArrayList<MaillonGrapheSec> succSom = this.getCentre(centre).getArrayVoisins();
            for (MaillonGrapheSec maillonGrapheSec : succSom) {
                String nomSucc = maillonGrapheSec.getDestination();
                if (!marquage.get(nomSucc)) {
                    res.put(nomSucc, new ArrayList<String>(res.get(centre)));
                    res.get(nomSucc).add(nomSucc);
                    marquage.put(nomSucc, true);
                    newFile.enfiler(nomSucc);
                }
            }
        }
        if(!(res.containsKey(centre2))){
            return null;
        }
        return res.get(centre2);
    }
}