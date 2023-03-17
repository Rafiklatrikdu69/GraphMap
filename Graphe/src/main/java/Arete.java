public class Arete {
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
        msg += msg+ this.sommet+" (durée: " + this.duree + " distance: " + this.distance+ " fiabilité: "+this.fiabilite+")";
        return msg;
    }
}
