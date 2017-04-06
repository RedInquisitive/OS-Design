package translator;

import java.text.ParseException;

public class Push extends Command {
	
	/**
	 * Common assembly for a push command
	 */
	public static final String PUSH =
		"@SP\n" +
		"A=M\n" +
		"M=D\n" +
		"@SP\n" +
		"M=M+1\n";
	
	/**
	 * Move the local pointer
	 */
	public static final String LOCAL = 
		"@LCL\n" +
		"D=M\n" +
		"@IDX\n" +
		"A=D+A\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * Argument to a function
	 */
	public static final String ARGUMENT = 
		"@ARG\n" +
		"D=M\n" +
		"@IDX\n" +
		"A=D+A\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * This stack
	 */
	public static final String THIS = 
		"@THIS\n" +
		"D=M\n" +
		"@IDX\n" +
		"A=D+A\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * That heap
	 */
	public static final String THAT = 
		"@THAT\n" +
		"D=M\n" +
		"@IDX\n" +
		"A=D+A\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * A pointer to this
	 * This is used when IDX is 0
	 */
	public static final String POINTER_THIS = 
		"@THIS\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * A pointer to that
	 * That is used when IDX is not 0
	 */
	public static final String POINTER_THAT = 
		"@THAT\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * A constant pushed to the stack
	 */
	public static final String CONSTANT = 
		"@IDX\n" +
		"D=A\n" +
		PUSH;
	
	/**
	 * A static variable across files pushed onto the stack
	 */
	public static final String STATIC = 
		"@FILENAME.IDX\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * Temporary register r5
	 */
	public static final String TEMP = 
		"@R5\n" +
		"D=A\n" +
		"@IDX\n" +
		"A=D+A\n" +
		"D=M\n" +
		PUSH;
	
	public Push(String name) {
		super(name, "");
	}
	
	public void parameters(String[] commands) throws ParseException {
		if(commands.length != 2) {
			throw new ParseException("You need 2 arguments for a push command!", line);
		}
		
		try {
			Integer.parseInt(commands[1]);
		} catch (NumberFormatException e) {
			throw new ParseException("The second parameter of a push command needs to be a number!", line);
		}
		
		switch (commands[0].toLowerCase()) {
		case "local":
			asm = LOCAL.replaceAll("IDX", commands[1]);
			break;
		case "argument":
			asm = ARGUMENT.replaceAll("IDX", commands[1]);
			break;
		case "this":
			asm = THIS.replaceAll("IDX", commands[1]);
			break;
		case "that":
			asm = THAT.replaceAll("IDX", commands[1]);
			break;
		case "pointer":
			if (commands[1].equals("0"))
				asm = POINTER_THIS;
			else
				asm = POINTER_THAT;
			break;
		case "constant":
			asm = CONSTANT.replaceAll("IDX", commands[1]);
			break;
		case "static":
			asm = STATIC.replaceAll("IDX", commands[1]).replaceAll("FILENAME", Translator.getFileName());
			break;
		case "temp":
			asm = TEMP.replace("IDX", commands[1]);
			break;
		default: 
			throw new ParseException(commands[1] + " is not a valid push command!", line);
		}
	}
}
