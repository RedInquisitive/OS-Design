package main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
	
	public static String INIT = 
		"@256\n" +
		"D=A\n" +
		"@SP\n" +
		"M=D\n" +
		"// call Sys.init 0\n" +
		 Replace.INIT +
		 "0;JMP\n";
	
	public static void main(String[] args) {
		List<Command> commands = new ArrayList<>();
		List<File> files = new ArrayList<>();
		List<BundledStream> streams = new ArrayList<>();
		init(commands);
		Mode mode;
		
		for(String path : args) {
			files.add(new File(path));
		}
		
		File system = searchFile(files, "Sys.vm", "It is impossible to deturmine the jump bootstrap!");
		if(system != null) {
			mode = Mode.BOOTSTRAP;
		} else if(files.size() > 0) {
			mode = Mode.INDIVIDUAL;
		} else {
			mode = Mode.STANDARD;
		}
		
		try {
			System.err.println("Using mode: " + mode);
			switch(mode) {
			case INDIVIDUAL:
				for(File file : files) {
					String oldFile = file.getAbsoluteFile().toString();
					String newFile = oldFile.substring(0, oldFile.lastIndexOf('.')) + ".asm";
					FileInputStream fis = new FileInputStream(file);
					FileOutputStream fos = new FileOutputStream(new File(newFile));
					PrintStream pos = new PrintStream(fos);
					streams.add(new BundledStream(fis, pos, file.getName(), file.getAbsolutePath()));
				}
				break;
			case BOOTSTRAP:
				boolean append = false;
				for(File file : files) {
					FileInputStream fis = new FileInputStream(file);
					FileOutputStream fos = new FileOutputStream(new File(system.getParent(), "Sys.asm"), append);
					PrintStream pos = new PrintStream(fos);
					streams.add(new BundledStream(fis, pos, file.getName(), file.getAbsolutePath()));
					append = true;
				}
				Command bootstrap = searchCommand(commands, "call");
				bootstrap.setParameters(new String[]{"call", "Sys.init", "0"});
				streams.get(0).out.println(INIT.replace(Replace.INIT, bootstrap.getAsm()));
				break;
			case STANDARD:
				streams.add(new BundledStream(System.in, System.out, "std", "java.io"));
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(2);
		} catch (ParseException e) {
			System.err.println("Something went wrong when setting up the bootstrap!");
			e.printStackTrace();
			System.exit(4);
		}
		
		for(BundledStream io : streams) {
			fileName = io.name;
			if(mode == Mode.INDIVIDUAL) init(commands);
			Scanner reader = new Scanner(io.in);
			int lineNumber = 0;
			while(reader.hasNextLine()) {
				try {
					lineNumber++;
					String input = reader.nextLine().replaceAll("//.*", "").trim();
					String[] command = input.split(" ");
					if(input.length() == 0) continue;
					if(command.length == 0) continue;
					if(command[0].equals("EOF")) break;
					
					Command found = searchCommand(commands, command[0]);
					if(found == null) throw new ParseException("Command " + command[0] + " not implemented!", lineNumber);
					found.setLine(lineNumber);
					found.setParameters(command);
					io.out.println();
					io.out.println("//" + input);
					io.out.print(found.getAsm());
				} catch (ParseException e) {
					System.err.println("An error occured during translation!");
					System.err.println("In file: " + io.name);
					System.err.println("Of path: " + io.path);
					System.err.println("On line: " + e.getErrorOffset() + " an exception was thrown: ");
					e.printStackTrace();
					reader.close();
					System.exit(1);
				}
			}
			io.out.flush();
			reader.close();
			System.err.println("Completed file " + io.name + " in path " + io.path);
		}
	}
	
	private static void init(List<Command> commands) {
		commands.clear();
		commands.add(new Goto("goto", "@" + Replace.FUNKYLABEL + "\n" + "0;JMP\n"));
		commands.add(new Goto("if-goto", Command.COND   + "D=M\n" + "@" + Replace.FUNKYLABEL + "\n" + "D;JNE\n"));
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
		commands.add(new Function("function"));
		commands.add(new Call("call"));
	}

	private static File searchFile(List<File> files, String search, String haltMessage) {
		int count = 0;
		File found = null;
		for(File file : files) {
			if(file.getName().equals(search)) {
				count++;
				found = file;
				if(haltMessage != null && count > 1) {
					System.err.println("Multiple input files called " + search);
					System.err.println(haltMessage);
					System.exit(3);
				}
			}
		}
		return found;
	}
	
	private static Command searchCommand(List<Command> commands, String search) {
		for(Command check : commands) {
			if(check.toString().equals(search)) return check;
		}
		return null;
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
