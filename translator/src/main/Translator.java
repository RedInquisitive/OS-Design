package main;
import java.util.ArrayList;

import translator.Call;
import translator.Command;
import translator.Compare;
import translator.Function;
import translator.Goto;
import translator.Label;
import translator.Pop;
import translator.Push;

public class Translator {
	private static String fileName, functionName;
	
	public static void main(String[] args) {
		ArrayList<Command> commands = new ArrayList<>();
		commands.add(new Goto("goto", "@" + Replace.FUNKYLABEL + "\n" + "0;JMP\n"));
		commands.add(new Goto("if-goto", Command.UNARY  + "D=M\n" + "@" + Replace.FUNKYLABEL + "\n" + "0;JNE\n"));
		commands.add(new Command("neg",  Command.UNARY  + "M=-M\n"));
		commands.add(new Command("not",  Command.UNARY  + "M=!M\n"));
		commands.add(new Command("add",  Command.BINARY + "M=D+M\n"));
		commands.add(new Command("sub",  Command.BINARY + "M=M-D\n"));
		commands.add(new Command("and",  Command.BINARY + "M=D&M\n"));
		commands.add(new Command("or",   Command.BINARY + "M=D|M\n"));
		commands.add(new Command("return", Command.RETURN));
		commands.add(new Compare("gt", "D;JGT\n")); 
		commands.add(new Compare("lt", "D;JLT\n")); 
		commands.add(new Compare("eq", "D;JEQ\n")); 
		commands.add(new Push("push"));
		commands.add(new Pop("pop"));
		commands.add(new Label("label"));
		commands.add(new Call("call"));
		commands.add(new Function("function"));
	}
	
	public static String getFileName() {
		return fileName;
	}

	public static String getFunctionName() {
		return functionName;
	}
	
	public static void setFunctionName(String function) {
		functionName = function;
	}
}
