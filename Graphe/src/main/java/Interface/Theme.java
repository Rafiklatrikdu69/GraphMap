package Interface;

import java.awt.Color;

public enum Theme {
    DARK(new Color(0,107,111), Color.BLUE, new Color(124,47,0), new Color(0, 100, 255), new Color(255,219,56), new Color(175,143,0)),
    LIGHT(new Color(58,166,170), Color.BLUE, new Color(175,110,70), new Color(0, 100, 255), new Color(255,219,56), new Color(255,219,56));

    private Color couleurSommetParDefaut;
    private Color couleurSommetSelectParDefaut;
    private Color couleurAreteParDefaut;
    private Color couleurAreteCheminSelect;
    private Color couleurTexteParDefaut;


    Theme(Color couleurParDefaut, Color couleurSommetSelectParDefaut, Color couleurAreteParDefaut, Color couleurAreteCheminSelect, Color couleurTexteParDefaut, Color couleurTexteSelect){
        this.couleurSommetParDefaut = couleurParDefaut;
        this.couleurSommetSelectParDefaut = couleurSommetSelectParDefaut;
        this.couleurAreteParDefaut = couleurAreteParDefaut;
        this.couleurAreteCheminSelect = couleurAreteCheminSelect;
        this.couleurTexteParDefaut = couleurTexteParDefaut;
    }

    public Color getCouleurSommetParDefaut() {
        return couleurSommetParDefaut;
    }

    public Color getCouleurSommetSelectParDefaut() {
        return couleurSommetSelectParDefaut;
    }

    public Color getCouleurAreteParDefaut() {
        return couleurAreteParDefaut;
    }

    public Color getCouleurAreteCheminSelect() {
        return couleurAreteCheminSelect;
    }

    public Color getCouleurTexteParDefaut() {
        return couleurTexteParDefaut;
    }

    public void setCouleurSommetParDefaut(Color couleurSommetParDefaut) {
        this.couleurSommetParDefaut = couleurSommetParDefaut;
    }

    public void setCouleurSommetSelectParDefaut(Color couleurSommetSelectParDefaut) {
        this.couleurSommetSelectParDefaut = couleurSommetSelectParDefaut;
    }

    public void setCouleurAreteParDefaut(Color couleurAreteParDefaut) {
        this.couleurAreteParDefaut = couleurAreteParDefaut;
    }

    public void setCouleurAreteCheminSelect(Color couleurAreteCheminSelect) {
        this.couleurAreteCheminSelect = couleurAreteCheminSelect;
    }

    public void setCouleurTexteParDefaut(Color couleurTexteParDefaut) {
        this.couleurTexteParDefaut = couleurTexteParDefaut;
    }

}
