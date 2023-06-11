package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilPanel extends JPanel {
    private JButton commencer, quitter;
    private InterfaceGraphe interfaceGraphe;
    private JPanel panelTop;
    AccueilPanel(InterfaceGraphe interfaceGraphe){
        this.interfaceGraphe = interfaceGraphe;
        setLayout(new BorderLayout());
        initAccueilComponents();
        initAccueilEventListeners();
        setVisible(true);
    }


    private void initAccueilComponents(){
        panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Bienvenue !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTop.add(titleLabel);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        commencer = new JButton("Ouvrir Fichier");
        quitter = new JButton("Quitter");

        commencer.setHorizontalAlignment(JButton.CENTER);
        quitter.setHorizontalAlignment(JButton.CENTER);

        centerPanel.add(commencer);
        centerPanel.add(quitter);
        add(panelTop, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
    private void initAccueilEventListeners(){
        commencer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfaceGraphe.setOuvrirFichierActions();


                interfaceGraphe.setContentPane(interfaceGraphe.getCp());
                interfaceGraphe.setJMenuBar(interfaceGraphe.getMenu());
                interfaceGraphe.pack();
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
