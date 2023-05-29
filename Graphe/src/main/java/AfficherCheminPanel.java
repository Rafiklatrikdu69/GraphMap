import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AfficherCheminPanel extends JOptionPane {
    private String autreSommetSelectionne;
    private Map<LCGraphe.MaillonGraphe, JLabel> sommet;
    private  DessinGraphe dessinGraphe;

    public AfficherCheminPanel(String chemin, Map<LCGraphe.MaillonGraphe, JLabel> sommets,DessinGraphe d) {
        this.sommet = sommets;
        this.dessinGraphe = d;
        JLabel label = new JLabel("Distance : ");
        JLabel dist = new JLabel(chemin + " km");

        StringBuilder cheminCourts = new StringBuilder();
        for (int i = 0; i < grapheConstant.graphe.chemin.size(); i++) {
            int sommetCourant = grapheConstant.graphe.chemin.get(i);
            cheminCourts.append(grapheConstant.graphe.getNomSommet(sommetCourant));

            if (i < grapheConstant.graphe.chemin.size() - 1) {
                cheminCourts.append(" -> ");
            }
        }
        JLabel CheminC = new JLabel(cheminCourts.toString());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(label);
        panel.add(dist);
        panel.add(CheminC);

        int result = showOptionDialog(null, panel, "AlgoPlusCourtsChemins", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        dessinGraphe.setSommets(sommet);
        dessinGraphe.repaint();
        AffichagePlusCourtsChemin dessinGrapheRouge = new AffichagePlusCourtsChemin(sommet,dessinGraphe);
        JPanel PanelDessin = new JPanel();
        //PanelDessin.setPreferredSize(new Dimension(500,500));
        PanelDessin.add(dessinGrapheRouge);

        JOptionPane.showMessageDialog(null, PanelDessin, "Graphe Chemin", JOptionPane.PLAIN_MESSAGE);
    }
}
