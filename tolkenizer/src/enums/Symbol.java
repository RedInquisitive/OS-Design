package enums;

import io.XmlName;

public enum Symbol implements XmlName {
	LBRACE("{"),
	RBRACE("}"),
	LPER("("),
	RPER(")"),
	LBRAK("["),
	RBRAK("]"),
	DOT("."),
	COMMA(","),
	SEMI(";"),
	PLUS("+"),
	MINUS("-"),
	MULT("*"),
	DIV("/"),
	AND("&"),
	OR("|"),
	LPOINT("<"),
	RPOINT(">"),
	EQ("="),
	NOT("~");
	
	private final String name;
	private Symbol(String symbol) {
		this.name = symbol;
	}
	
	public static Symbol raw(String name) {
		for(Symbol sym : Symbol.values()) {
			if(name.equals(sym.xmlText())) {
				return sym;
			}
		}
		return null;
	}
	
	public String xml() {
		return Type.SYMBOL.xml();
	}

	public String xmlText() {
		return name;
	}
}
