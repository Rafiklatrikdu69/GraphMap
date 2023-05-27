import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilInterface extends JFrame {
    private JButton commencer, parametre, quitter;

    /**
     * @param nom
     */
    AccueilInterface(String nom) {
        initComponents();
        setTitle(nom);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     *
     */

    private void initComponents() {
        JPanel cp = (JPanel) getContentPane();
        cp.setLayout(new BorderLayout());

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

        cp.add(centerPanel, BorderLayout.CENTER);

        initEventListeners();
    }

    /**
     *
     */

    private void initEventListeners() {
        commencer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!InterfaceGraphe.fenetreDejaOuverte) {
                    InterfaceGraphe interfaceGraphe = new InterfaceGraphe();
                    InterfaceGraphe.fenetreDejaOuverte = true;
                }

            }
        });
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}