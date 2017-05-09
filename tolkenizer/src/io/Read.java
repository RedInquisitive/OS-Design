package io;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.Pattern;

import enums.Symbol;

public class Read {
	
	public static final String DELIMITER_SEPERATE = "((?<=%1$s)|(?=%1$s))";
	public static final String DELIMITER_AFTER = "(?<=%1$s)";
	public static final Pattern TOKEN;
	public static final Pattern STRING;
	
	static {
		StringBuilder regex = new StringBuilder();
		for(Symbol s : Symbol.values()) regex.append("|" + String.format(DELIMITER_SEPERATE, Pattern.quote(s.xmlText() + "")));
		String find = "(\\s+)|([\\r\\n]+)" + regex.toString();
		TOKEN = Pattern.compile(find);
		STRING = Pattern.compile(String.format(DELIMITER_AFTER, Pattern.quote("\"")) + "|([\\r\\n]+)");
	}
	
	private final Scanner reader;
	
	public Read(InputStream in) {
		reader = new Scanner(in);
		reader.useDelimiter(TOKEN);
	}
	
	public boolean hasNext() {
		return reader.hasNext();
	}
	
	public Lex next() throws ParseException {
		return new Lex(reader);
	}
}
