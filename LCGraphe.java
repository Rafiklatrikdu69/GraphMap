
class LCGraphe {
    public class MaillonGrapheSec {
        private double fiab;
        private double dist;
        private double dur;
        private String dest;
        private MaillonGrapheSec suiv;

        private MaillonGrapheSec(double f, double dt , double dr, String d) {
            fiab = f;
            dist = dt;
            dur = dr;
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
        nouv.suiv= this.premier;
        this.premier = nouv;
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
    public String  toString(){
        String s = "";
        MaillonGraphe tmp = this.premier;
        while(tmp!=null){
            s =s + tmp.nom + ":";
            MaillonGrapheSec  tmp2 = tmp.lVois;
            while(tmp2!=null){
                s =s + tmp2.dest + "fiabilite " + tmp2.fiab + "diatance "+tmp2.dist + "duree " + tmp2.dur;
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
            s =s + "trajet de "+tmp.nom + ":";
            MaillonGrapheSec  tmp2 = tmp.lVois;
            while(tmp2!=null){
                s =s + tmp2.dest + "fiabilite " + tmp2.fiab + "diatance "+tmp2.dist + "duree " + tmp2.dur;
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
            s =s + "voisin de "+tmp.nom + ":";
            MaillonGrapheSec  tmp2 = tmp.lVois;
            while(tmp2!=null){
                s =s + tmp2.dest + "fiabilite " + tmp2.fiab + "diatance "+tmp2.dist + "duree " + tmp2.dur;
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