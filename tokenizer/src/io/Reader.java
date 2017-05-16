package io;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

import structure.Lexical;
import symbols.Keyword;
import symbols.Symbol;

public class Reader  {
	
	public static final String DELIMITER_SEPERATE = "((?<=%1$s)|(?=%1$s))";
	public static final String DELIMITER_AFTER = "(?<=%1$s)";
	public static final Pattern TOKEN;
	public static final Pattern STRING;

	private static int count = 0;
	private final Scanner reader;
	private Token last = null;
	private boolean abort = false;
	
	static {
		StringBuilder regex = new StringBuilder();
		for(Symbol s : Symbol.values()) regex.append("|" + String.format(DELIMITER_SEPERATE, Pattern.quote(s.xmlText() + "")));
		String find = "(\\s+)|(//.*)|([\\r\\n]+)" + regex.toString();
		TOKEN = Pattern.compile(find);
		STRING = Pattern.compile(String.format(DELIMITER_AFTER, Pattern.quote("\"")) + "|([\\r\\n]+)");
	}
	
	public Reader(InputStream in) {
		reader = new Scanner(in);
		reader.useDelimiter(TOKEN);
	}

	public static int getCount() {
		return count;
	}
	
	public boolean hasNext() {
		return reader.hasNext();
	}
	
	public Token next() throws ParseException {
		if(abort) {
			abort = false;
			return last;
		}
		count++;
		String read = "";
		while(read.trim().equals("")) {
			if(!reader.hasNext()) throw new ParseException("Ran out of tokens!", count);
			read = reader.next();
		}
		
		Lexical type;
		Symbol symbol = Symbol.raw(read);
		Keyword keyword = Keyword.raw(read);
		String str = null;
		int val = 0;
		
		if(symbol != null) {
			type = Lexical.SYMBOL;
		} else if(keyword != null){
			type = Lexical.KEYWORD;
		} else if(read.matches("^[0-9].*")) {
			type = Lexical.INTEGER;
			val = parseInt(read);
		} else if(read.contains("\"")) {
			type = Lexical.STRING;
			str = parseStr(read, reader);
		} else {
			type = Lexical.IDENTIFIER;
			str = read;
		}
		last = new Token(type, symbol, keyword, str, val);
		System.out.println(read);
		return last;
	}
	
	public void abort() {
		abort = true;
	}

	private String parseStr(String read, Scanner reader) throws ParseException {
		//check if second quote is in this token
		read = read.substring(read.indexOf("\"") + 1);
		if(read.indexOf("\"") >= 0)
			return read.substring(0, read.indexOf("\""));
		
		//if not, read until the second quote
		count++;
		reader.useDelimiter(STRING);
		String next = reader.next();
		reader.useDelimiter(TOKEN);
		
		//no end quote
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
}
