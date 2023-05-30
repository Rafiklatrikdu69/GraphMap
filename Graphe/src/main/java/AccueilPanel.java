import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilPanel extends JPanel {
    private JButton commencer, parametre, quitter;
    private JFrame fenetrePrincipale;
    AccueilPanel(JFrame fenetrePrincipale){
        this.fenetrePrincipale = fenetrePrincipale;
        initAccueilComponents();
        initAccueilEventListeners();
        setVisible(true);
    }


    private void initAccueilComponents(){
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Bienvenue !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);

        commencer = new JButton("Commencer");
        parametre = new JButton("Paramètres");
        quitter = new JButton("Quitter");

        commencer.setAlignmentX(Component.CENTER_ALIGNMENT);
        parametre.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitter.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(commencer);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(parametre);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(quitter);

        add(centerPanel, BorderLayout.CENTER);
    }
    private void initAccueilEventListeners(){
        commencer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                fenetrePrincipale.setContentPane(InterfaceGraphe.cp);
                fenetrePrincipale.setJMenuBar(InterfaceGraphe.menu);
            }
        });
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetrePrincipale.dispose();
            }
        });
    }
}
