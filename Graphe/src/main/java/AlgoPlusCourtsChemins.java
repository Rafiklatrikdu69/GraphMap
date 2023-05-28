import javax.swing.*;

public class AlgoPlusCourtsChemins extends JOptionPane {
private  String autreSommetSelectionne;

    /**
     *
     */
    public AlgoPlusCourtsChemins() {
        // Création des composants de votre boîte de dialogue
        JLabel label = new JLabel("Sélectionnez l'autre sommet:");
        JComboBox<String> comboBoxAutreSommet = new JComboBox<>();
        LCGraphe.MaillonGraphe m = grapheConstant.graphe.getPremier();
        while (m!=null){
            comboBoxAutreSommet.addItem(m.getNom());
            m = m.getSuivant();
        }


        JPanel panel = new JPanel();
        panel.add(label);


        panel.add(comboBoxAutreSommet);


        int result = showOptionDialog(null, panel, "AlgoPlusCourtsChemins", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);


        if (result == JOptionPane.OK_OPTION) {
             autreSommetSelectionne = (String) comboBoxAutreSommet.getSelectedItem();

            System.out.println("Autre sommet sélectionné : " + autreSommetSelectionne);
        }
    }

    /**
     *
     * @return
     */
    public String getChoixSommet(){
        return autreSommetSelectionne;
    }
}
