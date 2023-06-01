import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;


public class LancerInterface {
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
