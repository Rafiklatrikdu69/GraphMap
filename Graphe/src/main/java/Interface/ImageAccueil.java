package Interface;

import javax.swing.*;

public enum ImageAccueil {
	MEDECIN("C:\\Users\\Rafik\\Documents\\SAE\\sae_java_outil_aide_a_la_decision\\Graphe\\src\\img\\doctor.png");
	private ImageIcon imageIcon;
	ImageAccueil(String imagePath){
	imageIcon = new ImageIcon(imagePath);
	}
	
	public  ImageIcon getImageIcon() {
		return  imageIcon;
	}
}
