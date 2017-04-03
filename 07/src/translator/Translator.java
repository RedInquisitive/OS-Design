package translator;

import java.util.ArrayList;

public class Translator {
		  
	public static void main(String[] args) {
		ArrayList<Command> commands = new ArrayList<>();
		commands.add(new Command("neg", Command.SINGLE + "M=-M\n"));
		commands.add(new Command("not", Command.SINGLE + "M=!M\n"));
		commands.add(new Command("add", Command.DOUBLE + "M=D+M\n"));
		commands.add(new Command("sub", Command.DOUBLE + "M=M-D\n"));
		commands.add(new Command("and", Command.DOUBLE + "M=D&M\n"));
		commands.add(new Command("or",  Command.DOUBLE + "M=D|M\n"));
		commands.add(new Command("return", Command.RETURN));
		commands.add(new Compare("gt", "D;JGT\n")); 
		commands.add(new Compare("lt", "D;JLT\n")); 
		commands.add(new Compare("eq", "D;JEQ\n")); 
		
		ArrayList<Command> argues = new ArrayList<>();
		argues.add(new Command("push", ""));
	}

}
