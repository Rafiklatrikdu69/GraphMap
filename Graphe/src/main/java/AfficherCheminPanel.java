import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AfficherCheminPanel extends JOptionPane {
    private String autreSommetSelectionne;
    private Map<LCGraphe.MaillonGraphe, JLabel> sommet;
    private  DessinGraphe dessinGraphe;

    public AfficherCheminPanel(String chemin, Map<LCGraphe.MaillonGraphe, JLabel> sommets,DessinGraphe d) {
        this.sommet = sommets;
        this.dessinGraphe = d;
        JLabel label = new JLabel("Distance : ");
        JLabel dist = new JLabel(chemin + " km");//ajoute le chemin dans un Jlabel

        StringBuilder chaineChemin = new StringBuilder();//recupere le chemin sous forme de chaine
        for (int i = 0; i < grapheConstant.graphe.chemin.size(); i++) {
            int sommetCourant = grapheConstant.graphe.chemin.get(i);//recupere le sommet de la liste contenant le chemin
            chaineChemin.append(grapheConstant.graphe.getNomSommet(sommetCourant));

            if (i < grapheConstant.graphe.chemin.size() - 1) {
                chaineChemin.append(" -> ");
            }
        }
        JLabel cheminCourt = new JLabel(chaineChemin.toString());//ajoute le chemin dans Jlabel

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(label);
        panel.add(dist);
        panel.add(cheminCourt);
//affiche le chemin dans un JOption Pane
        int result = showOptionDialog(null, panel, "Chemin Court", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        //en cours de developpement -> Probleme bug Graphe pas mis a jour !!!
       // dessinGraphe.setSommets(sommet);
        dessinGraphe.repaint();
        AffichagePlusCourtsChemin dessinGrapheRouge = new AffichagePlusCourtsChemin(sommet,dessinGraphe);
        JPanel PanelDessin = new JPanel();
        //PanelDessin.setPreferredSize(new Dimension(500,500));
        PanelDessin.add(dessinGrapheRouge);

        JOptionPane.showMessageDialog(null, PanelDessin, "Graphe Chemin", JOptionPane.PLAIN_MESSAGE);
    }
}
