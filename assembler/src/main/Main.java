package main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static void main(String args[]) {
		int line = 1, rom = 0;
		List<Command> commands = new ArrayList<>();
		
		Scanner reader = new Scanner(System.in);
		while(reader.hasNextLine()) {
			String raw = Command.parse(reader.nextLine());
			if(raw != null)  {
				if(raw.equals("EOF")) break;
				Command command = new Command(raw, line, rom);
				commands.add(command);
				if(command.getType() != Command.Type.LABEL) rom++;
			}
			line++;
		}
		reader.close();
		
		try { 
			Symbols symbols = new Symbols(commands);
			new Generate(symbols, commands).parse();
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
	}
}
