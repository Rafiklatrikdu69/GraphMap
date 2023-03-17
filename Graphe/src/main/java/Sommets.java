public class Sommets {

    private String tete;
    private String type;
    private Sommets autreSommet;
    private Arete voisins;

    Sommets(String tete, String type) {
        this.tete = tete;
        this.type = type;
        this.autreSommet = null;
        this.voisins = null;
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

    public void ajoutVoisin(String newSommet, int newFiabilite, int newDistance, int newDuree) {
        if(this.voisins == null){
            this.voisins = new Arete(newSommet, this.tete, newFiabilite,  newDistance, newDuree);
        }
        else {
            Arete voisin  = this.voisins;
            while(voisin.getSuivant() != null){
                voisin = voisin.getSuivant();
            }
            voisin.setSuivant(new Arete(newSommet,this.tete,newFiabilite,newDistance,newDuree));
        }
    }
    
    public void afficherVoisins() {
        String msg = "";
        Arete voisin = this.voisins;
        while (voisin != null) {
            msg += voisin +" --> \n";
            voisin = voisin.getSuivant();
        }
        System.out.println(msg + " None");
    }
    
}
