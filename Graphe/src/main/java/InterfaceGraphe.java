import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class InterfaceGraphe extends JFrame {
    private Map<LCGraphe.MaillonGraphe, JLabel> sommets;
    private JButton hamburgerButton, option1Button, option2Button, option3Button;
    private JPanel cp, menuPanel, mainPanel,graphe;

    private DessinGraphe dessinGraphe;
    private boolean menuVisible = false;
    private JRadioButton bloquerGraphe;
    private JProgressBar barreChargement;
    private int progresse;
    private Timer timer;

    private static LCGraphe Graphe;

    private LinkedList<JLabel> labels = new LinkedList<>();
    private JMenuBar menu;
    private JMenu fichier, fenetre, fonctionnalites;
    private JMenuItem option1, option2, option3;
    private FileF<String> f;

    private String nomFichier;

    public InterfaceGraphe() {
        super();
        Graphe = new LCGraphe();
        f = LCGraphe.getFile();

        initComponents();
        setTitle("Graphe");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public LCGraphe getGraphe() {
        return this.Graphe;
    }

    private void initComponents() {
        cp = (JPanel) getContentPane();
        JPanel panelBoutons = new JPanel();


        cp.setLayout(new BorderLayout());
        cp.add(panelBoutons, BorderLayout.SOUTH);



        menu = new JMenuBar();
        fichier = new JMenu("Fichier");
        fenetre = new JMenu("Fenetre");
        fonctionnalites = new JMenu("Mode du Graphe");

        // Menu
        option1 = new JMenuItem("Ouvrir");
        option2 = new JMenuItem("Fermer");
        option3 = new JMenuItem("Mode ");
        bloquerGraphe = new JRadioButton("Bloquer");
        fonctionnalites.add(bloquerGraphe);
        menu.add(fonctionnalites);


        fichier.add(option1);
        fenetre.add(option2);
        fonctionnalites.add(option3);
        menu.add(fichier);
        menu.add(fenetre);
        menu.add(fonctionnalites);


        cp.add(menu, BorderLayout.NORTH);

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                progresse++;
                barreChargement.setValue(progresse);
                if (progresse == 100) {
                    System.out.println("100");
                    timer.stop();
                    cp.add(graphe, BorderLayout.EAST);
                    cp.revalidate();


                }
            }
        });

        slide();

        initEventListeners();
    }

    private void visible() {
        if (!menuVisible) {
            mainPanel.add(menuPanel, BorderLayout.WEST);
            menuVisible = true;
        } else {
            mainPanel.remove(menuPanel);
            menuVisible = false;
        }
        revalidate();
        repaint();
    }

    public void slide() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        menuPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Menu"));

        menuPanel.setPreferredSize(new Dimension(100, 100));

        option1Button = new JButton("Option 1");
        option2Button = new JButton("Option 2");
        option3Button = new JButton("Option 3");


        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(1, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        menuPanel.add(option1Button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        menuPanel.add(option2Button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        menuPanel.add(option3Button, gbc);

        hamburgerButton = new JButton("\u2630");

        hamburgerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visible();
            }
        });

        mainPanel.add(hamburgerButton, BorderLayout.WEST);
        cp.add(mainPanel, BorderLayout.WEST);

        setVisible(true);
    }

    public void demarrerChargement() {
        progresse = 0;
        timer.start();
    }

    public void initEventListeners() {

        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("test");
                JFileChooser fenetreOuvertureFichier = new JFileChooser(new File("."));
                File fichier;
                if (fenetreOuvertureFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // Si un fichier est sélectionné dans la fenêtre ouverte grâce à fenetreOuvertureFichier.showOpenDialog(null)
                    fichier = fenetreOuvertureFichier.getSelectedFile(); // Récupérer le fichier
                    System.out.println(fichier.getPath());
                    ChargementGraphe.Graphe.chargementFichier(fichier.getPath());
                    nomFichier = fichier.getPath();


                    barreChargement = new JProgressBar(0, 100);
                    barreChargement.setStringPainted(true);
                     graphe = new DessinGraphe();

                    graphe.setBorder(BorderFactory.createTitledBorder("Graphe"));


                    graphe.setPreferredSize(new Dimension(500,100));

                    JPanel barre = new JPanel();
                    barre.add(barreChargement);
                    cp.add(barre, BorderLayout.SOUTH);
                    barre.setPreferredSize(new Dimension(300, 100));
                    barre.setBorder(BorderFactory.createTitledBorder("Barre de chargement "));
                    demarrerChargement();


                }


            }
        });
        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });
        option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AfficherCheminGraphe cheminGraphe = new AfficherCheminGraphe("Chemins Graphes", InterfaceGraphe.this);
            }
        });


    }

    /**
     * @return
     */
    public String getNomFichier() {
        return this.nomFichier;
    }





}



