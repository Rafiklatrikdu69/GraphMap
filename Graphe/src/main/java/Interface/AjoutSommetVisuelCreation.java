package Interface;

import javax.swing.*;

import java.awt.*;


public class AjoutSommetVisuelCreation extends JPanel {
	private JPanel panelAjoutSommet;
	private JComboBox<String> choixTypeSommet;
	private JTextField entreeSommet ;
	
	AjoutSommetVisuelCreation() {
		System.out.println("test classe !");
		choixTypeSommet = new JComboBox<>();
		
		entreeSommet = new JTextField();
		
		choixTypeSommet.addItem("O");
		choixTypeSommet.addItem("M");
		choixTypeSommet.addItem("N");
		
		creationSommetPanel();
		add(panelAjoutSommet);
	}
	
	private void creationSommetPanel() {
		panelAjoutSommet = new JPanel();
		panelAjoutSommet.setLayout(new BorderLayout());
		panelAjoutSommet.setBorder(BorderFactory.createTitledBorder("panel ajout sommet"));
		panelAjoutSommet.add(choixTypeSommet, BorderLayout.EAST);
		panelAjoutSommet.add(entreeSommet, BorderLayout.WEST);
	}
	
}
