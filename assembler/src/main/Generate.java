package main;

import java.text.ParseException;
import java.util.List;

public class Generate {
	
	private Symbols symbols;
	private List<Command> commands;

	public Generate(Symbols symbols, List<Command> commands) {
		this.symbols = symbols;
		this.commands = commands;
	}

	public void parse() throws ParseException {
		parsing: for(Command command : commands) {
			switch(command.getType()) {
			case ADDRESS:
				write(command.getRAM(symbols));
				break;
			case COMMAND:
				write(0b1110000000000000 + (command.getCompare() << 6) + (command.getDestination() << 3) + command.getJump());
				break;
			case LABEL: 
				continue parsing;
			}
		}
	}
	
	public void write(int binary) {
		System.out.println(String.format("%16s", Integer.toBinaryString(binary)).replace(' ', '0'));
	}
}
