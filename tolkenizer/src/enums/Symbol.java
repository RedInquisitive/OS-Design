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
	
	public final char symbol;
	private Symbol(char symbol) {
		this.symbol = symbol;
	}
}
