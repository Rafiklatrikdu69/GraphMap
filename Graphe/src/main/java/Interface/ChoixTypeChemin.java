package Interface;

public enum ChoixTypeChemin {
	FIABILITE("Fiabilité"),
	DISTANCE("Distance"),
	DUREE("Durée");
	private String choix;
	
	ChoixTypeChemin(String attribut) {
		this.choix = attribut;
	}
	
	public String getAttribut() {
		return this.choix;
	}
}
