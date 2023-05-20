import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.*;

public class InterfaceGraphe extends JFrame {

    private static LCGraphe Graphe = new LCGraphe();
    private JButton btnRetour;
    private LinkedList<JLabel> labels = new LinkedList<>();
    private JMenuBar menu;
    private JMenu j;
    private JMenuItem option1, option2, option3;
    private JPanel cp;
    private String nomFichier;

    public InterfaceGraphe() {

        super();

        initComponents();
        setTitle("Graphe");
        setSize(1280, 720);
        setPreferredSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    private void initComponents() {

        cp = (JPanel) getContentPane();
        JPanel panelBoutons = new JPanel();
        this.btnRetour = new JButton("Retour Menu Principal");
        panelBoutons.add(btnRetour);

        menu = new JMenuBar();
        j = new JMenu("Menu");
        option1 = new JMenuItem("Choisir le fichier pour le graphe");
        option2 = new JMenuItem("Sélectionner un dispensaire");
        option3 = new JMenuItem("Modifier le Graphe");
        j.add(option1);
        j.add(option2);
        j.add(option3);
        for (JLabel label : labels) {
            JMenuItem menuItem = new JMenuItem(label.getText());
            j.add(menuItem);
        }
        menu.add(j);

        cp.add(menu, BorderLayout.NORTH);
        cp.add(panelBoutons, BorderLayout.PAGE_END);

        initEventListeners();
        pack();
    }

    public String getNomFichier() {
        return this.nomFichier;
    }

    public void initEventListeners() {

        option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fenetreOuvertureFichier = new JFileChooser(new File("."));
                File fichier;
                if (fenetreOuvertureFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fichier = fenetreOuvertureFichier.getSelectedFile();
                    System.out.println(fichier.getPath());

                    Graphe.chargementFichier(fichier.getPath());
                    DessinGraphe dessinGraphe = new DessinGraphe(Graphe);
                    cp.add(dessinGraphe, BorderLayout.CENTER);
                    cp.revalidate();
                }
            }
        });

        option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel p = new JPanel();

                JLabel j = new JLabel("Modification du graphe :");
                p.add(j);
                JTable jt = new JTable(5, 6);
                jt.setBounds(30, 40, 200, 300);
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.add(jt);
                cp.add(p);
            }
        });

        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.repaint();
            }
        });
    }

    public class DessinGraphe extends JPanel {

        private LCGraphe graphe;
        private HashMap<LCGraphe.MaillonGraphe, JLabel> sommets;
        private JLabel labelEnDeplacement;
        private int xPos, yPos;

        DessinGraphe(LCGraphe graphe) {
            super();
            this.graphe = graphe;
            sommets = new HashMap<LCGraphe.MaillonGraphe, JLabel>();
            setPreferredSize(new Dimension(1280, 720));
            initEventListeners();
        }

        private void initEventListeners() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    Object source = e.getSource();
                    if (source instanceof JLabel) {
                        JLabel label = (JLabel) source;
                        if (sommets.containsValue(label)) {
                            labelEnDeplacement = label;
                            xPos = e.getX() - label.getX();
                            yPos = e.getY() - label.getY();
                        }
                    }
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (labelEnDeplacement != null) {
                        int x = e.getX();
                        int y = e.getY();
                        labelEnDeplacement.setLocation(x - xPos, y - yPos);
                        int rayon = 45;

                        cp.repaint();

                    }
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    labelEnDeplacement = null;
                }
            });
        }

        private JLabel getLabelEnCoordonnees(int x, int y) {
            for (LCGraphe.MaillonGraphe sommet : sommets.keySet()) {
                JLabel label = sommets.get(sommet);
                if (x >= label.getX() && x <= label.getX() + label.getWidth()
                        && y >= label.getY() && y <= label.getY() + label.getHeight()) {
                    return label;
                }
            }
            return null;
        }

        private void dessinerSommet(Graphics2D g2d, LCGraphe.MaillonGraphe sommet) {
            int rayon = 45;
            JLabel label = sommets.get(sommet);
            if (label == null) {
                label = new JLabel(sommet.getNom());
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                sommets.put(sommet, label);
                cp.add(label);
            }
            labelEnDeplacement = new JLabel();
            //label.setBounds(label.getX() - rayon / 2, label.getY() - rayon / 2, rayon, rayon);
            g2d.setColor(Color.RED);
            g2d.fillOval(label.getX() - rayon / 2, label.getY() - rayon / 2, rayon, rayon);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(label.getX() - rayon / 2, label.getY() - rayon / 2, rayon, rayon);

          /*  g2d.setColor(Color.RED);
            g2d.fillOval(labelEnDeplacement.getX() - rayon / 2, labelEnDeplacement.getY() - rayon / 2, rayon, rayon);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(labelEnDeplacement.getX() - rayon / 2, labelEnDeplacement.getY() - rayon / 2, rayon, rayon);*/
            cp.repaint();

        }

        private void dessinerAretes(Graphics2D g2d) {
            g2d.setColor(Color.BLACK);
            for (LCGraphe.MaillonGraphe sommet1 : sommets.keySet()) {
                JLabel p1 = sommets.get(sommet1);
                for (LCGraphe.MaillonGraphe sommet2 : sommets.keySet()) {
                    JLabel p2 = sommets.get(sommet2);
                    if (sommet1.estVoisin(sommet2.getNom())) {
                        g2d.fillOval(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2, 10, 10);
                        g2d.fillOval(p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2, 10, 10);
                        g2d.setStroke(new BasicStroke(3));

                        g2d.drawLine(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2, p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2);
                    }
                }
            }
        }

        private void dessinerGraphe(Graphics2D g2d) {
            int tailleCadre = (int) (Math.sqrt(30) * 100);
            int xCentre = getWidth() / 2;
            int yCentre = getHeight() / 2;
            int i = 1;
            LCGraphe.MaillonGraphe sommet = graphe.getPremier();
            while (sommet != null) {
                double angle = 2 * Math.PI * i / 30;
                int x = xCentre + (int) (tailleCadre / 2 * Math.cos(angle));
                int y = yCentre + (int) (tailleCadre / 2 * Math.sin(angle));
                JLabel l = new JLabel("");
                l.setLocation(x, y);
                sommets.put(sommet, l);
                dessinerSommet(g2d, sommet);
                sommet = sommet.getSuivant();
                i++;
            }
            dessinerAretes(g2d);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            if (labelEnDeplacement != null) {
                int x = labelEnDeplacement.getX() + xPos;
                int y = labelEnDeplacement.getY() + yPos;

                int rayon = 45;
                g2d.setColor(Color.RED);
                g2d.fillOval(x - rayon / 2, y - rayon / 2, rayon, rayon);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x - rayon / 2, y - rayon / 2, rayon, rayon);
            }
            dessinerGraphe(g2d);
        }
    }
}