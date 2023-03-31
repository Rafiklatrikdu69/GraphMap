public class File{
    private ArrayList<String> contenu;
    
    File(){
        // if(type.equals("int")){
        //     this.contenu = new ArrayList<Integer>();
        // }
        // else if(type.equals("double")){
        //     this.contenu = new ArrayList<Double>();
        // }
        // else if(type.equals("string")){
        //     this.contenu = new ArrayList<String>();
        // }
        this.contenu = new ArrayList<String>();
    }
    
    public String getNom(){
        return this.nom;
    }
    
    // public ArrayList<Integer> getContenuInteger(){
    //     return this.contenu;
    // }
    // public ArrayList<Double> getContenuDouble(){
    //     return this.contenu;
    // }
    public ArrayList<String> getContenu(){
        return this.contenu;
    }

    public boolean enfiler(String x){
        boolean status = this.contenu.add(x);
        return status;
    }
    // public boolean enfiler(Integer x){
    //     boolean status = this.contenu.add(x);
    //     return status;
    // }
    // public boolean enfiler(Double x){
    //     boolean status = this.contenu.add(x);
    //     return status;
    // }

    public String defiler(){
        String res = this.contenu.remove(this.contenu.size()-1);
        return res;
    }
    public boolean estVide(){
        return this.contenu.isEmpty();
    }

}
