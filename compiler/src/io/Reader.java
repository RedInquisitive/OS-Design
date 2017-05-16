package io;

import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import structure.Lexical;
import symbols.Keyword;
import symbols.Symbol;

public class Reader  {
	
	public static final String DELIMITER_SEPERATE = "((?<=%1$s)|(?=%1$s))";
	public static final String DELIMITER_AFTER = "(?<=%1$s)";
	public static final String FIND;

	private static int count = 0;
	private final Scanner reader;
	private List<String> queue = new ArrayList<>();
	private Token last = null;
	private boolean abort = false;
	
	static {
		StringBuilder regex = new StringBuilder();
		for(Symbol s : Symbol.values()) regex.append("|" + String.format(DELIMITER_SEPERATE, Pattern.quote(s.xmlText() + "")));
		FIND = "(\\s+)|(//.*)" + regex.toString();
	}
	
	public Reader(InputStream in) {
		reader = new Scanner(in);
	}

	public static int getCount() {
		return count;
	}
	
	public boolean hasNext() {
		return reader.hasNext();
	}
	
	private void fill() throws ParseException {
		if(!reader.hasNextLine()) throw new ParseException("No more lines!", count);
		String line = reader.nextLine();
		
		//match long comments
		while(line.matches(".*\\/\\*\\*.*")) {
			if(line.matches(".*\\/\\*\\*.*\\*\\/.*")) {
				line = line.replaceAll("\\/\\*\\*.+?\\*\\/", "");
				if(!line.matches(".*\\/\\*\\*.*")) break;
			}
			line += " " + reader.nextLine();
		}
		
		//match strings
		Matcher m = Pattern.compile("\\\".+?\\\"").matcher(line);
		while (m.find()) {
			String old = m.group();
			String nul = old.replaceAll("\\s", "\u0000");
			line = line.replace(old, nul);
		}
		
		//match tokens
		List<String> segments = Arrays.asList(line.split(FIND));
		
		//if there is nothing, restart
		if(segments.size()  == 0) return;
		queue.addAll(segments);
	}
	
	public Token next() throws ParseException {
		if(abort) {
			abort = false;
			return last;
		}
		
		count++;
		String read = "";
		while(read.trim().equals("")) {
			if(queue.size() == 0 ) fill();
			if(queue.size() > 0) read = queue.remove(0);
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
			str = read.trim();
			str = str.substring(1, str.length() - 1);
			str = str.replaceAll("\\x00", " ");
		} else {
			type = Lexical.IDENTIFIER;
			str = read;
		}
		last = new Token(type, symbol, keyword, str, val);
		return last;
	}
	
	public void abort() {
		abort = true;
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
