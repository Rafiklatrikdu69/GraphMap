import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

public class DessinGraphe extends JPanel {

    private Map<LCGraphe.MaillonGraphe, JLabel> sommets;
    private LCGraphe.MaillonGraphe sommetSelectionne;

    private JLabel sommetEnDeplacement;
    private int xPos, yPos;

    private JPanel panelInfoSommet;

    /**
     * Constructeur de la classe DessinGraphe
     */
    DessinGraphe() {
        super();
        setLayout(null);
        sommets = new HashMap<>();


        panelInfoSommet = new JPanel();
        LCGraphe.MaillonGraphe tmp = grapheConstant.Graphe.getPremier();
        int LargeurPanel = getWidth() + 1250 / 2;//largeur de la panel

        int hauteurPanel = getHeight() + 700 / 2;
        int tailleCadre = (int) (Math.sqrt(30) * 30);

        int i = 1;

        while (tmp != null) {
            double angle = 2 * Math.PI * i / 30;
            int x = LargeurPanel + (int) (tailleCadre * Math.cos(angle));
            int y = hauteurPanel + (int) (tailleCadre * Math.sin(angle));
            ajouterSommet(tmp, x, y);
            i++;
            tmp = tmp.getSuivant();
        }
    }

    /**
     * Méthode pour ajouter un sommet avec ses coordonnées
     *
     * @param m Le nom du sommet
     * @param x La coordonnée X du sommet
     * @param y La coordonnée Y du sommet
     */
    private void ajouterSommet(LCGraphe.MaillonGraphe m, int x, int y) {
        JLabel label = new JLabel(m.getNom());
        label.setForeground(Color.WHITE);

        label.setBounds(x, y, 30, 20);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                sommetEnDeplacement = label;
                xPos = e.getXOnScreen();
                yPos = e.getYOnScreen();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sommetSelectionne = m;
                panelInfoSommet.setLayout(new BoxLayout(panelInfoSommet, BoxLayout.Y_AXIS));
                panelInfoSommet.removeAll();
                //panelInfoVoisin.removeAll();

                JLabel nom = new JLabel("Nom du sommet : " + sommetSelectionne.getNom());
                JLabel type = new JLabel("Type : " + sommetSelectionne.getType());
                System.out.println("Le nom du dispensaire " + sommetSelectionne.getNom() + " Son type : " + sommetSelectionne.getType());
                JLabel voisin = new JLabel("Les voisins de ce sommet : ");
                //panelInfoVoisin.add(voisin);
                panelInfoSommet.add(nom);
                panelInfoSommet.add(type);
                JLabel infoSommet = new JLabel("Info des sommets voisins:");
                panelInfoSommet.add(infoSommet);

                for (LCGraphe.MaillonGraphe sommet : sommets.keySet()) {
                    if (sommetSelectionne.estVoisin(sommet.getNom())) {
                        JLabel sommetVoisin = new JLabel("Nom : " + sommet.getNom() + " Type : " + sommet.getType());
                        panelInfoSommet.add(sommetVoisin); // Ajout du JLabel pour le voisin

                        LCGraphe.MaillonGrapheSec sommetVoisi = sommet.lVois;
                        while (sommetVoisi != null) {
                            if(sommetVoisi.equals(sommet)) {
                                String infoText = "Distance: " + sommetVoisi.getDistance() + " Durée: " + sommetVoisi.getDuree() + " Fiabilité: " + sommetVoisi.getFiabilite();
                                JLabel info = new JLabel(infoText);
                                panelInfoSommet.add(info);
                            }
                            sommetVoisi = sommetVoisi.suiv;
                        }
                        
                    }
                }





                panelInfoSommet.setBorder(BorderFactory.createTitledBorder("info"));


                add(panelInfoSommet);
                panelInfoSommet.setBounds(10, 20, 300, 700);

                panelInfoSommet.validate();
                panelInfoSommet.repaint();
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
                if (!InterfaceGraphe.bloquerGraphe.isSelected()) {
                    if (sommetEnDeplacement != null) {
                        int x = e.getXOnScreen() - xPos + sommetEnDeplacement.getX();
                        int y = e.getYOnScreen() - yPos + sommetEnDeplacement.getY();
                        int LargeurPanel = getWidth();
                        int hauteurPanel = getHeight();
                        int labelLargeur = sommetEnDeplacement.getWidth();
                        int labelhauteur = sommetEnDeplacement.getHeight();
                        int infoSommetWidth = panelInfoSommet.getWidth();


                        if (x < infoSommetWidth) {
                            x = infoSommetWidth;
                        } else if (x > LargeurPanel - labelLargeur) {
                            x = LargeurPanel - labelLargeur;
                        }

                        if (y < 0) {
                            y = 0;
                        } else if (y > hauteurPanel - labelhauteur) {
                            y = hauteurPanel - labelhauteur;
                        }


                        sommetEnDeplacement.setLocation(x, y);

                        xPos = e.getXOnScreen();
                        yPos = e.getYOnScreen();
                    }
                } else {
                    // ...
                }

                revalidate();
                repaint();
            }
        });

        sommets.put(m, label);
        add(label);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int cptArrete = 0;
        for (LCGraphe.MaillonGraphe sommet1 : sommets.keySet()) {
            JLabel p1 = sommets.get(sommet1);
            int x1 = p1.getX() + p1.getWidth() / 2;
            int y1 = p1.getY() + p1.getHeight() / 2;
            int radius = p1.getWidth() / 2;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fill(new Ellipse2D.Double(x1 - radius, y1 - radius, radius * 2, radius * 2));

            for (LCGraphe.MaillonGraphe sommet2 : sommets.keySet()) {
                JLabel p2 = sommets.get(sommet2);
                int x2 = p2.getX() + p2.getWidth() / 2;
                int y2 = p2.getY() + p2.getHeight() / 2;
                if (sommetSelectionne == sommet2) {
                    g2d.setColor(Color.RED);
                    g2d.fill(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
                } else {
                    g2d.setColor(Color.BLACK);
                    g2d.fill(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
                }
                if (sommet1.estVoisin(sommet2.getNom())) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(x1, y1, x2, y2);
                    cptArrete++;
                }
            }
        }

        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 500);
    }
}
