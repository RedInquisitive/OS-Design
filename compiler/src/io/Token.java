package io;

import java.text.ParseException;

import structure.Lexical;
import symbols.Keyword;
import symbols.Symbol;
import xml.XmlName;

public class Token implements XmlName {
	private final Lexical lexical;
	private final Symbol symbol;
	private final Keyword keyword;
	
	private final String str;
	private final int val;
	
	public Token(Lexical type, Symbol symbol, Keyword keyword, String str, int val) {
		this.lexical = type;
		this.symbol = symbol;
		this.keyword = keyword;
		this.str = str;
		this.val = val;
	}

	public Lexical getLexical() {
		return lexical;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public String xml() {
		return lexical.xml();
	}
	
	public String xmlText() throws ParseException {
		switch(lexical) {
		case IDENTIFIER:
			return str;
		case STRING:
			return str;
		case INTEGER:
			return val + "";
		case KEYWORD:
			return keyword.xmlText();
		case SYMBOL:
			return symbol.xmlText();
		default:
			throw new ParseException("This should never happen. Please report it if it does!", Reader.getCount());
		}
	}
}
