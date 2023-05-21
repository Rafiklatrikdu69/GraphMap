public class FileF<T> {
    private int tailleFile;
    private int iSommet;
    private T[] tab;

     FileF() {
        this.tailleFile = 30;
        this.iSommet = -1;
        this.tab = (T[]) new Object[tailleFile];
    }
    public T defiler(){
        T premier = tab[0];
        for (int i = 0;i<iSommet;i++){
            tab[i]= tab[i+1];
        }
       tab[iSommet--] = null;
       return premier;
    }
    public void enfiler(T valeur){
        tab[++iSommet]  = valeur;
    }
    public boolean estVide() {
        return (iSommet == -1);
    }

    public boolean estPleine() {
        return (iSommet == tailleFile - 1);
    }

}
