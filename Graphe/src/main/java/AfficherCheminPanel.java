import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AfficherCheminPanel extends JOptionPane {
    private String autreSommetSelectionne;
    private Map<LCGraphe.MaillonGraphe, JLabel> sommet;
    public   DessinGraphe dessinGraphe;

     AfficherCheminPanel(String chemin, Map<LCGraphe.MaillonGraphe, JLabel> sommets,DessinGraphe d) {
        this.sommet = sommets;
        this.dessinGraphe = d;
          
          
          StringBuilder chaineChemin = new StringBuilder();//recupere le chemin sous forme de chaine
          JLabel label = new JLabel("Distance : ");
          JLabel dist = new JLabel(chemin + " km");//ajoute le chemin dans un Jlabel
          for (int i = 0; i < InterfaceGraphe.Graphe.chemin.size(); i++) {
               int sommetCourant = InterfaceGraphe.Graphe.chemin.get(i);
               if (i == 0 || sommetCourant != InterfaceGraphe.Graphe.chemin.get(i - 1)) {
                    chaineChemin.append(InterfaceGraphe.Graphe.getNomSommet(sommetCourant)+" -> ");
                    if (i < InterfaceGraphe.Graphe.chemin.size() - 1) {
                         System.out.print(" -> ");
                    }
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
        PanelDessin.setPreferredSize(new Dimension(500,500));
        PanelDessin.add(dessinGrapheRouge);

        JOptionPane.showMessageDialog(null, PanelDessin, "Graphe Chemin", JOptionPane.PLAIN_MESSAGE);
    }
}
