import java.util.*;
public class Graphe {
    private String nom;
    private Sommets sommets;
    private int nombreSommets;
    
    Graphe(String nom){
        this.nom = nom;
        this.sommets = null;
        this.nombreSommets = 0;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public ArrayList<Sommets> getSommets(){
        ArrayList<Sommets> res = new ArrayList<>();
        Sommets sommet = this.sommets;
        if(sommet != null){
            res.add(sommet);
            while(sommet.getAutreSommet() != null){
                sommet = sommet.getAutreSommet();
                res.add(sommet);
            }
        }
        return res;
    }
    public void afficherSommets(){
        Sommets sommet = this.sommets;
        if(sommet != null) {
            System.out.print(sommet.getTete() +" ");
            while(sommet.getAutreSommet() != null){
                sommet = sommet.getAutreSommet();
                System.out.print(sommet.getTete()+" ");
            }
            System.out.println();
        }
    }
    public boolean existeSommet(String nomSommet){
        boolean res = false;
        if(!(this.sommets == null)){
            if(this.sommets.getTete().equals(nomSommet)){
                res = true;
            }
            else{
                Sommets sommet = this.sommets;
                while(sommet.getAutreSommet() != null && !res){
                    sommet = sommet.getAutreSommet();
                    if(sommet.getTete().equals(nomSommet)){
                        res = true;
                    } 
                }
            }
        }
        return res;
    }
    public boolean ajoutSommet(String nom, String type){
        boolean res = false;
        if(!(this.existeSommet(nom))){
            if(this.sommets == null){
                this.sommets = new Sommets(nom, type);
                this.nombreSommets++;
            } else {
                Sommets sommet = this.sommets;
                while(!(sommet.getAutreSommet() == null)){
                    sommet = sommet.getAutreSommet();
                }
                this.nombreSommets++;
                sommet.setAutreSommet(new Sommets(nom, type));
            }
            res = true;
        }
        return res;
    }
    public boolean ajoutSuccesseur(String som1, String som2, int fiabilite, int distance, int duree){
        boolean res = false;
        if((this.existeSommet(som1)) && (this.existeSommet(som2))){
            Sommets sommet = this.sommets;
            if(sommet.getTete().equals(som1)){
                res = sommet.ajoutVoisin(som2, fiabilite, distance, duree);
            } else {
                while(!(sommet.getAutreSommet() == null) && !res){
                    sommet = sommet.getAutreSommet();
                    if(sommet.getTete().equals(som1)){
                        res = sommet.ajoutVoisin(som2, fiabilite, distance, duree);
                    }
                }
            }
        }
        return res;
    }
    public int getNombreVoisins(String som){
        int res = 0;
        if(this.existeSommet(som)){
            Sommets sommet = this.sommets;
            if(sommet.getTete().equals(som)){
                res = sommet.getNombreVoisins();
            }
            while(sommet.getAutreSommet()!= null){
                sommet = sommet.getAutreSommet();
                if(sommet.getTete().equals(som)){
                    res = sommet.getNombreVoisins();
                }
            }
        }
        return res;
    }
    public ArrayList<Arete> getTousVoisins(){
        ArrayList<Arete> res = null;
        Sommets sommet = this.sommets;
        if(sommet!= null){
            res = sommet.getVoisins();
            while(sommet.getAutreSommet()!= null){
                sommet = sommet.getAutreSommet();
                ArrayList<Arete> aretes = sommet.getVoisins();
                for(int i = 0; i < aretes.size(); i++){
                    res.add(aretes.get(i));
                }
            }
        }
        return res;
    }
    public void afficherVoisins(String som){
        if(this.existeSommet(som)){
            Sommets sommet = this.sommets;
            if(sommet.getTete().equals(som)){
                sommet.afficherVoisins();
            }
            else{
                boolean res = false;
                while(!(sommet.getAutreSommet() == null) && !res){
                    sommet = sommet.getAutreSommet();
                    if(sommet.getTete().equals(som)){
                        sommet.afficherVoisins();
                        res = true;
                    }
                }
            }
        }
    }
    public int getNombreSommets(){
        return this.nombreSommets;
    }
    public Sommets getSommet(String nomSommet){
        Sommets sommet = this.sommets;
        Sommets res = null;
        if(sommet != null) {
            if(sommet.getTete().equals(nomSommet)){
                res = sommet;
            }
            while(res == null && sommet.getAutreSommet()!= null){
                sommet = sommet.getAutreSommet();
                if(sommet.getTete().equals(nomSommet)){
                    res = sommet;
                }
            }
        }
        return res;
    } 

    public ArrayList<String> plusCourtChemin(String som1, String som2){
        HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
        HashMap<String, Boolean> marquage = new HashMap<String, Boolean>();
        ArrayList<Sommets> listSommets = this.getSommets();
        res.put(som1, new ArrayList<>());
        res.get(som1).add(som1);
        for(int i = 0; i < listSommets.size(); i++){
            marquage.put(listSommets.get(i).getTete(), false);
        }
        marquage.put(som1, true);
        File newFile = new File("file");
        newFile.enfiler(som1);
        while(!newFile.estVide()){
            String som = newFile.defiler();
            // Traitement
            
            ArrayList<Arete> succSom = this.getSommet(som).getVoisins();
            for(int i = 0; i < succSom.size(); i++){
                String nomSucc = succSom.get(i).getSommet();
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

}
class File{
    private String nom;
    private ArrayList<String> contenu;
    
    File(String nom){
        this.nom = nom;
        this.contenu = new ArrayList<String>();
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public ArrayList<String> getContenu(){
        return this.contenu;
    }

    public boolean enfiler(String x){
        boolean status = this.contenu.add(x);
        return status;
    }

    public String defiler(){
        String res = this.contenu.remove(this.contenu.size()-1);
        return res;
    }
    public boolean estVide(){
        return this.contenu.isEmpty();
    }

}
class Sommets {
    private String tete;
    private String type;
    private Sommets autreSommet;
    private Arete voisins;
    private int nombreVoisins;

    Sommets(String tete, String type) {
        this.tete = tete;
        this.type = type;
        this.autreSommet = null;
        this.voisins = null;
        this.nombreVoisins = 0;
    }

    public String toString(){
        return "[Nom: "+this.tete+", Type: "+this.type+"]";
    }
    public String getTete() {
        return this.tete;
    }

    public String getType() {
        return this.type;
    }

    public Sommets getAutreSommet() {
        return this.autreSommet;
    }

    public Arete getArete() {
        return this.voisins;
    }

    public int getNombreVoisins() {
        return this.nombreVoisins;
    }
    
    public void setTete(String newTete) {
        this.tete = newTete;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    public void setAutreSommet(Sommets newSommet) {
        this.autreSommet = newSommet;
    }

    public void setArrete(Arete newArrete) {
        this.voisins = newArrete;
    }

    public boolean ajoutVoisin(String newSommet, int newFiabilite, int newDistance, int newDuree) {
        boolean res = false;
        if(this.voisins == null){
            this.voisins = new Arete(newSommet, this.tete, newFiabilite,  newDistance, newDuree);
            this.nombreVoisins++;
            res = true;
        }
        else {
            boolean check = false;
            Arete voisin  = this.voisins;
            if(voisin.getSommet().equals(newSommet)){
                check = true;
            }
            while(voisin.getSuivant() != null && !check){
                voisin = voisin.getSuivant();
                if(voisin.getSommet().equals(newSommet)){
                    check = true;
                }
            }
            if(!check){
                voisin.setSuivant(new Arete(newSommet,this.tete,newFiabilite,newDistance,newDuree));
                this.nombreVoisins++;
                res = true;
            }
        }
        return res;
    }
    
    public ArrayList<Arete> getVoisins(){
        ArrayList<Arete> res = new ArrayList<Arete>();
        Arete voisin = this.voisins;
        while (voisin!= null) {
            res.add(voisin);
            voisin = voisin.getSuivant();
        }
        return res;
    }

    public void afficherVoisins() {
        Arete voisin = this.voisins;
        while (voisin != null) {
            System.out.println(voisin);
            voisin = voisin.getSuivant();
        }
    } 
}
class Arete {
    private String sommet;
    private String source;
    private int fiabilite;
    private int distance;
    private int duree;
    private Arete suivant;
    Arete(String newSommet,String newSource,int newFiabilite, int newDistance,int newDuree){
        this.sommet = newSommet;
        this.source = newSource;
        this.fiabilite = newFiabilite;
        this.distance = newDistance;
        this.duree = newDuree;
        this.suivant  = null;
    }

    public Arete getSuivant(){
        return this.suivant;
    }
    public void setSuivant(Arete newSuivant){
        this.suivant = newSuivant;
    }
    public int getDuree(){
        return this.duree;
    }
    public String getSommet(){
        return this.sommet;
    }
    public int getDistance(){
        return this.duree;
    }
    public int getFiabilite(){
      return this.fiabilite;
    }
    public String getSource(){
      return this.source;
    }
    public String toString(){
        String msg = "";
        msg += this.sommet+" (durée: " + this.duree + " distance: " + this.distance+ " fiabilité: "+this.fiabilite+")";
        return msg;
    }
}