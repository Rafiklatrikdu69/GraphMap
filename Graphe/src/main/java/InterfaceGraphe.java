
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InterfaceGraphe extends JFrame {

    private static LCGraphe Graphe = new LCGraphe();
    private LinkedList<JLabel> labels = new LinkedList<>();
private JScrollPane scroll;
    private JMenuBar menu;
    private JMenu j;
    private JMenuItem option1, option2, option3;
    private JPanel cp;

    public InterfaceGraphe() {
        super();
        Graphe.chargementFichier();
        Graphe.toString();
        initComponents();
        setTitle("Graphe");
        setSize(1280, 720);
        setPreferredSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    private void initComponents() {
        cp = (JPanel) getContentPane();
        System.out.println("test");

        menu = new JMenuBar();
        j = new JMenu("Options");
        option1 = new JMenuItem("Afficher tous les dispensaires");
        option2 = new JMenuItem("Sélectionner un dispensaire");
        option3 = new JMenuItem("option 3");
        j.add(option1);
        j.add(option2);
        j.add(option3);
        for (JLabel label : labels) {
            JMenuItem menuItem = new JMenuItem(label.getText());
            j.add(menuItem);
        }
        menu.add(j);

        cp.add(menu, BorderLayout.NORTH);
        DessinGraphe dessinGraphe = new DessinGraphe(Graphe);
        cp.add(dessinGraphe, BorderLayout.CENTER);
       scroll = new JScrollPane(cp);
    }

    public class DessinGraphe extends JPanel {

        private LCGraphe graphe;
        private HashMap<LCGraphe.MaillonGraphe, Point> sommets;

        DessinGraphe(LCGraphe graphe) {
            super();
            this.graphe = graphe;
            sommets = new HashMap<>();
            labels = new LinkedList<>();
            setPreferredSize(new Dimension(1280, 720));
        }

        private void dessinerSommet(Graphics2D g2d, LCGraphe.MaillonGraphe sommet) {
            int rayon = 60;
            Point p = sommets.get(sommet);
            g2d.setColor(Color.RED);
            g2d.fillOval(p.x - rayon / 2, p.y - rayon / 2, rayon, rayon);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(p.x - rayon / 2, p.y - rayon / 2, rayon, rayon);
            JLabel label = new JLabel(sommet.getNom());
            label.setBounds(p.x - rayon / 2, p.y - rayon / 2, rayon, rayon);
            labels.add(label);
        }

  private void dessinerAretes(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);
    for (LCGraphe.MaillonGraphe sommet1 : sommets.keySet()) {
        Point p1 = sommets.get(sommet1);
        for (LCGraphe.MaillonGraphe sommet2 : sommets.keySet()) {
            Point p2 = sommets.get(sommet2);
            if (sommet1.estVoisin(sommet2.getNom())) {
                  g2d.fillOval(p1.x, p1.y, 10, 10);
                   g2d.fillOval(p2.x, p2.y, 10, 10);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
}

private int  compteSommet() {
    int nbSommet= 0;
   LCGraphe.MaillonGraphe tmp =  graphe.premier;
   while(tmp!= null){
       nbSommet++;
       tmp = tmp.suiv;
   }
   return nbSommet;
}
        private void dessinerGraphe(Graphics2D g2d) {
            // Calcul de la taille du cadre
            int tailleCadre = (int) (Math.sqrt(compteSommet()) * 200);

            // Calcul du centre du cadre
            int xCentre = getWidth() / 2;
            int yCentre = getHeight() / 2;
            int i = 1;
            // Placement des sommets dans le cadre
            LCGraphe.MaillonGraphe sommet = graphe.premier;
            while (sommet != null) {
                // Calcul de l'angle entre le sommet et l'axe horizontal
                double angle = 2 * Math.PI * i / compteSommet();

                // Calcul des coordonnées du sommet dans le cadre
                int x = xCentre + (int) (tailleCadre / 2 * Math.cos(angle));
                int y = yCentre + (int) (tailleCadre / 2 * Math.sin(angle));

                // Ajout du sommet à la liste
                sommets.put(sommet, new Point(x, y));

                // Dessin du sommet
                dessinerSommet(g2d, sommet);

                // Passage au sommet suivant
                sommet = sommet.suiv;
                
                i++;
            }
dessinerAretes(g2d);

        }



        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            dessinerGraphe(g2d);

            for (JLabel label : labels) {
                add(label);
            }
        }
    }

}
