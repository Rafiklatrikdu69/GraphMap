import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static javax.swing.JOptionPane.showOptionDialog;

public class InterfaceGraphe extends JFrame {
    private JFrame fenetrePrincipale; // la fenetre
    
    public static JPanel cp;
    private JPanel graphePanel;
    private AccueilPanel accueilPanel;
    private JPanel barreDeChargementPanel;
    
    public static JPanel contenuInfoSommetPanel;
    
    public static JMenuBar menu;
    private JMenu itemFichier, itemFenetre, itemFonctionnalites;
    private JMenuItem itemOuvrirFichier, itemFermerFenetre, itemMenuPrincipale;
    static JRadioButton bloquerGraphe;
    private JProgressBar barreChargement;
    private int progresse;
    private Timer timer;
    
    private Dimension screenSize;
    
    private File fichierCharge;
    public static LCGraphe Graphe;
    private Integer nombreRoutes;
    private Integer nombreCentre;
    static boolean cheminValide = false;

    private JPanel contenuGraphePanel;

    public InterfaceGraphe() {
        super();
        Graphe = new LCGraphe();
        fenetrePrincipale = this;
        accueilPanel = new AccueilPanel(fenetrePrincipale);
        setContentPane(accueilPanel);
        
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setSize(new Dimension(screenWidth/2 , screenHeight/2+200 ));
        
        initComponents();
        
        setTitle("Graphe");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void initContainerDessinGraphePanel() {
        graphePanel = new DessinGraphe();
        graphePanel.setBorder(BorderFactory.createTitledBorder("Graphe"));
        contenuGraphePanel.add(graphePanel, BorderLayout.CENTER);
    }
    
    private void initComposantsInfoSommetPanel() {
        contenuInfoSommetPanel = new JPanel();
        contenuInfoSommetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        contenuInfoSommetPanel.setBorder(BorderFactory.createTitledBorder("Info"));
        contenuInfoSommetPanel.setPreferredSize(new Dimension((int) screenSize.getWidth(), 45));
        cp.add(contenuInfoSommetPanel, BorderLayout.NORTH);
    }
    
    private void initComposantsBarreDeChargement() {
        barreDeChargementPanel = new JPanel();
        barreDeChargementPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        barreDeChargementPanel.setPreferredSize(new Dimension((int) screenSize.getWidth(), 45));
        barreDeChargementPanel.setBorder(BorderFactory.createTitledBorder("Barre de chargement"));
        cp.add(barreDeChargementPanel, BorderLayout.SOUTH);
    }
    
    
    private void initComponents() {
        cp = new JPanel();
        cp.setLayout(new BorderLayout());
        
        menu = new JMenuBar();
        itemFichier = new JMenu("Fichier");
        itemFenetre = new JMenu("Fenetre");
        itemFonctionnalites = new JMenu("Mode du Graphe");
        
        // Menu
        itemOuvrirFichier = new JMenuItem("Ouvrir");
        itemFermerFenetre = new JMenuItem("Fermer");
        itemMenuPrincipale = new JMenuItem("Menu Principale");
        
        bloquerGraphe = new JRadioButton("Bloquer");
        itemFonctionnalites.add(bloquerGraphe);
        menu.add(itemFonctionnalites);
        
        itemFichier.add(itemOuvrirFichier);
        itemFichier.setFont(new Font("Arial", Font.BOLD, 14));
        itemFonctionnalites.setFont(new Font("Arial", Font.BOLD, 14));
        itemFenetre.setFont(new Font("Arial", Font.BOLD, 14));
        menu.setBackground(Color.GRAY);
        itemFenetre.add(itemFermerFenetre);
        itemFenetre.add(itemMenuPrincipale);
        
        menu.add(itemFichier);
        menu.add(itemFenetre);
        menu.add(itemFonctionnalites);

        contenuGraphePanel = new JPanel();
        contenuGraphePanel.setLayout(new BorderLayout());
        cp.add(contenuGraphePanel, BorderLayout.CENTER);
        initComposantsBarreDeChargement();
        initComposantsInfoSommetPanel();
        initEventListeners();
    }
    
    public void demarrerChargement() {
        progresse = 0;
        timer.start();
    }
    
    public void initEventListeners() {
        itemOuvrirFichier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fenetreOuvertureFichier = new JFileChooser(new File("."));
                File fichier;
                if (fenetreOuvertureFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fichier = fenetreOuvertureFichier.getSelectedFile();
                    System.out.println(fichier.getPath());
                    if (fichier.getPath().endsWith(".csv")) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        barreDeChargementPanel.removeAll();
                        barreChargement = new JProgressBar(0, 100);
                        barreDeChargementPanel.add(barreChargement);
                        barreChargement.setStringPainted(true);
                        
                        timer = new Timer(30, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                progresse++;
                                barreChargement.setValue(progresse);
                                
                                if (progresse == 100) {
                                    timer.stop();

                                    Graphe = new LCGraphe();
                                    contenuGraphePanel.removeAll();
                                    System.out.println(Graphe.existeCentre("S1"));
                                    chargerNouveauFichier(fichier);
                                    initContainerDessinGraphePanel();

                                    
                                    barreChargement.setVisible(false);
                                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                                }
                            }
                        });
                        
                        // Appel de demarrerChargement()
                        demarrerChargement();
                    } else {
                        System.out.println("Format de fichier interdit !");
                        
                        JPanel panelCorrompu = new JPanel();
                        int result = showOptionDialog(null, panelCorrompu, "fichier corrompu ! ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                        
                    }
                }
            }
        });
        
        itemFermerFenetre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        itemMenuPrincipale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetrePrincipale.setContentPane(accueilPanel);
            }
        });
    }
    
    @Override
    public void setEnabled(boolean b) {
        if (b) {
            setBackground(Color.BLACK);
        } else {
            setBackground(Color.GRAY);
        }
        super.setEnabled(b);
    }
    
    private void chargerNouveauFichier(File file) {
        Graphe.chargementFichier(file.getPath());
    }
}
