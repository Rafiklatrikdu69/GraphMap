package Interface.JOptionPane;

import LCGraphe.Graphe;

import javax.swing.*;
import java.awt.*;

public class AjoutAretePanel extends JPanel{
    private JComboBox<String> choixSommet1, choixSommet2;
    private JSpinner spinnerDistance, spinnerDuree, spinnerFiabilite;

    public AjoutAretePanel(Graphe graphe) {
        setLayout(new GridLayout(5,2));




        initComponents(graphe);

    }

    private void initComponents(Graphe graphe){
        choixSommet1 = new JComboBox<>();
        choixSommet2 = new JComboBox<>();
        try {
            graphe.tousLesSommetToList().forEach(maillonGraphe -> {
                choixSommet1.addItem(maillonGraphe.getNom());
                choixSommet2.addItem(maillonGraphe.getNom());
            });
        } catch (Exception ignored) {}
        add(new JLabel("Sommet Source"));
        add(choixSommet1);
        add(new JLabel("Sommet Destination"));
        add(choixSommet2);
        add(new JLabel("Distance (Km)"));
        spinnerDistance = new JSpinner(new SpinnerNumberModel(1, 1, 300, 1));
        add(spinnerDistance);

        add(new JLabel("Durée (minutes)"));
        spinnerDuree = new JSpinner(new SpinnerNumberModel(1, 1, 300, 1));
        add(spinnerDuree);
        add(new JLabel("Fiabilité (1 = 10%)"));
        spinnerFiabilite= new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        add(spinnerFiabilite);
    }
    /**
     * Creation de la methode pour creer  une saisie pour l'utilisateur afin de creer le sommet
     *
     */
    public String getChoixSommet1(){
        return (String) choixSommet1.getSelectedItem();
    }
    public String getChoixSommet2(){
        return (String) choixSommet2.getSelectedItem();
    }

    public double getDistance() {
        return Double.parseDouble(spinnerDistance.getValue().toString());
    }

    public double getDuree() {
        return Double.parseDouble(spinnerDuree.getValue().toString());
    }

    public double getFiabilite() {
        return Double.parseDouble(spinnerFiabilite.getValue().toString());
    }
}
