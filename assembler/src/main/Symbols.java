package main;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Symbols {
	private final Map<String, Integer> symbols = new HashMap<>();
	int ram = 16;
	
	public Symbols(List<Command> commands) throws ParseException {
		symbols.put("SP", 0);
		symbols.put("LCL", 1);
		symbols.put("ARG", 2);
		symbols.put("THIS", 3);
		symbols.put("THAT", 4);
		symbols.put("SCREEN", 16384);
		symbols.put("KBD", 24576);
		symbols.put("R0", 0);
		symbols.put("R1", 1);
		symbols.put("R2", 2);
		symbols.put("R3", 3);
		symbols.put("R4", 4);
		symbols.put("R5", 5);
		symbols.put("R6", 6);
		symbols.put("R7", 7);
		symbols.put("R8", 8);
		symbols.put("R9", 9);
		symbols.put("R10", 10);
		symbols.put("R11", 11);
		symbols.put("R12", 12);
		symbols.put("R13", 13);
		symbols.put("R14", 14);
		symbols.put("R15", 15);
		
		for(Command command : commands) {
			if(command.getType() == Command.Type.LABEL) {
				if(symbols.put(command.getLabel(), command.getROM()) != null) {
					throw new ParseException("Label " + command.getLabel() + " is redefined on line " + command.getLine() + "!", command.getLine());
				}
			}
		}
	}
	
	public Integer put(String name) {
		Object symbol = symbols.put(name, ram);
		if(symbol == null) ram++;
		return Integer.valueOf(ram - 1);
	}
	
	public Integer get(String symbol) {
		return symbols.get(symbol);
	}
}
