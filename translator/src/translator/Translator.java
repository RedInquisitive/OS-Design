package translator;

import java.util.ArrayList;

public class Translator {
	private static String fileName;

	public static void main(String[] args) {
		ArrayList<Command> commands = new ArrayList<>();
		commands.add(new Command("neg", Command.UNARY + "M=-M\n"));
		commands.add(new Command("not", Command.UNARY + "M=!M\n"));
		commands.add(new Command("add", Command.BINARY + "M=D+M\n"));
		commands.add(new Command("sub", Command.BINARY + "M=M-D\n"));
		commands.add(new Command("and", Command.BINARY + "M=D&M\n"));
		commands.add(new Command("or",  Command.BINARY + "M=D|M\n"));
		commands.add(new Command("return", Command.RETURN));
		commands.add(new Compare("gt", "D;JGT\n")); 
		commands.add(new Compare("lt", "D;JLT\n")); 
		commands.add(new Compare("eq", "D;JEQ\n")); 
		commands.add(new Push("push"));
		commands.add(new Pop("pop"));
	}
	
	public static String getFileName() {
		return fileName;
	}
}
