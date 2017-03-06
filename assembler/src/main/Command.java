package main;

import java.text.ParseException;

public class Command {
	
	private String command;
	private int line;
	private int rom;
	
	public enum Type {
		ADDRESS, COMMAND, LABEL;
		public static Type of(String command) {
			if(command.charAt(0) == '@') return ADDRESS;
			if(command.charAt(0) == '(') return LABEL;
			return COMMAND;
		}
	}
	
	public Command(String command, int line, int rom) {
		this.command = command;
		this.line = line;
		this.rom = rom;
	}
	
	public Type getType() {
		return Type.of(command);
	}
	
	public char getDestination() throws ParseException {
		if(getType() != Type.COMMAND) throw error("a command");
		if (command.indexOf('=') == -1) return 0;
		String lhs = command.replaceAll("=.*", "");
		char res = 0;
		if (lhs.indexOf('A') != -1) res |= 4;
		if (lhs.indexOf('D') != -1) res |= 2;
		if (lhs.indexOf('M') != -1) res |= 1;
		return res;
	}
	
	public char getCompare() throws ParseException {
		if(getType() != Type.COMMAND) throw error("a command");
		String s = command.replaceAll(".*=", "");
		s = s.replaceAll(";.*", "");
		switch (s) {
		case "0":	return 0b0101010;
		case "1":	return 0b0111111;
		case "-1":	return 0b0111010;
		case "D":	return 0b0001100;
		case "A":	return 0b0110000;
		case "!D":	return 0b0001101;
		case "!A":	return 0b0110001;
		case "-D":	return 0b0001111;
		case "-A":	return 0b0110011;
		case "D+1":	return 0b0011111;
		case "A+1":	return 0b0110111;
		case "D-1":	return 0b0001110;
		case "A-1":	return 0b0110010;
		case "D+A":	return 0b0000010;
		case "D-A":	return 0b0010011;
		case "A-D":	return 0b0000111;
		case "D&A":	return 0b0000000;
		case "D|A":	return 0b0010101;

		case "M":	return 0b1110000;
		case "!M":	return 0b1110001;
		case "-M":	return 0b1110011;
		case "M+1":	return 0b1110111;
		case "M-1":	return 0b1110010;
		case "D+M":	return 0b1000010;
		case "D-M":	return 0b1010011;
		case "M-D":	return 0b1000111;
		case "D&M":	return 0b1000000;
		case "D|M":	return 0b1010101;
		default:	throw error("a command with a valid comparison (it has " + s + ")");
		}
	}
	
	public char getJump() throws ParseException {
		if(getType() != Type.COMMAND) throw error("a command");
		if (command.indexOf(';') == -1) return 0;
		String rhs = command.replaceAll(".*;", "");
		switch (rhs) {
		case "JGT":	return 0b001;
		case "JEQ":	return 0b010;
		case "JGE":	return 0b011;
		case "JLT":	return 0b100;
		case "JNE":	return 0b101;
		case "JLE":	return 0b110;
		case "JMP":	return 0b111;
		default:	return 0;
		}
	}
	
	public String getLabel() throws ParseException {
		if(getType() != Type.LABEL) throw error("a label");
		return command.substring(1, command.length() - 1);
	}
	
	public int getRAM(Symbols symbols) throws ParseException {
		if(getType() != Type.ADDRESS) throw error("an address");
		String name = command.substring(1);
		try {
			int ram = Integer.parseInt(name);
			return ram;
		} catch(NumberFormatException e) {
			Integer address = symbols.get(name);
			if(address == null) address = symbols.put(name);
			return address;
		}
	}
	
	public int getROM() {
		return rom;
	}
	
	public int getLine() {
		return line;
	}
	
	public static String parse(String raw) {
		raw = raw.replaceAll("\\s+", ""); //remove spaces
		raw = raw.replaceAll("//.*", ""); //remove comments
		if(raw.length() == 0) return null;
		return raw;
	}

	private ParseException error(String not) {
		return new ParseException("The command " + command + " on line " + line + " is not " + not + "!", line);
	}
	
}
