import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class InterfaceGraphe extends JFrame {

    private JButton btnRetour;
    private JPanel cp;
    private DessinGraphe dessinGraphe;

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

    /**
     *
     */
    private void initComponents() {
        cp = (JPanel) getContentPane();
        JPanel panelBoutons = new JPanel();
        this.btnRetour = new JButton("Retour Menu Principal");
        panelBoutons.add(btnRetour);

        cp.setLayout(new BorderLayout());
        cp.add(panelBoutons, BorderLayout.SOUTH);
        dessinGraphe = new DessinGraphe();
        menu = new JMenuBar();
        fichier = new JMenu("Fichier");
        fenetre = new JMenu("Fenetre");
        fonctionnalites = new JMenu("Fonctionnalités");


        //Menu
        option1 = new JMenuItem("Ouvrir");
        option2 = new JMenuItem("Fermer");
        option3 = new JMenuItem("Afficher le plus courts chemins");
        fichier.add(option1);
        fenetre.add(option2);
        fonctionnalites.add(option3);
        menu.add(fichier);
        menu.add(fenetre);
        menu.add(fonctionnalites);

        cp.add(menu, BorderLayout.NORTH);

        initEventListeners();


    }

    public void initEventListeners() {
        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
                AccueilInterface a = new AccueilInterface("Graphe");
            }
        });
        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("test");
                JFileChooser fenetreOuvertureFichier = new JFileChooser(new File("."));
                File fichier;
                if (fenetreOuvertureFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { // Si un fichier est sélectionné dans la fenêtre ouverte grâce à fenetreOuvertureFichier.showOpenDialog(null)
                    fichier = fenetreOuvertureFichier.getSelectedFile(); // Récupérer le fichier
                    System.out.println(fichier.getPath());
                    Graphe.chargementFichier(fichier.getPath());
                    nomFichier  = fichier.getPath();
                    Graphe();



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
     *
     * @return
     */
    public String getNomFichier(){
        return this.nomFichier;
    }

    public void Graphe() {

        LCGraphe.MaillonGraphe tmp = Graphe.getPremier();

        int tailleCadre = (int) (Math.sqrt(30) * 100);
        int xCentre = 1400 / 2;
        int yCentre = 600 / 2;
        int i = 1;
        while (tmp != null) {

            double angle = 2 * Math.PI * i / 30;
            int x = xCentre + (int) (tailleCadre / 2 * Math.cos(angle));
            int y = yCentre + (int) (tailleCadre / 2 * Math.sin(angle));

            dessinGraphe.ajouterSommet(tmp, x, y);
            //System.out.println("test");
            tmp = tmp.getSuivant();
            i++;
        }
        int preferredWidth = tailleCadre + 100;
        int preferredHeight = tailleCadre + 100;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        cp.add(dessinGraphe, BorderLayout.CENTER);
        cp.revalidate();
    }

    public class DessinGraphe extends JPanel {

        private Map<LCGraphe.MaillonGraphe, JLabel> sommets;
        private JLabel sommetEnDeplacement;
        private int xPos, yPos;

        /**
         * 
         */
        DessinGraphe() {
            super();

            setLayout(null);
            sommets = new HashMap<>();


        }

        /**
         * 
         * @param m
         * @param x
         * @param y
         */
        private void ajouterSommet(LCGraphe.MaillonGraphe m, int x, int y) {
            JLabel label = new JLabel(m.getNom());

            label.setBounds(x, y, 50, 30);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    sommetEnDeplacement = label;
                    xPos = e.getX();
                    yPos = e.getY();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    sommetEnDeplacement = null;
                }
            });

            label.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                    if (sommetEnDeplacement != null) {
                        int x = e.getXOnScreen() - xPos;
                        int y = e.getYOnScreen() - yPos;
                        sommetEnDeplacement.setLocation(x, y);

                        repaint();
                    }
                }
            });

            sommets.put(m, label);
            add(label);
        }

        /**
         * @param g the <code>Graphics</code> object to protect
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            //double[][] predecesseurs = Graphe.floydWarshall("S1", "S8");

            // while (!f.estVide()) {
            //   String def = f.defiler();

            for (LCGraphe.MaillonGraphe sommet1 : sommets.keySet()) {
                JLabel p1 = sommets.get(sommet1);

                for (LCGraphe.MaillonGraphe sommet2 : sommets.keySet()) {
                    JLabel p2 = sommets.get(sommet2);

                    if (sommet1.estVoisin(sommet2.getNom())/*sommet1.getNom().equals("S1") && sommet2.getNom().equals("S8")*/) {
                        g2d.setColor(Color.BLACK);
                        g2d.setStroke(new BasicStroke(3));
                        g2d.drawLine(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2, p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2);
                    } /*else {
                            g2d.setColor(Color.BLACK);
                            g2d.setStroke(new BasicStroke(1));
                            g2d.drawLine(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2, p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2);
                        }*/
                }
            }
        }


    }
}



