package enums;

public enum Symbol {
	LBRACE('{'),
	RBRACE('}'),
	LPER('('),
	RPER(')'),
	LBRAK('['),
	RBRAK(']'),
	DOT('.'),
	COMMA(','),
	SEMI(';'),
	PLUS('+'),
	MINUS('-'),
	MULT('*'),
	DIV('/'),
	AND('&'),
	OR('|'),
	LPOINT('<'),
	RPOINT('>'),
	EQ('='),
	NOT('~');
	
	public final char name;
	
	private Symbol(char symbol) {
		this.name = symbol;
	}
	
	public String toString() {
		return name + "";
	}
	
	public static Symbol fromString(String name) {
		for(Symbol sym : Symbol.values()) {
			if(name.equals(sym.toString())) {
				return sym;
			}
		}
		return null;
	}
}
