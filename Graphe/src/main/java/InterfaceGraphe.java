import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InterfaceGraphe extends LCGraphe {

    private JMenuBar menu;
    private JMenu j;
    private JMenuItem option1,option2,option3;

    public InterfaceGraphe() {
        JFrame frame = new JFrame("Graphe");
          JPanel panel = new JPanel();
        initComponents();
        frame.setVisible(true);
    }

    public void initComponents() {
        
        JPanel cp = (JPanel)getContentPane();
        cp.setLayout(new FlowLayout());
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
        setPreferredSize(new Dimension(800, 600));
        setTitle("Menu d'accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // centrage de la fenêtre à l'écran
    }
    
    class DessinGraphe extends LCGraphe {
        private double x ;
        private double y;
        
        private MaillonGraphe premier;
        private MaillonGraphe suiv;
        DessinGraphe(double axeX, double axeY){
            super();
            this.x = axeX;
            this.y = axeY;
        }

       

       

        
    }

}
