import javax.swing.*;
import java.awt.*;

public class InterfaceGraphe extends JFrame {

    private JMenuBar menu;
    private JMenu j;
    private JMenuItem option1,option2,option3;

    public InterfaceGraphe() {
        initComponents();
        setVisible(true);
    }

    public void initComponents() {
        JPanel cp = (JPanel) getContentPane();
        cp.setLayout(new FlowLayout());
        menu = new JMenuBar();
        j = new JMenu("menu");
        option1 = new JMenuItem("Afficher tous les dispensaires");
        option2 = new JMenuItem("Sélectionner un dispensaire");
        option3 = new JMenuItem("option 3");
        j.add(option1);   
        j.add(option2);
        j.add(option3);
        menu.add(j);
        setJMenuBar(menu);
        setPreferredSize(new Dimension(800, 600));
        setTitle("Menu d'accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // centrage de la fenêtre à l'écran
    }
}
