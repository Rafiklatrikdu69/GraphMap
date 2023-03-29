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
    }

    private MaillonGraphe premier;

    public LCGraphe(){
        premier = null;
    }

    public void addMain(String ori, String t){
        MaillonGraphe nouv = new MaillonGraphe(ori,t);
        nouv.suiv = this.premier;
        this.premier = nouv;
    }


    public boolean existEdge(String o, String d){
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
    public boolean addEdge(String o, String d, double fiab, double dist, double dur) throws ExistEdgeException{
        boolean status = true;
        MaillonGrapheSec tmp2 = null;
        MaillonGraphe tmp = this.premier;
        while (!tmp.nom.equals(o)){
            tmp = tmp.suiv;
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
        MaillonGrapheSec nouv = new MaillonGrapheSec(fiab, dist, dur, d);
        nouv.suiv = tmp.lVois;
        tmp.lVois = nouv;

        MaillonGrapheSec nouv2 = new MaillonGrapheSec(fiab, dist, dur, o);
        tmp = this.premier;
        while (!tmp.nom.equals(d)){
            tmp = tmp.suiv;
        }
        nouv2.suiv = tmp.lVois;
        tmp.lVois = nouv2;
        return status;
    }
    public String  toString(){
        String s = "";
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            MaillonGrapheSec  tmp2 = tmp.lVois;
            while(tmp2!=null){
                s += tmp2.dest + "fiabilite " + tmp2.fiab + "distance "+tmp2.dist + "durée " + tmp2.dur;
                tmp2 = tmp2.suiv;
            }
            tmp = tmp.suiv;
        }
        return s;
        
    }
    
    public String afficheBloc(){
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
    public String trajet(){
        String s = "";
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            MaillonGrapheSec  tmp2 = tmp.lVois;
            while(tmp2!=null){
                s += tmp2.dest + "fiabilite " + tmp2.fiab + "distance "+tmp2.dist + "durée " + tmp2.dur;
                tmp2 = tmp2.suiv;
            }
            tmp = tmp.suiv;
        }
        s = s + "\n";
        return s;
}
    public String toutLesVoisins(String disp){
        String s = "";
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            if(tmp.nom.equals(disp)){
                MaillonGrapheSec  tmp2 = tmp.lVois;
                while(tmp2!=null){
                    s =s + tmp2.dest + " [fiabilite=" + tmp2.fiab + ", distance="+tmp2.dist + ", durée=" + tmp2.dur+"]\n";
                    tmp2 = tmp2.suiv;
                }
            }
            tmp = tmp.suiv;
        }
        s = s + "\n";
        return s;
    }
    public void clear(){
        
    }
}