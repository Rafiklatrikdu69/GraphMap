import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class InterfaceGraphe extends JFrame {

    private JButton btnRetour;
    private JPanel cp;
    private DessinGraphe dessinGraphe;

    public InterfaceGraphe() {
        super();
        initComponents();
        setTitle("Graphe");
        setSize(1980, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        cp = (JPanel) getContentPane();
        JPanel panelBoutons = new JPanel();
        this.btnRetour = new JButton("Retour Menu Principal");
        panelBoutons.add(btnRetour);

        cp.setLayout(new BorderLayout());
        cp.add(panelBoutons, BorderLayout.SOUTH);
        dessinGraphe = new DessinGraphe();


        initEventListeners();
        cp.add(dessinGraphe, BorderLayout.CENTER);
    }

    public void initEventListeners() {
        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cp.repaint();
            }
        });
    }

    public class DessinGraphe extends JPanel {

        private Map<LCGraphe.MaillonGraphe, JLabel> sommets;
        private JLabel sommetEnDeplacement;
        private int xPos, yPos;


        DessinGraphe() {
            super();

            setLayout(null);
            sommets = new HashMap<>();

            LCGraphe graphe = new LCGraphe();

            graphe.chargementFichier("C:\\Users\\Rafik\\Documents\\SAE\\sae_java_outil_aide_a_la_decision\\Graphe\\src\\fichiersGraphe\\liste-adjacence-jeuEssai.csv");
            LCGraphe.MaillonGraphe tmp = graphe.getPremier();

            int tailleCadre = (int) (Math.sqrt(30) * 100);
            int xCentre = 1400 / 2;
            int yCentre = 600 / 2;
            int i = 1;
            while (tmp != null) {

                double angle = 2 * Math.PI * i / 30;
                int x = xCentre + (int) (tailleCadre / 2 * Math.cos(angle));
                int y = yCentre + (int) (tailleCadre / 2 * Math.sin(angle));

                ajouterSommet(tmp, x, y);
               //System.out.println("test");
                tmp = tmp.getSuivant();
                i++;
            }
            int preferredWidth = tailleCadre + 100;
            int preferredHeight = tailleCadre + 100;
            setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        }

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

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);


            for (LCGraphe.MaillonGraphe sommet1 : sommets.keySet()) {
                JLabel p1 = sommets.get(sommet1);
                for (LCGraphe.MaillonGraphe sommet2 : sommets.keySet()) {
                    JLabel p2 = sommets.get(sommet2);
                    if (sommet1.estVoisin(sommet2.getNom())) {
                        g2d.fillOval(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2, 10, 10);
                        g2d.fillOval(p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2, 10, 10);
                        g2d.setStroke(new BasicStroke(1));

                        g2d.drawLine(p1.getX() + p1.getWidth() / 2, p1.getY() + p1.getHeight() / 2, p2.getX() + p2.getWidth() / 2, p2.getY() + p2.getHeight() / 2);
                    }
                }
            }


        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceGraphe();
            }
        });
    }
}
