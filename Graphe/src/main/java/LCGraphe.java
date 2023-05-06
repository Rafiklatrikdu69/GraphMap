
import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;
import java.lang.String;
/**
 * 
 * @author Rafik Bouchenna et Emmanuel Ardoin
 */
class LCGraphe {

    public class MaillonGrapheSec {

        private double fiab;
        private double dist;
        private double dur;
        private String dest;
        private MaillonGrapheSec suiv;

        private MaillonGrapheSec(double fiabilite, double distance, double duree, String d) {
            fiab = fiabilite;
            dist = distance;
            dur = duree;
            dest = d;
            suiv = null;
        }
/**
 * 
 * @return String: destination
 */
        private String getDestination() {
            return dest;
        }
/**
 * 
 * @return double : distance
 */
        private double getDistance() {
            return dist;
        }
/**
 * 
 * @return double : duree
 */
        private double getDuree() {
            return dur;
        }
/**
 * 
 * @return double : fiabilite
 */
        private double getFiabilite() {
            return fiab;
        }
        /**
         *
         * @param dest
         */
        public void setDestination(String dest) {
            this.dest = dest;
        }

        /**
         *
         * @param dist
         */
        public void setDistance(double dist) {
            this.dist = dist;
        }

        /**
         *
         * @param duree
         */
        public void setDuree(double duree) {
            this.dur = duree;
        }

        /**
         *
         * @param fiab
         */
        public void setFiabilite(double fiab) {
            this.fiab = fiab;
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
/**
 * 
 * @return String :  nom du sommet
 */
        private String getNom() {
            return nom;
        }
/**
 * 
 * @return String : type de sommet
 */
        private String getType() {
            return type;
        }
/**
 * 
 * @return boolean : listed
 */
        private boolean estVide() {
            return !listed;
        }
/**
 * Cette methode renvoie une liste de voisin 
 * 
 * @return List<MaillonGrapheSec>
 * 
 */
        private List<MaillonGrapheSec> getArrayVoisins() {
            List<MaillonGrapheSec> res = new LinkedList<>();
            MaillonGrapheSec tmp = this.lVois;
            while (tmp != null) {
                res.add(tmp);
                tmp = tmp.suiv;
            }
            return res;
        }
/**
 * Renvoie une chaine contenant les voisins
 *  
 * @return  String : res 
 */
        public String voisinsToString() {
            MaillonGrapheSec tmp = this.lVois;
            StringBuilder s = new StringBuilder();
            while (tmp != null) {
                s.append("Destination : ").append(tmp.dest).append(" [fiabilite=").append(tmp.fiab).append(", distance=").append(tmp.dist).append(", durée=").append(tmp.dur).append("]\n");
                tmp = tmp.suiv;
            }
            return s.toString();
        }
    }
    private MaillonGraphe premier;

    public LCGraphe() {
        premier = null;
    }
/**
 * Cette methode ajoute au debut de la liste le sommet avec les parametres
 * 
 * @param nomCentre
 * @param typeCentre 
 */
    public void ajoutCentre(String nomCentre, String typeCentre) {
        MaillonGraphe nouv = new MaillonGraphe(nomCentre, typeCentre);
        nouv.suiv = this.premier;
        this.premier = nouv;
    }
/**
 * Cette methode ajoute les Voisins(Arretes) avec les parametres 
 * 
 * @param nomCentre
 * @param nomDestinataire
 * @param fiab
 * @param dist
 * @param dur 
 */
    public void ajoutVoisin(String nomCentre, String nomDestinataire, Double fiab, Double dist, Double dur) {
        MaillonGrapheSec nouv = new MaillonGrapheSec(fiab, dist, dur, nomDestinataire);
        MaillonGraphe tmp = this.premier;
        while (!tmp.nom.equals(nomCentre)) {
            tmp = tmp.suiv;
        }
        nouv.suiv = tmp.lVois;
        tmp.lVois = nouv;

        MaillonGrapheSec nouv2 = new MaillonGrapheSec(fiab, dist, dur, nomCentre);
        tmp = this.premier;
        while (!tmp.nom.equals(nomDestinataire)) {
            tmp = tmp.suiv;
        }
        nouv2.suiv = tmp.lVois;
        tmp.lVois = nouv2;
    }
/**
 * Cette methode permet de modifier les donnee d'un voisin(Arrete)
 * 
 * @param nomCentre
 * @param nomDestinataire
 * @param fiab
 * @param dist
 * @param dur
 * @throws ExistEdgeException
 * @throws NotExistMainException 
 */
    public void modifVoisin(String nomCentre, String nomDestinataire, double fiab, double dist, double dur) throws ExistEdgeException, NotExistMainException {
        MaillonGrapheSec tmp2 = null;
        MaillonGraphe tmp = this.premier;
        MaillonGraphe tmp3 = this.premier;
        boolean check = false;
        while (!tmp.nom.equals(nomCentre)) {
            tmp = tmp.suiv;
            if (tmp == null) {
                throw new NotExistMainException("Le centre " + nomCentre + " n'existe pas!");
            }
        }
        tmp2 = tmp.lVois;
        while (!check && tmp2 != null) {
            if (tmp2.dest.equals(nomDestinataire)) {
              tmp2.setDistance(dist);
                tmp2.setDuree(dur);
                tmp2.setFiabilite(fiab);
                check = true;
            } else {
                tmp2 = tmp2.suiv;
            }
        }

        if (tmp2 == null) {
            throw new ExistEdgeException("L'arete n'existe pas !");
        } // regarde si l'arete existe

        while (!tmp3.nom.equals(nomDestinataire)) {
            tmp3 = tmp3.suiv;
            if (tmp3 == null) {
                throw new NotExistMainException("Le sommet " + nomDestinataire + " n'existe pas!");
            }
        } 
        check = false;
        tmp2 = tmp3.lVois;
        while (!check && tmp2 != null) {
            if (tmp2.dest.equals(nomCentre)) {
                tmp2.setDistance(dist);
                tmp2.setDuree(dur);
                tmp2.setFiabilite(fiab);
                check = true;
            }
            tmp2 = tmp2.suiv;
        }
    }
/**
 * Cette methode renvoie true si il existe une arrete sinon elle renvoie false
 * 
 * @param nomCentre
 * @param nomDestinataire
 * @return boolean:  res
 */
    public boolean existeVoisin(String nomCentre, String nomDestinataire) {
        boolean res = false;
        MaillonGraphe tmp = this.premier;
        MaillonGrapheSec tmp2 = null;
        while (!tmp.nom.equals(nomCentre)) {
            tmp = tmp.suiv;
        }
        tmp2 = tmp.lVois;
        while (!res && tmp2 != null) {
            if (tmp2.dest.equals(nomDestinataire)) {
                res = true;
            }
            tmp2 = tmp2.suiv;
        }
        return res;
    }
/**
 * Cette methode renvoie true si le centre(sommet) existe sinon elle renvoie false 
 * 
 * @param nomCentre
 * @return  boolean : res 
 */
   public boolean existeCentre(String nomCentre) {
    MaillonGraphe tmp = this.premier;
    while (tmp != null && !tmp.getNom().equals(nomCentre)) {
        tmp = tmp.suiv;
    }
    return (tmp != null);
}

/**
 * Cette methode renvoie tout les sommet en les affichant 
 * 
 * @return String : res
 */
    public String tousLesCentresToString() {
        StringBuilder res = new StringBuilder();
        MaillonGraphe tmp = this.premier;
        while (tmp != null) {
            res.append(tmp.nom).append(" [").append(tmp.type).append("]\n");
            tmp = tmp.suiv;
        }
        return res.toString();
    }

    public List<MaillonGraphe> tousLesCentresToList() {
        LinkedList<MaillonGraphe> res = new LinkedList<>();
        MaillonGraphe tmp = this.premier;
        while (tmp != null) {
            res.add(tmp);
            tmp = tmp.suiv;
        }
        return res;
    }

    public String tousLesBlocsToString() {
        StringBuilder res = new StringBuilder();
        MaillonGraphe tmp = this.premier;
        while (tmp != null) {
            if (tmp.type.equals("O")) {
                res.append(tmp.nom).append("\n");
            }
            tmp = tmp.suiv;
        }
        return res.toString();
    }
/**
 * Cette methode recupere le centre avec son nom en parametre
 * 
 * @param nomCentre
 * @return MaillonGraphe : res
 */
    public MaillonGraphe getCentre(String nomCentre) {
        MaillonGraphe tmp = this.premier;
        MaillonGraphe res = null;
        while (res == null && tmp != null) {
            if (tmp.nom.equals(nomCentre)) {
                res = tmp;
            }
            tmp = tmp.suiv;
        }
        return res;
    }

    public void chargementFichier() {
        try {
            File file = new File("C:/Users/Rafik/Documents/SAE/Graphe/src/fichiersGraphe/liste-adjacence-jeuEssai.csv");
            //File file = new File("src/fichiersGraphe/jeuEssaietest.csv");
            Scanner scanner = new Scanner(file);
            int lineCounter = 0;
            HashMap<Integer, List> hashMapArreteTraite = new HashMap<>();
            List<String> listArreteDonnee;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (lineCounter >= 5) { // permet de pas prendre les premiere lignes d'explications
                    String[] parts = line.split(";");

                    String nom = parts[0]; // prends le nom du sommet
                    String type = parts[1];// prends le type du sommet
                    ajoutCentre(nom, type); // et on ajoute le sommet au Graphe

                    for (int i = 2; i < parts.length; i++) { // parcours toutes les arretes du sommet actuel
                        if (!parts[i].equals("0")) {
                            listArreteDonnee = new LinkedList<>();
                            if (hashMapArreteTraite.containsKey(i)) { // cette ligne permet de voir si ya une arrete complete a été détecté
                                ajoutVoisin(hashMapArreteTraite.get(i).get(0).toString(), "S" + (lineCounter - 4), Double.parseDouble((String) hashMapArreteTraite.get(i).get(1)), Double.parseDouble((String) hashMapArreteTraite.get(i).get(2)), Double.parseDouble((String) hashMapArreteTraite.get(i).get(3)));
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

    public String toString() {
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

    private List<MaillonGraphe> getCentres() {
        List<MaillonGraphe> res = new ArrayList<>();
        MaillonGraphe tmp = this.premier;
        while (tmp != null) {
            res.add(tmp);
            tmp = tmp.suiv;
        }
        return res;
    }

    public List<String> plusCourtCheminDijkstra(String centre1, String centre2) {
        Map<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>(); // res sera le chemin entre centre1 et centre2 (null si ya pas de chemin)
        Map<String, Boolean> marquage = new HashMap<String, Boolean>(); // permet de marquer les centres (Sommets traité en Graphe)
        res.put(centre1, new ArrayList<>());
        res.get(centre1).add(centre1);
        this.getCentres().forEach(Centre -> {
            marquage.put(Centre.getNom(), false);//marque tout les sommet en les mettant a false 
        });
        marquage.put(centre1, true); // Je marque le premier Centre (le premier sommet en Graphe)
        FileFIFO<String> newFile = new FileFIFO<>(); // Permet de rex§lacher les successeurs
        newFile.enfiler(centre1); // Ajoute le premier centre a traité
        while (!newFile.estVide()) {
            String centre = newFile.defiler();
            List<MaillonGrapheSec> succSom = this.getCentre(centre).getArrayVoisins(); // On récupère tous les voisins du sommet en cours de traitement
            for (MaillonGrapheSec maillonGrapheSec : succSom) { // On parcours tous les voisins
                String nomSucc = maillonGrapheSec.getDestination();
                if (!marquage.get(nomSucc)) { // Regarde si le voisin n'est pas marqué
                    res.put(nomSucc, new ArrayList<String>(res.get(centre))); // créer une nouvelle liste pour le sommet suivant et le met dans la hashmap
                    res.get(nomSucc).add(nomSucc);
                    marquage.put(nomSucc, true); // Marque le voisin (Comme sommet traité)
                    newFile.enfiler(nomSucc); // Ajoute le voisin
                }
            }
        }

        if (!(res.containsKey(centre2))) {
            return null;
        }
        List<String> chemin = res.get(centre2);

        double distanceTotale = 0; // Initialisation de la distance totale
        for (int i = 0; i < chemin.size() - 1; i++) {
            String centreActuel = chemin.get(i);//recupere le centre a l'indice i 
            String centreSuivant = chemin.get(i + 1);//recupere le centre suivant  l'indice i +1
            MaillonGraphe centre = this.getCentre(centreActuel);
            for (MaillonGrapheSec voisin : centre.getArrayVoisins()) {
                if (voisin.getDestination().equals(centreSuivant)) {
                    distanceTotale += voisin.getDistance(); // Ajout de la distance entre chaque centre et son prédécesseur pour calculer la distance totale
                    break;
                }
            }
        }
        System.out.println("Distance totale : " + distanceTotale); // Affichage de la distance totale
        return chemin;

    }

    /**
     *
     * @param centre1
     * @param centre2
     * @return
     */
    public LinkedHashMap<String, Double> plusCourtCheminDijkstraFiabilite(String centre1, String centre2) {
        Map<String, LinkedHashMap<String, Double>> res = new HashMap<>();//Hash map qui permet de stocker les chemins les plus courts
        Map<String, Boolean> sommetsTraites = new HashMap<>();//Hash map permettant de marquer les sommet traiter 
        res.put(centre1, new LinkedHashMap<>());
        res.get(centre1).put(centre1, 1.0);//le premier centre(sommet) a une fiabilite de 1 
        //on itere sur tout les centres afin de les mettre a false -> les sommets ne sont pas encore marquer
        this.getCentres().forEach(Centre -> {
            sommetsTraites.put(Centre.getNom(), false);
        });
        sommetsTraites.put(centre1, true);//le premier centre est le debut donc il traiter -> true
        List<String[]> fileAttente = new ArrayList<>();//creation d'une liste permettant d'ajouter les sommet traiter
        fileAttente.add(new String[]{centre1, "1"});//ajoute le premier sommet avec comme fiabilite 1 
        String[] donnee;
        while (!(fileAttente.isEmpty())) {//tant que cette liste n'est pas vide 
            donnee = fileAttente.get(fileAttente.size() - 1);//sauvegarde la fiabilite et le centre de la fin de la file dans la variable donnee
            fileAttente.remove(fileAttente.size() - 1);//supprime ces cet element 
            String centre = donnee[0];//donne[0] vaut le nom du centre
            double fiab = Double.parseDouble(donnee[1]);//donne[1] vaut la fiabilite de type String convertie en double

            MaillonGrapheSec voisin = this.getCentre(centre).lVois;
            while (voisin != null) {
                String nomVoisin = voisin.getDestination();
                if (!sommetsTraites.get(nomVoisin)) {
                    if (!res.containsKey(nomVoisin)) {
                        // On arrive à cette endroit si nomVoisin n'a pas encore de chemin
                        res.put(nomVoisin, new LinkedHashMap<>(res.get(centre)));
                        res.get(nomVoisin).put(nomVoisin, (voisin.getFiabilite() / 10) * fiab);
                        fileAttente.add(new String[]{nomVoisin, String.valueOf((voisin.getFiabilite() / 10) * fiab)});

                    } else {
                        // si nomVoisin a déjà un chemin
                        LinkedHashMap<String, Double> chemin = res.get(nomVoisin);
                        Double lastFiabCentreDansChemin = null;
                        String lastNomCentreDansChemin = null;
                        for (Map.Entry<String, Double> centreChemin : chemin.entrySet()) {
                            // Dans cette boucle on récupère l'extrémité du chemin <centre1, nomVoisin>
                            lastNomCentreDansChemin = centreChemin.getKey(); // le nom de l'extrémité
                            lastFiabCentreDansChemin = centreChemin.getValue(); // la fiabilité de l'extrémité
                        }
                        if (lastFiabCentreDansChemin < (voisin.getFiabilite() / 10) * fiab) {
                            // Si on arrive ici, ca veut dire que le chemin initial entre le centre1 et nomVoisin n'est pas le plus fiable a ce moment la
                            res.put(nomVoisin, new LinkedHashMap<>(res.get(donnee[0])));
                            res.get(nomVoisin).put(lastNomCentreDansChemin, (voisin.getFiabilite() / 10) * fiab);
                            // Donc on créer le nouveau chemin le plus fiable entre centre1 et nomVoisin

                            // Comme il y a un nouveau chemin, ca veut dire que la fileAttente est obsolete car elle n'est pas à jour
                            // Grace a la boucle ci dessous, on retire l'ancien nomVoisin puis on le remplace par le nouveau avec sa nouvelle fiabilité
                            int i = 0;
                            boolean check = false;
                            while (!check && i < fileAttente.size()) {
                                if (fileAttente.get(i)[0].equals(nomVoisin)) {
                                    check = true;
                                    fileAttente.remove(i);
                                    fileAttente.add(new String[]{nomVoisin, String.valueOf((voisin.getFiabilite() / 10) * fiab)});
                                }
                                i++;
                            }
                        }

                    }
                }
                voisin = voisin.suiv;
            }
            if (fileAttente.size() >= 2) {
                // si on arrive ca veut dire qu'il faut mettre le sommetTraite le plus fiable tout en haut de la liste pour la traité en premier
                int maxiFiabIndice = 0;
                for (int i = 1; i < fileAttente.size(); i++) {
                    if (Double.parseDouble(fileAttente.get(i)[1]) > Double.parseDouble(fileAttente.get(maxiFiabIndice)[1])) {
                        // si on arrive ici, ca veut dire qu'on a trouvé le plus fiable au ieme moment
                        maxiFiabIndice = i;
                    } else if (Double.parseDouble(fileAttente.get(i)[1]) == Double.parseDouble(fileAttente.get(maxiFiabIndice)[1])) {
                        // si on arrive ici, ca veut dire que deux sommets ont la même fiabilité donc je met en priorité le plus petit sommet (ex: D'abord S9 puis S15, et pas S15 puis S9)
                        String[] listNomCentre = fileAttente.get(i)[0].split("");
                        String[] listMaxiNomCentre = fileAttente.get(maxiFiabIndice)[0].split("");
                        int nombre;
                        int maxiNombre;
                        if (listNomCentre.length == 2) {
                            nombre = Integer.parseInt(listNomCentre[1]);
                        } else {
                            nombre = Integer.parseInt(listNomCentre[1] + listNomCentre[2]);
                        }
                        if (listMaxiNomCentre.length == 2) {
                            maxiNombre = Integer.parseInt(listMaxiNomCentre[1]);
                        } else {
                            maxiNombre = Integer.parseInt(listMaxiNomCentre[1] + listMaxiNomCentre[2]);
                        }
                        if (maxiNombre > nombre) {
                            maxiFiabIndice = i;
                        }
                    }
                }
                sommetsTraites.put(fileAttente.get(maxiFiabIndice)[0], true); // je marque le sommet qui va etre traité
                fileAttente.add(new String[]{fileAttente.get(maxiFiabIndice)[0], String.valueOf(fileAttente.get(maxiFiabIndice)[1])});
                fileAttente.remove(maxiFiabIndice);
            }
        }
        if (!(res.containsKey(centre2))) {
            return null;
        }
        return res.get(centre2);
    }
}
