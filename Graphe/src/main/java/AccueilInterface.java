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

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Bienvenue !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(titleLabel, gbc);

        commencer = new JButton("Commencer");
        parametre = new JButton("Paramètres");
        quitter = new JButton("Quitter");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(commencer, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(parametre, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(quitter, gbc);
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

                InterfaceGraphe interfaceGraphe = new InterfaceGraphe();
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