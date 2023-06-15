package Interface.InfosSommetPanel;

public enum ChoixTypeSommet {
	MATERNITE("Maternité", "M"),
	NUTRITION("Centre de nutrition", "N"),
	OPERATOIRE("Opératoire", "O");
	private String type;
	private String code;
	ChoixTypeSommet(String type, String code){
		this.code = code;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public String getTypeSommet(){
		return type;
	}
}
