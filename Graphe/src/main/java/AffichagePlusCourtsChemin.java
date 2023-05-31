import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;
import java.util.Map;

public class AffichagePlusCourtsChemin extends DessinGraphe {
    protected static final Color SELECTED_LABEL_COLOR = Color.RED;
    private Map<LCGraphe.MaillonGraphe, JLabel> sommets;
    private DessinGraphe dessinGraphe;
    
    public AffichagePlusCourtsChemin(Map<LCGraphe.MaillonGraphe, JLabel> sommets,DessinGraphe d) {
        super();
        this.sommets = sommets;
        this.dessinGraphe = d;
     
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
                
                if (dessinGraphe.sommetSelectionne == sommet2) {
                    g2d.setColor(SELECTED_LABEL_COLOR);
                    g2d.fill(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
                } else {
                    g2d.setColor(dessinGraphe.BACKGROUND_COLOR);
                    g2d.fill(new Ellipse2D.Double(x2 - radius, y2 - radius, radius * 2, radius * 2));
                }
                
                if (sommet1.estVoisin(sommet2.getNom())) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }
        
        
        
    }
    
   
 
  
}
