import Exception.CentreException;
import Exception.VoisinException;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//import org.Assert.assertEquals;

/**
 * @author Rafik Bouchenna et Emmanuel Ardoin
 */
class LCGraphe {

    public class MaillonGrapheSec {

        private double fiab;
        private double dist;
        private double dur;
        private String dest;
        public MaillonGrapheSec suiv;

        private MaillonGrapheSec(double fiabilite, double distance, double duree, String d) {
            fiab = fiabilite;
            dist = distance;
            dur = duree;
            dest = d;
            suiv = null;
        }

        /**
         * @return String: destination
         */
        public String getDestination() {
            return dest;
        }

        /**
         * @return double : distance
         */
        public double getDistance() {
            return dist;
        }

        /**
         * @return double : duree
         */
        public double getDuree() {
            return dur;
        }

        /**
         * @return double : fiabilite
         */
        public double getFiabilite() {
            return fiab;
        }

        /**
         * @param dest
         */
        public void setDestination(String dest) {
            this.dest = dest;
        }

        /**
         * @param dist
         */
        public void setDistance(double dist) {
            this.dist = dist;
        }

        /**
         * @param duree
         */
        public void setDuree(double duree) {
            this.dur = duree;
        }

        /**
         * @param fiab
         */
        public void setFiabilite(double fiab) {
            this.fiab = fiab;
        }

        public String toString() {
            StringBuilder res = new StringBuilder();
            res.append(this.dest).append(" [Fiabilité : ").append(this.fiab * 10).append("%, Distance : ").append(this.dist).append("Km, Durée : ").append(this.dur).append(" minutes]");
            return res.toString();
        }
    }

    class MaillonGraphe {

        private String nom;
        private String type;
        public MaillonGrapheSec lVois;
        public MaillonGraphe suiv;
        private boolean listed;
        private JLabel posLabel;

        MaillonGraphe(String n, String t) {
            nom = n;
            type = t;
            lVois = null;
            suiv = null;
            listed = false;

        }


        public MaillonGraphe getSuivant() {
            return this.suiv;
        }

        /**
         * @return String : nom du sommet
         */
        public String getNom() {
            return nom;
        }

        /**
         * @return String : type de sommet
         */
        public String getType() {
            return type;
        }

        /**
         * @return boolean : listed
         */
        private boolean estVide() {
            return !listed;
        }

        /**
         * Cette methode renvoie une liste de voisin
         *
         * @return LinkedList<MaillonGrapheSec>
         */
        public LinkedList<MaillonGrapheSec> voisinsToList() {
            LinkedList<MaillonGrapheSec> res = new LinkedList<>();
            MaillonGrapheSec tmp = this.lVois;
            while (tmp != null) {
                res.add(tmp);
                tmp = tmp.suiv;
            }
            assert res != null;
            return res;
        }

        /**
         * @param nomVoisin
         * @return
         */
        public MaillonGrapheSec getVoisin(String nomVoisin) {
            MaillonGrapheSec res = null;
            MaillonGrapheSec tmp = this.lVois;
            while (res == null && tmp != null) {
                if (tmp.getDestination().equals(nomVoisin)) {
                    res = tmp;
                }
                tmp = tmp.suiv;
            }
            return res;
        }

        /**
         * @param nomVoisin
         * @return
         */
        public boolean estVoisin(String nomVoisin) {
            boolean res = false;
            MaillonGrapheSec tmp = this.lVois;
            while (!res && tmp != null) {
                if (tmp.getDestination().equals(nomVoisin)) {
                    res = true;
                }
                tmp = tmp.suiv;
            }
            return res;
        }

        /**
         * Renvoie une chaine contenant les voisins
         *
         * @return String : res
         */
        public String voisinsToString() {
            MaillonGrapheSec tmp = this.lVois;
            StringBuilder s = new StringBuilder();
            while (tmp != null) {
                s.append("Destination : ").append(tmp.dest).append(" [Fiabilité : ").append(tmp.fiab * 10).append("%, Distance : ").append(tmp.dist).append("Km, Durée : ").append(tmp.dur).append(" minutes]\n");
                tmp = tmp.suiv;

            }
            return s.toString();
        }

    }

    public MaillonGraphe premier;

    public LCGraphe() {
        premier = null;
    }

    public MaillonGraphe getPremier() {
        return this.premier;
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
     * @param centre1
     * @param centre2
     */
    public String voisinsVoisinsToString(String centre1, String centre2) {
        StringBuilder res = new StringBuilder();
        List<String> voisins1 = new LinkedList<>();
        List<String> voisins2 = new LinkedList<>();
        MaillonGraphe maillonCentre1 = chercherMaillon(centre1);
        MaillonGraphe maillonCentre2 = chercherMaillon(centre2);

        MaillonGrapheSec tmp1 = maillonCentre1.lVois;
        while (tmp1 != null) {
            voisins1.add(tmp1.dest);
            tmp1 = tmp1.suiv;
        }
        MaillonGrapheSec tmp2 = maillonCentre2.lVois;
        while (tmp2 != null) {
            voisins2.add(tmp2.dest);
            tmp2 = tmp2.suiv;
        }
        List<String> voisinsCommuns = new ArrayList<>(voisins1);
        voisinsCommuns.retainAll(voisins2);//ne retiens que les elements communs au deux liste

        boolean distance2 = false;

        for (String voisin : voisinsCommuns) {
            MaillonGraphe maillonVoisin = chercherMaillon(voisin);
            MaillonGrapheSec tmp3 = maillonVoisin.lVois;
            while (tmp3 != null) {
                if (voisins1.contains(tmp3.dest) && voisins2.contains(tmp3.dest)) {
                    res.append("Les sommets " + centre1 + " et " + centre2 + " sont à une distance de 2.");
                    distance2 = true;
                    break;
                }
                tmp3 = tmp3.suiv;
            }
            if (distance2) {
                break;
            }
        }
        if (!distance2) {
            res.append("Les sommets " + centre1 + " et " + centre2 + " ne sont pas à une distance de 2.");
        }
        return res.toString();
    }

    /**
     * @param nomMaillon
     * @return
     */
    public MaillonGraphe chercherMaillon(String nomMaillon) {
        MaillonGraphe courant = this.premier;
        while (courant != null) {
            if (courant.nom.equals(nomMaillon)) {
                return courant;
            }
            courant = courant.suiv;
        }
        return null; // Le maillon n'a pas été trouvé dans la liste chaînée
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
     * @throws VoisinException
     * @throws CentreException
     */
    public void modifVoisin(String nomCentre, String nomDestinataire, double fiab, double dist, double dur) throws VoisinException, CentreException {
        MaillonGrapheSec tmp2 = null;
        MaillonGraphe tmp = this.premier;
        MaillonGraphe tmp3 = this.premier;
        boolean check = false;
        while (!tmp.nom.equals(nomCentre)) {
            tmp = tmp.suiv;
            if (tmp == null) {
                throw new CentreException("Le centre " + nomCentre + " n'existe pas!");
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
            throw new VoisinException("L'arete n'existe pas !");
        } // regarde si l'arete existe

        while (!tmp3.nom.equals(nomDestinataire)) {
            tmp3 = tmp3.suiv;
            if (tmp3 == null) {
                throw new CentreException("Le sommet " + nomDestinataire + " n'existe pas!");
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
     * Cette methode renvoie true si il existe une arrete sinon elle renvoie
     * false
     *
     * @param nomCentre
     * @param nomDestinataire
     * @return boolean: res
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
     * Cette methode renvoie true si le centre(sommet) existe sinon elle renvoie
     * <p>
     * false
     *
     * @param nomCentre
     * @return boolean : res
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

    /**
     * @return res : LinkedList<MaillonGraphe>
     */
    public LinkedList<MaillonGraphe> tousLesCentresToList() {
        LinkedList<MaillonGraphe> res = new LinkedList<>();
        MaillonGraphe tmp = this.premier;
        while (tmp != null) {
            res.add(tmp);
            tmp = tmp.suiv;
        }
        return res;
    }

    /**
     * @return
     */
    public List<MaillonGrapheSec> toutesLesAretesToList() {
        List<MaillonGrapheSec> res = new LinkedList<>();
        MaillonGraphe tmp = this.premier;
        MaillonGrapheSec tmp2 = null;
        while (tmp != null) {
            tmp2 = tmp.lVois;
            while (tmp2 != null) {
                if (res.contains(tmp2)) {
                    tmp2 = tmp2.suiv;
                }
            }
            tmp = tmp.suiv;
        }
        return res;
    }

    /**
     * @return String : res
     */
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

    /**
     *
     */
    public void chargementFichier(String nomFichierChoisi) {
        try {
            File file = new File(nomFichierChoisi);
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

    /**
     * @return
     */
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

    /**
     * Cette methode trouve le chemin le plus fiable pour aller d'un SXX a un sommet SXX
     *
     * @param centre1 : String
     * @param centre2 : String
     * @return
     */
    public LinkedHashMap<String, Double> plusCourtCheminDijkstraFiabilite(String centre1, String centre2) {
        Map<String, LinkedHashMap<String, Double>> res = new HashMap<>();//Hash map qui permet de stocker les chemins les plus courts
        Map<String, Boolean> sommetsTraites = new HashMap<>();//Hash map permettant de marquer les sommet traiter 
        res.put(centre1, new LinkedHashMap<>());
        res.get(centre1).put(centre1, 1.0);//le premier centre(sommet) a une fiabilite de 1 
        //on itere sur tout les centres afin de les mettre a false -> les sommets ne sont pas encore marquer
        this.tousLesCentresToList().forEach(Centre -> {
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

    public LinkedHashMap<String, Double> plusCourtCheminDijkstraDistance(String centre1, String centre2) {
        Map<String, LinkedHashMap<String, Double>> res = new HashMap<>();//Hash map qui permet de stocker les chemins les plus courts
        Map<String, Boolean> sommetsTraites = new HashMap<>();//Hash map permettant de marquer les sommet traiter
        res.put(centre1, new LinkedHashMap<>());
        res.get(centre1).put(centre1, 0.0);//le premier centre(sommet) a une distance de 0
        //on itere sur tout les centres afin de les mettre a false -> les sommets ne sont pas encore marquer
        this.tousLesCentresToList().forEach(Centre -> {
            sommetsTraites.put(Centre.getNom(), false);
        });
        sommetsTraites.put(centre1, true);//le premier centre est le debut donc il traiter -> true
        List<String[]> fileAttente = new ArrayList<>();//creation d'une liste permettant d'ajouter les sommet traiter
        fileAttente.add(new String[]{centre1, "0"});//ajoute le premier sommet avec comme distance 0
        String[] donnee;
        while (!(fileAttente.isEmpty())) {//tant que cette liste n'est pas vide
            donnee = fileAttente.get(fileAttente.size() - 1);//sauvegarde la distance et le centre de la fin de la file dans la variable donnee
            fileAttente.remove(fileAttente.size() - 1);//supprime ces cet element
            String centre = donnee[0];//donne[0] vaut le nom du centre
            double distance = Double.parseDouble(donnee[1]);//donne[1] vaut la distance de type String convertie en double

            MaillonGrapheSec voisin = this.getCentre(centre).lVois;
            while (voisin != null) {
                String nomVoisin = voisin.getDestination();
                if (!sommetsTraites.get(nomVoisin)) {
                    if (!res.containsKey(nomVoisin)) {
                        // On arrive à cette endroit si nomVoisin n'a pas encore de chemin
                        res.put(nomVoisin, new LinkedHashMap<>(res.get(centre)));
                        res.get(nomVoisin).put(nomVoisin, voisin.getDistance() + distance);
                        fileAttente.add(new String[]{nomVoisin, String.valueOf(voisin.getDistance() + distance)});

                    } else {
                        // si nomVoisin a déjà un chemin
                        LinkedHashMap<String, Double> chemin = res.get(nomVoisin);
                        Double lastDistCentreDansChemin = null;
                        String lastNomCentreDansChemin = null;
                        for (Map.Entry<String, Double> centreChemin : chemin.entrySet()) {
                            // Dans cette boucle on récupère l'extrémité du chemin <centre1, nomVoisin>
                            lastNomCentreDansChemin = centreChemin.getKey(); // le nom de l'extrémité
                            lastDistCentreDansChemin = centreChemin.getValue(); // la distance de l'extrémité
                        }
                        if (lastDistCentreDansChemin > voisin.getDistance() + distance) {
                            // Si on arrive ici, ca veut dire que le chemin initial entre le centre1 et nomVoisin n'est pas le plus court à ce moment la
                            res.put(nomVoisin, new LinkedHashMap<>(res.get(donnee[0])));
                            res.get(nomVoisin).put(lastNomCentreDansChemin, voisin.getDistance() + distance);
                            // Donc on créer le nouveau chemin le plus court entre centre1 et nomVoisin

                            // Comme il y a un nouveau chemin, ca veut dire que la fileAttente est obsolete car elle n'est pas à jour
                            // Grace a la boucle ci dessous, on retire l'ancien nomVoisin puis on le remplace par le nouveau avec sa nouvelle distance
                            int i = 0;
                            boolean check = false;
                            while (!check && i < fileAttente.size()) {
                                if (fileAttente.get(i)[0].equals(nomVoisin)) {
                                    check = true;
                                    fileAttente.remove(i);
                                    fileAttente.add(new String[]{nomVoisin, String.valueOf(voisin.getDistance() + distance)});
                                }
                                i++;
                            }
                        }

                    }
                }
                voisin = voisin.suiv;
            }
            if (fileAttente.size() >= 2) {
                // si on arrive ca veut dire qu'il faut mettre le sommetTraite le plus court tout en haut de la liste pour la traité en premier
                int maxiDistIndice = 0;
                for (int i = 1; i < fileAttente.size(); i++) {
                    if (Double.parseDouble(fileAttente.get(i)[1]) < Double.parseDouble(fileAttente.get(maxiDistIndice)[1])) {
                        // si on arrive ici, ca veut dire qu'on a trouvé le plus court au ieme moment
                        maxiDistIndice = i;
                    } else if (Double.parseDouble(fileAttente.get(i)[1]) == Double.parseDouble(fileAttente.get(maxiDistIndice)[1])) {
                        // si on arrive ici, ca veut dire que deux sommets ont la même distance donc je met en priorité le plus petit sommet (ex: D'abord S9 puis S15, et pas S15 puis S9)
                        String[] listNomCentre = fileAttente.get(i)[0].split("");
                        String[] listMaxiNomCentre = fileAttente.get(maxiDistIndice)[0].split("");
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
                            maxiDistIndice = i;
                        }
                    }
                }
                sommetsTraites.put(fileAttente.get(maxiDistIndice)[0], true); // je marque le sommet qui va etre traité
                fileAttente.add(new String[]{fileAttente.get(maxiDistIndice)[0], String.valueOf(fileAttente.get(maxiDistIndice)[1])});
                fileAttente.remove(maxiDistIndice);
            }
        }
        if (!(res.containsKey(centre2))) {
            return null;
        }
        return res.get(centre2);
    }

    public LinkedHashMap<String, Double> plusCourtCheminDijkstraDuree(String centre1, String centre2) {
        Map<String, LinkedHashMap<String, Double>> res = new HashMap<>();//Hash map qui permet de stocker les chemins les plus courts
        Map<String, Boolean> sommetsTraites = new HashMap<>();//Hash map permettant de marquer les sommet traiter
        res.put(centre1, new LinkedHashMap<>());
        res.get(centre1).put(centre1, 0.0);//le premier centre(sommet) a une durée de 0
        //on itere sur tout les centres afin de les mettre a false -> les sommets ne sont pas encore marquer
        this.tousLesCentresToList().forEach(Centre -> {
            sommetsTraites.put(Centre.getNom(), false);
        });
        sommetsTraites.put(centre1, true);//le premier centre est le debut donc il traiter -> true
        List<String[]> fileAttente = new ArrayList<>();//creation d'une liste permettant d'ajouter les sommet traiter
        fileAttente.add(new String[]{centre1, "0"});//ajoute le premier sommet avec comme durée 0
        String[] donnee;
        while (!(fileAttente.isEmpty())) {//tant que cette liste n'est pas vide
            donnee = fileAttente.get(fileAttente.size() - 1);//sauvegarde la durée et le centre de la fin de la file dans la variable donnee
            fileAttente.remove(fileAttente.size() - 1);//supprime ces cet element
            String centre = donnee[0];//donne[0] vaut le nom du centre
            double duree = Double.parseDouble(donnee[1]);//donne[1] vaut la durée de type String convertie en double

            MaillonGrapheSec voisin = this.getCentre(centre).lVois;
            while (voisin != null) {
                String nomVoisin = voisin.getDestination();
                if (!sommetsTraites.get(nomVoisin)) {
                    if (!res.containsKey(nomVoisin)) {
                        // On arrive à cette endroit si nomVoisin n'a pas encore de chemin
                        res.put(nomVoisin, new LinkedHashMap<>(res.get(centre)));
                        res.get(nomVoisin).put(nomVoisin, voisin.getDuree() + duree);
                        fileAttente.add(new String[]{nomVoisin, String.valueOf(voisin.getDuree() + duree)});

                    } else {
                        // si nomVoisin a déjà un chemin
                        LinkedHashMap<String, Double> chemin = res.get(nomVoisin);
                        Double lastDurCentreDansChemin = null;
                        String lastNomCentreDansChemin = null;
                        for (Map.Entry<String, Double> centreChemin : chemin.entrySet()) {
                            // Dans cette boucle on récupère l'extrémité du chemin <centre1, nomVoisin>
                            lastNomCentreDansChemin = centreChemin.getKey(); // le nom de l'extrémité
                            lastDurCentreDansChemin = centreChemin.getValue(); // la durée de l'extrémité
                        }
                        if (lastDurCentreDansChemin > voisin.getDuree() + duree) {
                            // Si on arrive ici, ca veut dire que le chemin initial entre le centre1 et nomVoisin n'est pas le plus court à ce moment la
                            res.put(nomVoisin, new LinkedHashMap<>(res.get(donnee[0])));
                            res.get(nomVoisin).put(lastNomCentreDansChemin, voisin.getDuree() + duree);
                            // Donc on créer le nouveau chemin le plus court entre centre1 et nomVoisin

                            // Comme il y a un nouveau chemin, ca veut dire que la fileAttente est obsolete car elle n'est pas à jour
                            // Grace a la boucle ci dessous, on retire l'ancien nomVoisin puis on le remplace par le nouveau avec sa nouvelle durée
                            int i = 0;
                            boolean check = false;
                            while (!check && i < fileAttente.size()) {
                                if (fileAttente.get(i)[0].equals(nomVoisin)) {
                                    check = true;
                                    fileAttente.remove(i);
                                    fileAttente.add(new String[]{nomVoisin, String.valueOf(voisin.getDuree() + duree)});
                                }
                                i++;
                            }
                        }

                    }
                }
                voisin = voisin.suiv;
            }
            if (fileAttente.size() >= 2) {
                // si on arrive ca veut dire qu'il faut mettre le sommetTraite le plus court tout en haut de la liste pour la traité en premier
                int maxiDurIndice = 0;
                for (int i = 1; i < fileAttente.size(); i++) {
                    if (Double.parseDouble(fileAttente.get(i)[1]) < Double.parseDouble(fileAttente.get(maxiDurIndice)[1])) {
                        // si on arrive ici, ca veut dire qu'on a trouvé le plus court au ieme moment
                        maxiDurIndice = i;
                    } else if (Double.parseDouble(fileAttente.get(i)[1]) == Double.parseDouble(fileAttente.get(maxiDurIndice)[1])) {
                        // si on arrive ici, ca veut dire que deux sommets ont la même distance donc je met en priorité le plus petit sommet (ex: D'abord S9 puis S15, et pas S15 puis S9)
                        String[] listNomCentre = fileAttente.get(i)[0].split("");
                        String[] listMaxiNomCentre = fileAttente.get(maxiDurIndice)[0].split("");
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
                            maxiDurIndice = i;
                        }
                    }
                }
                sommetsTraites.put(fileAttente.get(maxiDurIndice)[0], true); // je marque le sommet qui va etre traité
                fileAttente.add(new String[]{fileAttente.get(maxiDurIndice)[0], String.valueOf(fileAttente.get(maxiDurIndice)[1])});
                fileAttente.remove(maxiDurIndice);
            }
        }
        if (!(res.containsKey(centre2))) {
            return null;
        }
        return res.get(centre2);
    }

    /**
     * @return
     */
    public double[][] floydWarshall() {
        MaillonGraphe tmp = this.premier;
        int cpt = 0; // taille de la matrice

        while (tmp != null) {
            cpt++;
            tmp = tmp.suiv;
        }

        double[][] matrice = new double[cpt][cpt];
//initialisation de la matrice ,sur la diagonale on ne met que des 0 sinon la valeur infinie
        for (int i = 0; i < cpt; i++) {
            for (int j = 0; j < cpt; j++) {
                if (i == j) {
                    matrice[i][j] = 0;
                } else {
                    matrice[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
//affectation des sommets a un indice pour la matrice
        Map<String, Integer> indexSommet = new TreeMap<>();

        tmp = this.premier;
        int index = 0;
        while (tmp != null) {
            String nomSommet = tmp.nom;
            indexSommet.put(nomSommet, index);
            index++;
            tmp = tmp.suiv;
        }

        tmp = this.premier;
        while (tmp != null) {
            //recupere le maillon de la liste principale
            String source = tmp.nom;
            MaillonGrapheSec voisins = tmp.lVois;
            while (voisins != null) {
                //recupere le maillon de la liste des voisins
                /*
               S1 -> S2 ->S3.S1 est le maillon principale et les autre maillons sont les voisins de S1
               S2-> S2 -S5
               matrice :
               S1[0,S2,S3,S4]
               S2[S1,0,S4,S5]
               etc...
                */
                String destination = voisins.getDestination();//recupere le voisin qui est relié au sommet principale
                double distance = voisins.getDistance();//recupere la distance

                int indexSource = getIndice(source, indexSommet);//recupere l'indice du sommet de la liste principale
                int indexDestination = getIndice(destination, indexSommet);//recupere l'indice du sommet voisin

                matrice[indexSource][indexDestination] = distance;//on affecte la distance a l'indice

                voisins = voisins.suiv;//on passe au voisin suivant
            }

            tmp = tmp.suiv;//on passe au maillon suivant jusqu'a la fin de la liste chainée
        }

        double[][] distances = new double[cpt][cpt];
        for (int i = 0; i < cpt; i++) {
            for (int j = 0; j < cpt; j++) {

                distances[i][j] = matrice[i][j];//initialisation

            }
        }
        double[][] predecesseurs = new double[cpt][cpt];

        for (int i = 0; i < cpt; i++) {
            for (int j = 0; j < cpt; j++) {
                if (i != j && matrice[i][j] != Double.POSITIVE_INFINITY) {
                    predecesseurs[i][j] = i;
                } else {
                    predecesseurs[i][j] = -1;
                }
            }
        }

        for (int k = 0; k < cpt; k++) {
            for (int i = 0; i < cpt; i++) {
                for (int j = 0; j < cpt; j++) {
                    if (distances[i][k] + distances[k][j] < distances[i][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                        predecesseurs[i][j] = predecesseurs[k][j];
                    }
                }
            }
        }

        afficherPlusCourtsChemins(predecesseurs, distances, indexSommet);

        return predecesseurs;
    }

    /**
     * Cette methode affiche tout les plus courts chemins
     *
     * @param predecesseurs
     * @param distances
     * @param indexSommet
     */
    public void afficherPlusCourtsChemins(double[][] predecesseurs, double[][] distances, Map<String, Integer> indexSommet) {
        int taille = predecesseurs.length;

        // Parcours de chaque paire de sommets pour afficher les chemins et les distances
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (i != j) {
                    System.out.println("Plus court chemin de " + getNomSommet(i, indexSommet) + " à " + getNomSommet(j, indexSommet) + ":");
                    afficherChemin(i, j, predecesseurs, indexSommet);
                    System.out.println();
                    System.out.println("Distance : " + distances[i][j]);
                    System.out.println();
                }
            }
        }
    }

    /**
     * @param source
     * @param destination
     * @param predecesseurs
     * @param indexSommet
     */
    public void afficherChemin(int source, int destination, double[][] predecesseurs, Map<String, Integer> indexSommet) {
        if (predecesseurs[source][destination] == -1) {
            System.out.println("Aucun chemin trouvé de " + getNomSommet(source, indexSommet) + " à " + getNomSommet(destination, indexSommet));
            return;
        }

        List<Integer> chemin = new ArrayList<>();
        construireChemin(source, destination, predecesseurs, chemin);

        System.out.print("  " + getNomSommet(source, indexSommet));
        int i = 0;
        for (i = 0; i < chemin.size(); i++) {
            System.out.print(" -> " + getNomSommet(chemin.get(i), indexSommet));
        }
        System.out.print(" -> " + getNomSommet(destination, indexSommet));
    }

    /**
     * @param source
     * @param destination
     * @param predecesseurs
     * @param chemin
     */
    private void construireChemin(int source, int destination, double[][] predecesseurs, List<Integer> chemin) {
        int predecesseur = (int) predecesseurs[source][destination];
        if (predecesseur != source) {
            construireChemin(source, predecesseur, predecesseurs, chemin);
        }
        chemin.add(predecesseur);
    }

    /**
     * @param index
     * @param indexSommet
     * @return
     */
    public String getNomSommet(int index, Map<String, Integer> indexSommet) {
        for (Map.Entry<String, Integer> entry : indexSommet.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return "";
    }

    /**
     * @param nomSommet
     * @param indexSommet
     * @return
     */
    public int getIndice(String nomSommet, Map<String, Integer> indexSommet) {
        Integer index = indexSommet.get(nomSommet);
        if (index != null) {
            return index;
        }
        throw new IllegalArgumentException("Problème : " + nomSommet);
    }


}
