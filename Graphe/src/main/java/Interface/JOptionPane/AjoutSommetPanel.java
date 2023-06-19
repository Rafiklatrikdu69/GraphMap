package Interface.JOptionPane;

import javax.swing.*;

import java.awt.*;
import java.util.Objects;


public class AjoutSommetPanel extends JPanel {
	private JComboBox<String> choixTypeSommet;
	private JTextField entreeSommet ;
	
	public AjoutSommetPanel() {
		setLayout(new GridLayout(2,2));
		
		choixTypeSommet = new JComboBox<>();//liste des type

		add(new JLabel("Nom Sommet"));
		add(new JLabel("Type Sommet"));
		entreeSommet = new JTextField();//la saisie
		
		choixTypeSommet.addItem("Opératoire");
		choixTypeSommet.addItem("Maternité");
		choixTypeSommet.addItem("Centre de nutrition");
		
		creationSommetPanel();
	}
	
	/**
	 * Creation de la methode pour creer  une saisie pour l'utilisateur afin de creer le sommet
	 *
	 */
	private void creationSommetPanel() {
		add(entreeSommet);
		add(choixTypeSommet);
	}
	public String getNom(){
		return entreeSommet.getText();
	}
	public String getType(){
		return Objects.requireNonNull(choixTypeSommet.getSelectedItem()).toString();
	}
	
}
