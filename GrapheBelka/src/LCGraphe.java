import java.util.ArrayList;

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
    
    public void ajoutVoisin(String o, String d, double fiab, double dist, double dur) throws ExistEdgeException, NotExistMainException{
        MaillonGrapheSec tmp2 = null;
        MaillonGraphe tmp = this.premier;
        MaillonGraphe tmp3 = this.premier;
        while (!tmp.nom.equals(o)){
            tmp = tmp.suiv;
            if(tmp == null){
                throw new NotExistMainException("Le sommet "+o+" n'existe pas!");
            }
        }
        if(tmp != null){
            tmp2 = tmp.lVois;
            while(tmp2!=null){
                if(tmp2.dest.equals(d)){
                    throw new ExistEdgeException("Impossible de faire des doublons !");
                }
                tmp2 = tmp2.suiv;
            }
        }
        while(!tmp3.nom.equals(d)){
            tmp3 = tmp3.suiv;
            if(tmp3 == null){
                throw new NotExistMainException("Le sommet "+d+" n'existe pas!");
            }
        }
        MaillonGrapheSec nouv = new MaillonGrapheSec(fiab, dist, dur, d);
        nouv.suiv = tmp.lVois;
        tmp.lVois = nouv;

        MaillonGrapheSec nouv2 = new MaillonGrapheSec(fiab, dist, dur, o);
        nouv2.suiv = tmp3.lVois;
        tmp3.lVois = nouv2;
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
        
    public ArrayList<String> plusCourtChemin(String som1, String som2){
        HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
        HashMap<String, Boolean> marquage = new HashMap<String, Boolean>();
        MaillonGraphe tmp = this.premier;
        res.put(som1, new ArrayList<>());
        res.get(som1).add(som1);
        while(tmp!=null){
            marquage.put(tmp.nom, false);
            tmp = tmp.suiv;
        }
        marquage.put(som1, true);
        File newFile = new File();
        newFile.enfiler(som1);
        while(!newFile.estVide()){
            String som = newFile.defiler();
            // Traitement
            
            ArrayList<MaillonGrapheSec> succSom = this.getEdges(som);
            for(int i = 0; i < succSom.size(); i++){
                String nomSucc = succSom.get(i).getDest();
                if(!marquage.get(nomSucc)){
                    res.put(nomSucc, new ArrayList<String>(res.get(som)));
                    res.get(nomSucc).add(nomSucc);
                    marquage.put(nomSucc, true);
                    newFile.enfiler(nomSucc);
                }
            }
        }
        if(!(res.containsKey(som2))){
            return null;
        }
        return res.get(som2);
    }
    

    public void clear(){
        
    }
}