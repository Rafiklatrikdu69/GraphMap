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
    private JMenuItem option1,option2,option3;
    private JPanel panel, cp;

    public InterfaceGraphe() {
        super();
        Graphe.chargementFichier();
        initComponents();
        initEventListeners();
        setTitle("Graphe");
        setSize(1280,720);
        setPreferredSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    private void initComponents() {
        cp = (JPanel) getContentPane();
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
    }
    private void initEventListeners(){

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
