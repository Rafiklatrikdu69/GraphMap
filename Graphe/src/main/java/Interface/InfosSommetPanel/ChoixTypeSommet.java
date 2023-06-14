package Interface.InfosSommetPanel;

public enum ChoixTypeSommet {
	MATERNITE("Maternité"),
	NUTRITION("Nutrition"),
	OPERATOIRE("Opératoire");
	private String type;
	ChoixTypeSommet(String m){
		type = m;
	}
	public String getTypeSommet(){
		return type;
	}
}
