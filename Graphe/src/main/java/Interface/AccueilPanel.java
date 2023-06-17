package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilPanel extends JPanel {
    private JButton commencer, quitter;
    private InterfaceGraphe interfaceGraphe;
    
    public AccueilPanel(InterfaceGraphe interfaceGraphe) {
        this.interfaceGraphe = interfaceGraphe;
        setLayout(new BorderLayout());
        initAccueilComponents();
        initAccueilEventListeners();
        setVisible(true);
    }
    
    private void initAccueilComponents() {
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Bienvenue !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panelTop.add(titleLabel);
        
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        commencer = new JButton("Ouvrir Fichier");
        quitter = new JButton("Quitter");
        
        centerPanel.add(commencer);
        centerPanel.add(quitter);
        
        add(panelTop, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        JLabel imageLabel = new JLabel();
     
        
      //  imageLabel.setIcon(ImageAccueil.MEDECIN.getImageIcon());
        //add(imageLabel, BorderLayout.SOUTH);
        //System.out.println("Largeur de l'image : " + ImageAccueil.MEDECIN.getImageIcon().getIconWidth());
        //System.out.println("Hauteur de l'image : " + ImageAccueil.MEDECIN.getImageIcon().getIconHeight());
        
    }
    
    private void initAccueilEventListeners() {
        commencer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceGraphe.setOuvrirFichierActions();
            }
        });
        
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceGraphe.dispose();
            }
        });
    }
}
