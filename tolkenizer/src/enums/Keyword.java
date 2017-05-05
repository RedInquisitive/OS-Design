package enums;

public enum Keyword {
	CLASS("class"),
	CONSTRUCTOR("constructor"),
	FUNCTION("function"),
	METHOD("method"),
	FIELD("field"),
	STATIC("static"),
	VAR("var"),
	INT("int"),
	CHAR("char"),
	BOOLEAN("boolean"),
	VOID("void"),
	TRUE("true"),
	FALSE("false"),
	NULL("null"),
	THIS("this"),
	LET("let"),
	DO("do"),
	IF("if"),
	ELSE("else"),
	WHILE("while"),
	RETURN("return");
	
	public final String name;
	
	private Keyword(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public static Keyword fromString(String name) {
		for(Keyword key : Keyword.values()) {
			if(name.equals(key.toString())) {
				return key;
			}
		}
		return null;
	}
}
