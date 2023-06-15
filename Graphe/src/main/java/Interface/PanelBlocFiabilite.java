package Interface;

import javax.swing.*;
import java.awt.*;

public class PanelBlocFiabilite extends JPanel {
	
	private AreteVisuel arete;
	
	PanelBlocFiabilite(AreteVisuel areteVisuel) {
		arete = areteVisuel;
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Dessiner un rectangle

		int width = 100; // Largeur du rectangle
		int height = 50; // Hauteur du rectangle
		int ax = arete.getSommetVisuel1().getCentreDuCercle().x;
		int bx = arete.getSommetVisuel2().getCentreDuCercle().x;
		int ay = arete.getSommetVisuel1().getCentreDuCercle().y;
		int by = arete.getSommetVisuel2().getCentreDuCercle().y;
		g.drawRect((ax+bx)/2, (ay+by)/2, width, height);
	}
}
