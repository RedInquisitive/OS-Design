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
	NOT('~'),
	QUOTE('"');
	
	public final char name;
	private Symbol(char symbol) {
		this.name = symbol;
	}
}
