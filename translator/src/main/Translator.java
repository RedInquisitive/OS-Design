package main;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

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
		
		Scanner reader = new Scanner(System.in);
		int lineNumber = 0;
		while(reader.hasNextLine()) {
			try {
				lineNumber++;
				String[] command = reader.nextLine().replaceAll("//.*", "").trim().split(" ");
				if(command.length == 0) continue;
				if(command[0].equals("EOF")) break;
				
				Command found = null;
				for(Command search : commands) {
					if(search.toString().equals(command[0])) {
						found = search;
						break;
					}
				}
				
				if(found == null) throw new ParseException("Command " + command[0] + " not implemented!", lineNumber);
				found.setLine(lineNumber);
				found.setParameters(command);
				System.out.println(found.getAsm());
			} catch (ParseException e) {
				System.err.println("An error occured during translation!");
				System.err.println("On line " + e.getErrorOffset() + " an exception was thrown: ");
				e.printStackTrace();
				reader.close();
				System.exit(-1);
			}
		}
		reader.close();
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
