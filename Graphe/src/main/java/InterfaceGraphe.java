
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InterfaceGraphe extends JFrame {

    private static LCGraphe Graphe = new LCGraphe();
    
    private JMenuBar menu;
    private JMenu j;
    private JMenuItem option1, option2, option3;
    private JPanel panel, cp;

    public InterfaceGraphe() {
        super();
        Graphe.chargementFichier();
        Graphe.toString();
        initComponents();
        //initEventListeners();
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
        j = new JMenu("menu");
        option1 = new JMenuItem("Afficher tous les dispensaires");
        option2 = new JMenuItem("Sélectionner un dispensaire");
        option3 = new JMenuItem("option 3");
        j.add(option1);
        j.add(option2);
        j.add(option3);
        menu.add(j);
        setJMenuBar(menu);
        cp.add(menu);
        DessinGraphe dessin = new DessinGraphe(30, 100);

        // Ajout de l'objet DessinGraphe au JPanel cp
      
        
    }

    

    class DessinGraphe extends LCGraphe {

        private double x;
        private double y;

        private LCGraphe.MaillonGraphe premier;
        private LCGraphe.MaillonGraphe suiv=null;

        DessinGraphe(double axeX, double axeY) {
            super();
            this.x = axeX;
            this.y = axeY;
            premier = null;

        }

   public void dessinerSommet(Graphics2D g2d, MaillonGraphe sommet) {
    int rayon = 20; // rayon du cercle représentant le sommet
    int x = (int) (this.x);
    int y = (int) (this.y);

    g2d.setColor(Color.BLUE);
    g2d.fillOval(x - rayon / 2, y - rayon / 2, rayon, rayon); // dessin du cercle
    g2d.setColor(Color.BLACK);
    g2d.drawOval(x - rayon / 2, y - rayon / 2, rayon, rayon); // contour du cercle
}



public void dessin(JPanel panel) {
    Graphics2D g2d = (Graphics2D) panel.getGraphics();
    LCGraphe.MaillonGraphe tmp = this.premier;
    while (tmp != null) {
        System.out.println(tmp);
        dessinerSommet(g2d, tmp);
        tmp = tmp.suiv;
    }
}

    }

}
