import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

public class DessinGraphe extends JPanel {
    
    protected Map<LCGraphe.MaillonGraphe, JLabel> sommets;
    private LCGraphe.MaillonGraphe sommetSelectionne;
    
    private JLabel sommetEnDeplacement;
    private int xPos, yPos;
    
    private static final int LABEL_WIDTH = 30;
    private static final int LABEL_HEIGHT = 30;
    protected static final Color DEFAULT_LABEL_COLOR = Color.WHITE;
    protected static final Color SELECTED_LABEL_COLOR = Color.GREEN;
    protected static final Color BACKGROUND_COLOR = Color.BLACK;
    
    /**
     * Constructeur de la classe DessinGraphe
     */
    public DessinGraphe() {
        super();
        sommets = new HashMap<>();
        setLayout(null); // Utiliser un layout null pour positionner les sommets manuellement
        initGraphe();
    }
    
    /**
     * Méthode pour initialiser le graphe avec les sommets
     */
    private void initGraphe() {
      //  InterfaceGraphe.Graphe.chargementFichier("C:\\Users\\Rafik\\Documents\\SAE\\sae_java_outil_aide_a_la_decision\\Graphe\\src\\fichiersGraphe\\liste-adjacence-jeuEssai.csv");
        
        LCGraphe.MaillonGraphe tmp = InterfaceGraphe.Graphe.getPremier();
        
        int largeurPanel = 300; // largeur du panel
        int hauteurPanel = 200; // hauteur du panel
        int tailleCadre = (int) (Math.sqrt(30) * 30);
        int i = 1;
        if (tmp == null){
            System.out.println("probleme !");
        }
        
        
        while (tmp != null) {

            int[] pos = getRandomPositionPourCentre(InterfaceGraphe.contenuGraphePanel);
            
            ajouterSommet(tmp, pos[0], pos[1]);
            i++;
            //System.out.println(tmp.getNom());
            tmp = tmp.getSuivant();
        }
    }
    
    /**
     * Méthode pour ajouter un sommet avec ses coordonnées
     *
     * @param m Le sommet
     * @param x La coordonnée X du sommet
     * @param y La coordonnée Y du sommet
     */
    private void ajouterSommet(LCGraphe.MaillonGraphe m, int x, int y) {
        JLabel label = new JLabel(m.getNom());
      
        label.setForeground(DEFAULT_LABEL_COLOR);
        label.setBounds(x, y, LABEL_WIDTH, LABEL_HEIGHT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                sommetEnDeplacement = label;
                xPos = e.getXOnScreen();
                yPos = e.getYOnScreen();
                repaint();
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                sommetSelectionne = m;
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                sommetEnDeplacement = null;
                repaint();
            }
        });
        
        label.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                super.mouseDragged(e);
                if (!InterfaceGraphe.bloquerGraphe.isSelected() && sommetEnDeplacement != null) {
                    int labelLargeur = sommetEnDeplacement.getWidth();
                    int labelHauteur = sommetEnDeplacement.getHeight();
                    int dx = e.getX() - sommetEnDeplacement.getWidth() / 2;
                    int dy = e.getY() - sommetEnDeplacement.getHeight() / 2;
                    int newPosX = sommetEnDeplacement.getX() + dx;
                    int newPosY = sommetEnDeplacement.getY() + dy;
                    int maxX = getParent().getWidth() - labelLargeur;
                    int maxY = getParent().getHeight() - labelHauteur;
                    newPosX = Math.max(0, Math.min(newPosX, maxX));
                    newPosY = Math.max(0, Math.min(newPosY, maxY));

                    sommetEnDeplacement.setLocation(newPosX, newPosY);

                    xPos = e.getXOnScreen();
                    yPos = e.getYOnScreen();
                }
            }
        });
        
        sommets.put(m, label);
        add(label);
    }
    private void initDessinGraphe(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        
        int radius;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        
        
        JLabel p1;
        JLabel p2;
        int x1, y1, x2, y2;
        //Iterator<Map.Entry<LCGraphe.MaillonGraphe, JLabel>> it = sommets.entrySet().iterator();
        Iterator<Map.Entry<LCGraphe.MaillonGraphe, JLabel>> it = sommets.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<LCGraphe.MaillonGraphe, JLabel> mapSommet = it.next();
            LCGraphe.MaillonGraphe sommet1 = mapSommet.getKey();
            
            p1 = sommets.get(sommet1);
            x1 = p1.getX() + p1.getWidth() / 2;
            y1 = p1.getY() + p1.getHeight() / 2;
            radius = p1.getWidth() / 2;
            
            g2d.fill(new Ellipse2D.Double(x1 - radius, y1 - radius, radius * 2, radius * 2));
            
            Iterator<LCGraphe.MaillonGraphe> iterator = sommets.keySet().iterator();
            while (iterator.hasNext()) {
                LCGraphe.MaillonGraphe sommet2 = iterator.next();
                
                p2 = sommets.get(sommet2);
                x2 = p2.getX() + p2.getWidth() / 2;
                y2 = p2.getY() + p2.getHeight() / 2;
                
                if (sommetSelectionne == sommet2) {
                    g2d.setColor(SELECTED_LABEL_COLOR);
                    g2d.fill(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
                } else {
                    g2d.setColor(BACKGROUND_COLOR);
                    g2d.fill(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
                }
                
                if (sommet1.estVoisin(sommet2.getNom())) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }
        
        
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       initDessinGraphe(g);
    }

    private int[] getRandomPositionPourCentre(JPanel container){
        Random random = new Random();
        int minX = 5;
        int maxX = container.getWidth()-30;
        int randomInRangeX = random.nextInt(maxX - minX + 1) + minX;
        int minY = 15;
        int maxY = container.getHeight()-30;
        int randomInRangeY = random.nextInt(maxY - minY + 1) + minY;
        while(sommetExisteDansZone(randomInRangeX, randomInRangeY)){
            randomInRangeX = random.nextInt(maxX - minX + 1) + minX;
            randomInRangeY = random.nextInt(maxY - minY + 1) + minY;
        }
        return new int[]{randomInRangeX, randomInRangeY};
    }
    private boolean sommetExisteDansZone(int x, int y) {
        for (Map.Entry<LCGraphe.MaillonGraphe, JLabel> entry : sommets.entrySet()) {
            JLabel jLabel = entry.getValue();
            if (jLabel.getBounds().intersects(new Rectangle(x, y, 30, 30))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

}