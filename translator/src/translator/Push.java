package translator;

import java.text.ParseException;

import main.Replace;
import main.Translator;

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
		"@" + Replace.IDX + "\n" +
		"A=D+A\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * Argument to a function
	 */
	public static final String ARGUMENT = 
		"@ARG\n" +
		"D=M\n" +
		"@" + Replace.IDX + "\n" +
		"A=D+A\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * This stack
	 */
	public static final String THIS = 
		"@THIS\n" +
		"D=M\n" +
		"@" + Replace.IDX + "\n" +
		"A=D+A\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * That heap
	 */
	public static final String THAT = 
		"@THAT\n" +
		"D=M\n" +
		"@" + Replace.IDX + "\n" +
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
		"@" + Replace.IDX + "\n" +
		"D=A\n" +
		PUSH;
	
	/**
	 * A static variable across files pushed onto the stack
	 */
	public static final String STATIC = 
		"@" + Replace.FILENAME + "." + Replace.IDX + "\n" +
		"D=M\n" +
		PUSH;
	
	/**
	 * Temporary register r5
	 */
	public static final String TEMP = 
		"@R5\n" +
		"D=A\n" +
		"@" + Replace.IDX + "\n" +
		"A=D+A\n" +
		"D=M\n" +
		PUSH;
	
	public Push(String name) {
		super(name, "");
	}
	
	public void parameters(String[] commands) throws ParseException {
		super.stringWithNumber(commands);
	}
	
	public String getAsm() throws ParseException {
		switch (commands[0].toLowerCase()) {
		case "local":
			return LOCAL.replaceAll(Replace.IDX, commands[1]);
		case "argument":
			return ARGUMENT.replaceAll(Replace.IDX, commands[1]);
		case "this":
			return THIS.replaceAll(Replace.IDX, commands[1]);
		case "that":
			return THAT.replaceAll(Replace.IDX, commands[1]);
		case "pointer":
			return (commands[1].equals("0") ? POINTER_THIS : POINTER_THAT);
		case "constant":
			return CONSTANT.replaceAll(Replace.IDX, commands[1]);
		case "static":
			return STATIC.replaceAll(Replace.IDX, commands[1]).replaceAll(Replace.FILENAME, Translator.getFileName());
		case "temp":
			return TEMP.replace(Replace.IDX, commands[1]);
		default: 
			throw new ParseException(commands[1] + " is not a valid push command!", line);
		}
	}
}
