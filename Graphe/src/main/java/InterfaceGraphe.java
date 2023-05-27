import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class InterfaceGraphe extends JFrame {
    private Map<LCGraphe.MaillonGraphe, JLabel> sommets;
    static JButton hamburgerButton, option1Button, option2Button, option3Button;
    private JPanel cp, menuPanel, mainPanel, graphe, barre;

    private DessinGraphe dessinGraphe;
    private boolean menuVisible = false;
    static JRadioButton bloquerGraphe;
    private JProgressBar barreChargement;
    private int progresse;
    private Timer timer;


    private boolean occupied;

    private LinkedList<JLabel> labels = new LinkedList<>();
    private JMenuBar menu;
    private JMenu fichier, fenetre, fonctionnalites;
    private JMenuItem option1, option2, option3;
    private File fichierCharge;
    private String nomFichier;
    static boolean fenetreDejaOuverte = false;

    public InterfaceGraphe() {
        super();


        initComponents();

        setTitle("Graphe");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void initComponents() {
        cp = (JPanel) getContentPane();
        cp.setLayout(new BorderLayout());

        menu = new JMenuBar();
        fichier = new JMenu("Fichier");
        fenetre = new JMenu("Fenetre");
        fonctionnalites = new JMenu("Mode du Graphe");

        // Menu
        option1 = new JMenuItem("Ouvrir");
        option2 = new JMenuItem("Fermer");

        bloquerGraphe = new JRadioButton("Bloquer");
        fonctionnalites.add(bloquerGraphe);
        menu.add(fonctionnalites);

        fichier.add(option1);
        fenetre.add(option2);

        menu.add(fichier);
        menu.add(fenetre);
        menu.add(fonctionnalites);

        cp.add(menu, BorderLayout.NORTH);

        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progresse++;
                barreChargement.setValue(progresse);
               /* if (barreChargement != null) {
                    barreChargement.setValue(progresse);
                }*/
                if (progresse == 100) {
                    System.out.println("temps : 100");
                    timer.stop();
                    if (graphe != null) {
                        sommets = new TreeMap<>();
                        dessinGraphe = new DessinGraphe();

                        //algoPlusCourtsChemins.setSommets(sommets);

                        cp.add(dessinGraphe);


                    }

                    barreChargement.setVisible(false);
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
        mainPanel.setPreferredSize(new Dimension(400, 100));

        menuPanel.setPreferredSize(new Dimension(400, 100));

        option1Button = new JButton("Option 1");
        option2Button = new JButton("Rechercher un itineraire");
        option3Button = new JButton("Option 3");

        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

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
                verifeAutorisation();
            }
        });

        mainPanel.add(hamburgerButton, BorderLayout.WEST);
        cp.add(mainPanel, BorderLayout.WEST);

        setVisible(true);
    }

    public void verifeAutorisation() {
        if (!bloquerGraphe.isSelected()) {
            option1Button.setEnabled(false);
            visible();
        }
    }

    public void demarrerChargement() {
        progresse = 0;
        timer.start();
    }

    public void initEventListeners() {
        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fenetreOuvertureFichier = new JFileChooser(new File("."));
                File fichier;
                if (fenetreOuvertureFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fichier = fenetreOuvertureFichier.getSelectedFile();
                    if (fichier.getPath().endsWith(".csv")) {
                        System.out.println(fichier.getPath());
                        chargerNouveauFichier(fichier);
                    }
                    cp.revalidate();
                }
            }
        });

        option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                InterfaceGraphe.fenetreDejaOuverte = false;
            }
        });

        option2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("recherche itineraire");
                AlgoPlusCourtsChemins algoPlusCourtsChemins = new AlgoPlusCourtsChemins();
                remove(dessinGraphe);
                cp.add(algoPlusCourtsChemins, BorderLayout.EAST);
            }
        });
    }

    @Override
    public void setEnabled(boolean b) {
        if (b) {
            setBackground(Color.black);
        } else {
            setBackground(Color.gray);
        }
        super.setEnabled(b);
    }

    public void setSommets(Map<LCGraphe.MaillonGraphe, JLabel> sommets) {
        this.sommets = sommets;
    }

    private void supprimerGraphe() {
        // Supprimer tous les composants liés au graphe
        if (graphe != null) {
            cp.remove(graphe);
            graphe = null;
        }

        // Supprimer la barre de chargement
        if (barreChargement != null) {
            cp.remove(barreChargement);
            barreChargement = null;
        }

        // Repaint et revalider le contenu du conteneur
        cp.revalidate();
        cp.repaint();
    }


    private void fermerFichier() {
        try {
            if (fichierCharge != null) {
                System.out.println(fichierCharge.getPath());
                FileInputStream fileInputStream = new FileInputStream(fichierCharge);
                fileInputStream.close();

            }
            fichierCharge = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void chargerNouveauFichier(File file) {
        //fermerFichier();
        supprimerGraphe();
        if (fichierCharge != null) {
            int option = JOptionPane.showConfirmDialog(null, "Un fichier est déjà ouvert. Voulez-vous le fermer et en ouvrir un nouveau ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                fermerFichier();
            } else {
                return;
            }
        }

        fichierCharge = file;

        if (fichierCharge != null) {
            grapheConstant.Graphe.chargementFichier(fichierCharge.getPath());
        } else {

            return;
        }

        barreChargement = new JProgressBar(0, 100);
        barreChargement.setStringPainted(true);

        graphe = new DessinGraphe();
        graphe.setBorder(BorderFactory.createTitledBorder("Graphe"));
        graphe.setPreferredSize(new Dimension(3000, 200));

        barre = new JPanel();
        barre.add(barreChargement);
        barre.setPreferredSize(new Dimension(300, 100));
        barre.setBorder(BorderFactory.createTitledBorder("Barre de chargement "));
        demarrerChargement();

        cp.add(barre, BorderLayout.SOUTH);


        cp.revalidate();
    }


}
