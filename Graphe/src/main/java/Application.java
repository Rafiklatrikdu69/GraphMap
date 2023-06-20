import Interface.InterfaceGraphe;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;


public class Application {
    public static void main(String[] args){
        

        try {
            // Changer le thème en utilisant le nom de classe du look and feel
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            new InterfaceGraphe();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
