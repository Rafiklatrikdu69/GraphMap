package Interface.InfosSommetPanel;

public enum ChoixTypeSommet {
	MATERNITE("M"),
	NUTRITION("N"),
	OPERATOIRE("O");
	private String type;
	ChoixTypeSommet(String m){
		type = m;
	}
	public String getTypeSommet(){
		return type;
	}
}
