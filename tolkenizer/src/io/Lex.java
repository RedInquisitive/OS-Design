package io;

import java.text.ParseException;
import java.util.Scanner;

import enums.Keyword;
import enums.Symbol;
import enums.Type;

public class Lex implements XmlName {
	
	public final Type type;
	public final Symbol symbol;
	public final Keyword keyword;
	
	private String str;
	private static int count = 0;
	private int val;
	
	public Lex(Scanner reader) throws ParseException {
		count++;
		String read = "";
		while(read.trim().equals("")) {
			read = reader.next();
		}
		symbol = Symbol.raw(read);
		keyword = Keyword.raw(read);
		
		if(symbol != null) {
			type = Type.SYMBOL;
			return;
		}
		if(keyword != null){
			type = Type.KEYWORD;
			return;
		}
		if(read.matches("^[0-9].*")) {
			type = Type.INTEGER;
			val = parseInt(read);
			return;
		}
		if(read.contains("\"")) {
			type = Type.STRING;
			str = parseStr(read, reader);
			return;
		}
		type = Type.IDENTIFIER;
		str = read;
	}

	private String parseStr(String read, Scanner reader) throws ParseException {
		//check if second quote is in this token
		read = read.substring(read.indexOf("\"") + 1);
		if(read.indexOf("\"") >= 0)
			return read.substring(0, read.indexOf("\""));
		
		//if not, read until the second quote
		count++;
		reader.useDelimiter(Read.STRING);
		String next = reader.next();
		reader.useDelimiter(Read.TOKEN);
		if(!next.contains("\"")) throw new ParseException("End of string not found at \"" + next + "\"", getCount());
		read += next.substring(0, next.indexOf("\""));
		return read;
	}

	private int parseInt(String read) throws ParseException {
		int val = 0;
		try {
			val = Integer.parseInt(read);
		} catch(NumberFormatException e) {
			throw new ParseException("Expected integer at \"" + read + "\"", getCount());
		}
		if (val > 32767 || val < 0) {
			throw new ParseException("Out of range number at \"" + read + "\"", getCount());
		}
		return val;
	}

	public static int getCount() {
		return count;
	}

	public String xml() {
		return type.xml();
	}

	public String xmlText() {
		switch(type) {
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
			System.err.println("The default statement should never fire!");
			return null;
		}
	}
}
