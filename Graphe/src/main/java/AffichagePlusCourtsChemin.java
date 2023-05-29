import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
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
        
        for (Map.Entry<LCGraphe.MaillonGraphe, JLabel> entry : sommets.entrySet()) {
            LCGraphe.MaillonGraphe sommet = entry.getKey();
            JLabel label = entry.getValue();
            int x = label.getX() + label.getWidth() / 2;
            int y = label.getY() + label.getHeight() / 2;
            radius = label.getWidth() / 2;
            
            g2d.fill(new Ellipse2D.Double(x - radius, y - radius, radius * 2, radius * 2));
        }
    }
    
   
 
  
}
