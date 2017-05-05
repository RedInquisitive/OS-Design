package io;

import java.text.ParseException;
import java.util.Scanner;

import enums.Keyword;
import enums.Symbol;
import enums.Type;

public class Lex {
	
	private static int count = 0;
	public final Type type;
	public final String str;
	public final int val;
	
	public Lex(Scanner reader) throws ParseException {
		count++;
		String read = reader.next();
		if(read.trim().equals("")) {
			type = Type.NOTHING;
			str = read;
			val = 0;
			return;
		}
		if(Symbol.fromString(read) != null) {
			type = Type.SYMBOL;
			str = read;
			val = 0;
			return;
		}
		if(Keyword.fromString(read) != null){
			type = Type.KEYWORD;
			str = read;
			val = 0;
			return;
		}
		if(read.matches("^[0-9]*")) {
			type = Type.INTEGER;
			str = read;
			val = parseInt(read);
			return;
		}
		if(read.contains("\"")) {
			type = Type.STRING;
			val = 0;
			str = parseStr(read, reader);
			return;
		}
		val = 0;
		str = read;
		type = Type.IDENTIFIER;
	}

	private String parseStr(String read, Scanner reader) throws ParseException {
		
		//check if second quote is in this token
		read = read.substring(read.indexOf("\"") + 1);
		if(read.indexOf("\"") >= 0)
			return read.substring(0, read.indexOf("\""));
		
		//if not, read until the second quote
		reader.useDelimiter(Read.STRING);
		String next = reader.next();
		reader.useDelimiter(Read.TOKEN);
		if(!next.contains("\"")) throw new ParseException("End of string not found at \"" + next + "\"", count);
		read += next.substring(0, next.indexOf("\""));
		return read;
	}

	private int parseInt(String read) throws ParseException {
		int val = 0;
		try {
			val = Integer.parseInt(read);
		} catch(NumberFormatException e) {
			throw new ParseException("Expected integer at \"" + read + "\"", count);
		}
		if (val > 32767 || val < 0) {
			throw new ParseException("Out of range number at \"" + read + "\"", count);
		}
		return val;
	}
}
