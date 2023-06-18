package Interface;

import java.awt.Color;

public enum Theme {
    DARK(new Color(0,107,111), new Color(199,98,122), new Color(199, 183, 199), new Color(240, 213, 67), new Color(35,60,78), new Color(160, 135, 0), new Color(255,219,56), new Color(0,107,111), new Color(199, 183, 199)),
    LIGHT(new Color(104,174,186), new Color(199,98,122), new Color(199, 183, 199), new Color(240, 213, 67), new Color(199,183,199), new Color(240, 213, 67), new Color(240,213,67), new Color(104, 174, 186), new Color(199, 183, 199));

    private Color couleurSommet;
    private Color couleurSommetSelect;
    private Color couleurArete;
    private Color couleurAreteChemin;
    private Color couleurTexte;
    private Color couleurBordureSommet;
    private Color couleurBordureSommetChemin;
    private Color couleurRectangleFiab;
    private Color couleurBordureRectFiab;

    Theme(Color couleurSommet, Color couleurSommetSelect, Color couleurBordureSommet, Color couleurBordureSommetChemin, Color couleurAreteParDefaut, Color couleurAreteCheminSelect, Color couleurTexteParDefaut, Color couleurRectangleFiab, Color couleurBordureRectFiab){
        this.couleurSommet = couleurSommet;
        this.couleurSommetSelect = couleurSommetSelect;
        this.couleurArete = couleurAreteParDefaut;
        this.couleurAreteChemin = couleurAreteCheminSelect;
        this.couleurTexte = couleurTexteParDefaut;
        this.couleurBordureSommet = couleurBordureSommet;
        this.couleurBordureSommetChemin = couleurBordureSommetChemin;
        this.couleurRectangleFiab = couleurRectangleFiab;
        this.couleurBordureRectFiab = couleurBordureRectFiab;
    }

    public Color getCouleurSommet() {
        return couleurSommet;
    }

    public Color getCouleurSommetSelect() {
        return couleurSommetSelect;
    }

    public Color getCouleurArete() {
        return couleurArete;
    }

    public Color getCouleurAreteChemin() {
        return couleurAreteChemin;
    }

    public Color getCouleurTexte() {
        return couleurTexte;
    }

    public void setCouleurSommet(Color couleurSommet) {
        this.couleurSommet = couleurSommet;
    }

    public void setCouleurSommetSelect(Color couleurSommetSelect) {
        this.couleurSommetSelect = couleurSommetSelect;
    }

    public void setCouleurArete(Color couleurArete) {
        this.couleurArete = couleurArete;
    }

    public void setCouleurAreteChemin(Color couleurAreteChemin) {
        this.couleurAreteChemin = couleurAreteChemin;
    }

    public void setCouleurTexte(Color couleurTexte) {
        this.couleurTexte = couleurTexte;
    }

    public Color getCouleurBordureSommet() {
        return couleurBordureSommet;
    }

    public Color getCouleurBordureSommetChemin() {
        return couleurBordureSommetChemin;
    }

    public Color getCouleurRectangleFiab() {
        return couleurRectangleFiab;
    }

    public Color getCouleurBordureRectFiab() {
        return couleurBordureRectFiab;
    }

}
