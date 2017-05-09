package enums;

import io.XmlName;

public enum Keyword implements XmlName {
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
	
	private final String name;
	private Keyword(String name) {
		this.name = name;
	}
	
	public static Keyword raw(String name) {
		for(Keyword key : Keyword.values()) {
			if(name.equals(key.xmlText())) {
				return key;
			}
		}
		return null;
	}

	public String xml() {
		return Type.KEYWORD.xml();
	}

	public String xmlText() {
		return name;
	}
}
