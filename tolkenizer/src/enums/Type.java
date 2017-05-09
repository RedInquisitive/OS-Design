package enums;

import io.XmlName;

public enum Type implements XmlName {
	KEYWORD("keyword"),
	SYMBOL("symbol"),
	INTEGER("integerConstant"),
	STRING("stringConstant"),
	IDENTIFIER("identifier");
	
	private final String name;
	private Type(String name) {
		this.name= name;
	}

	public String xml() {
		return name;
	}
	
	public String xmlText() {
		return "";
	}
}
