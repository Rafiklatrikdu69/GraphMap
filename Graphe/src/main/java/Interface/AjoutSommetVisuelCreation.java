package Interface;

import javax.swing.*;

import java.awt.*;
import java.util.Objects;


public class AjoutSommetVisuelCreation extends JPanel {
	private JPanel panelAjoutSommet;
	private JComboBox<String> choixTypeSommet;
	private JTextField entreeSommet ;
	
	AjoutSommetVisuelCreation() {
		System.out.println("test classe !");
		choixTypeSommet = new JComboBox<>();//liste des type
		
		entreeSommet = new JTextField();//la saisie
		
		choixTypeSommet.addItem("O");
		choixTypeSommet.addItem("M");
		choixTypeSommet.addItem("N");
		
		creationSommetPanel();
		add(panelAjoutSommet);//ajout du panel dans la classe elle meme
	}
	
	/**
	 * Creation de la methode pour creer  une saisie pour l'utilisateur afin de creer le sommet
	 *
	 */
	private void creationSommetPanel() {
		panelAjoutSommet = new JPanel();
		panelAjoutSommet.setLayout(new BorderLayout());
		panelAjoutSommet.setBorder(BorderFactory.createTitledBorder("panel ajout sommet"));
		panelAjoutSommet.add(choixTypeSommet, BorderLayout.EAST);
		panelAjoutSommet.add(entreeSommet, BorderLayout.WEST);
	}
	public String getNom(){
		return entreeSommet.getText();
	}
	public String getType(){
		return Objects.requireNonNull(choixTypeSommet.getSelectedItem()).toString();
	}
	
}
