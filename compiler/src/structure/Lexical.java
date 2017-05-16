package structure;

import java.text.ParseException;

import tokenizer.Main;
import xml.XmlName;

public enum Lexical implements XmlName {
	INVALID("invalid"),
	KEYWORD("keyword"),
	SYMBOL("symbol"),
	INTEGER("integerConstant"),
	STRING("stringConstant"),
	IDENTIFIER("identifier");
	
	private final String name;
	private Lexical(String name) {
		this.name= name;
	}

	public String xml() {
		return name;
	}
	
	public String xmlText() throws ParseException {
		throw Main.textlessXML(getClass().getName());
	}
}
