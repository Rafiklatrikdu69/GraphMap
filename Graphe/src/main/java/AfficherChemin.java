import javax.swing.*;

public class AfficherChemin extends JOptionPane {
    private  String autreSommetSelectionne;
    public AfficherChemin(String chemin) {
        // Création des composants de votre boîte de dialogue
        DessinGraphe algo = new DessinGraphe();
        JLabel label = new JLabel("Distance : ");

        JLabel dist = new JLabel(chemin+" km") ;




        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(dist);


        // Affichage de la boîte de dialogue
        int result = showOptionDialog(null, panel, "AlgoPlusCourtsChemins", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        // Traitement du résultat

    }

}
