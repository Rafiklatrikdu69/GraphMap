import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AfficherCheminGraphe extends JFrame {
    private InterfaceGraphe interfaceGraphe;
    private JComboBox<String> comboBoxPremier, comboBoxDeuxieme;
    private JLabel sommets;
    private String choixSommet1, choixSommet2;
    private JButton lancerRecherche;

    /**
     *
     * @param nom
     * @param interfaceGraphe
     */
    AfficherCheminGraphe(String nom, InterfaceGraphe interfaceGraphe) {
        this.interfaceGraphe = interfaceGraphe;
        initComponents();
        setTitle(nom);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chargementGraphe.Graphe.chargementFichier(interfaceGraphe.getNomFichier());
        setVisible(true);
    }

    /**
     *
     */
    private void initComponents() {
        JPanel cp = (JPanel) getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));

        comboBoxPremier = new JComboBox<>();
        JPanel combo = new JPanel();
        for (int i = 1; i <= 30; i++) {
            sommets = new JLabel("S" + i);
            comboBoxPremier.addItem("S" + i);
        }

        comboBoxDeuxieme = new JComboBox<>();
        JPanel comboDeux = new JPanel();
        for (int i = 1; i <= 30; i++) {
            sommets = new JLabel("S" + i);
            comboBoxDeuxieme.addItem("S" + i);
        }
        combo.add(comboBoxPremier);
        comboDeux.add(comboBoxDeuxieme);

        cp.add(combo);
        cp.add(comboDeux);
        lancerRecherche = new JButton("Recherche chemin");
        cp.add(lancerRecherche);
        initEventListeners();
    }

    /**
     *
     */

    private void initEventListeners() {
        comboBoxPremier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSommet = (String) comboBoxPremier.getSelectedItem();
                System.out.println(selectedSommet);
                choixSommet1 = selectedSommet;
            }
        });

        comboBoxDeuxieme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSommet = (String) comboBoxDeuxieme.getSelectedItem();
                System.out.println(selectedSommet);
                choixSommet2 = selectedSommet;
                System.out.println(choixSommet2);
            }
        });

        lancerRecherche.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double prec[][] = chargementGraphe.Graphe.floydWarshall("S1", "S3");
                // Utilisez la liste pour votre algorithme ici
            }
        });
    }
}
