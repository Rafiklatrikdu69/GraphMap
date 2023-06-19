package Interface.JOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmerAjoutDansCSV extends JPanel {
    private JButton yesButton, noButton;
    private JLabel question;
    private JPanel panelButtons;
    private boolean confirmer;

    public ConfirmerAjoutDansCSV(){
        setLayout(new GridLayout(2,1));
        initComponents();
        initEventsListener();
    }
    private void initComponents(){
        yesButton = new JButton("Oui");
        noButton = new JButton("Non");

        question = new JLabel("Voulez vous ajouter ce sommet dans le fichier ?");

        panelButtons = new JPanel(new GridLayout(1,2));
        panelButtons.add(yesButton);
        panelButtons.add(noButton);

        add(question);
        add(panelButtons);
    }
    private void initEventsListener(){
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmer = true;
                JOptionPane.getRootFrame().dispose();
            }
        });
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmer = false;
                JOptionPane.getRootFrame().dispose();
            }
        });
    }

    public boolean isConfirmer() {
        return confirmer;
    }
}
