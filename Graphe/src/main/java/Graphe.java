public class Graphe {
    private String nom;
    private Sommets sommets;
    Graphe(String nom){
        this.nom = nom;
        this.sommets = null;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public void afficherSommet(){
        Sommets sommet = this.sommets;
        if(sommet != null){
            System.out.println(sommet.getTete());
            while(sommet.getAutreSommet() != null){
                sommet = sommet.getAutreSommet();
                System.out.println(sommet.getTete());
            }
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
            } else {
                Sommets sommet = this.sommets;
                while(!(sommet.getAutreSommet() == null)){
                    sommet = sommet.getAutreSommet();
                }
                sommet.setAutreSommet(new Sommets(nom, type));
            }
            res = true;
        }
        return res;
    }
    public boolean ajoutSuccesseur(String sommet1, String sommet2, int fiabilite, int distance, int duree){
        boolean res = false;
        if((this.existeSommet(sommet1)) && (this.existeSommet(sommet2))){
            Sommets sommet = this.sommets;
            if(sommet.getTete().equals(sommet1)){
                sommet.ajoutVoisin(sommet2, fiabilite, distance, duree);
                res = true;
            } else {
                while(!(sommet.getAutreSommet() == null) && !res){
                    sommet = sommet.getAutreSommet();
                    if(sommet.getTete().equals(sommet1)){
                        sommet.ajoutVoisin(sommet2, fiabilite, distance, duree);
                        res = true;
                    }
                }
            }
        }
        return res;
    }
    public void afficherSuccesseur(String som){
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
}
