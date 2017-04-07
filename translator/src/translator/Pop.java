package translator;

import java.text.ParseException;

import main.Replace;
import main.Translator;

public class Pop extends Command {
	
	/**
	 * Common assembly for a pop command.
	 */
	private static final String POP =
		"@R13\n" +
		"M=D\n" +
		"@SP\n" +
		"AM=M-1\n" +
		"D=M\n" +
		"@R13\n" +
		"A=M\n" +
		"M=D\n";
	
	/**
	 * @see Push#LOCAL
	 */
	public static final String LOCAL =
		"@LCL\n" +
		"D=M\n" +
		"@" + Replace.IDX + "\n" +
		"D=D+A\n" +
		POP;
	
	/**
	 * @see Push#ARGUMENT
	 */
	public static final String ARGUMENT = 
		"@ARG\n" +
		"D=M\n" +
		"@" + Replace.IDX + "\n" +
		"D=D+A\n" +
		POP;
	
	/**
	 * @see Push#THIS
	 */
	public static final String THIS =
		"@THIS\n" +
		"D=M\n" +
		"@" + Replace.IDX + "\n" +
		"D=D+A\n" +
		POP;
	
	/**
	 * @see Push#THAT
	 */
	public static final String THAT =
		"@THAT\n" +
		"D=M\n" +
		"@" + Replace.IDX + "\n" +
		"D=D+A\n" +
		POP;
	
	/**
	 * @see Push#POINTER_THIS
	 */
	public static final String POINTER_THIS =
		"@THIS\n" +
		"D=A\n" +
		POP;
	
	/**
	 * @see Push#POINTER_THAT
	 */
	public static final String POINTER_THAT = 
		"@THAT\n" +
		"D=A\n" +
		POP;
	
	/**
	 * @see Push#STATIC
	 */
	public static final String STATIC =
		"@" + Replace.FILENAME + "." + Replace.IDX + "\n" +
		"D=A\n" +
		POP;
	
	/**
	 * @see Push#TEMP
	 */
	public static final String TEMP = 
		"@R5\n" +
		"D=A\n" +
		"@" + Replace.IDX + "\n" +
		"D=D+A\n" +
		POP;

	public Pop(String name) {
		super(name, "");
	}
	
	public void setParameters(String[] commands) throws ParseException {
		super.stringWithNumber(commands);
		super.setParameters(commands);
	}
	
	public String getAsm() throws ParseException {
		switch (commands[1].toLowerCase()) {
		case "local":
			return LOCAL.replace(Replace.IDX, commands[2]);
		case "argument":
			return ARGUMENT.replace(Replace.IDX, commands[2]);
		case "this":
			return THIS.replace(Replace.IDX, commands[2]);
		case "that":
			return THAT.replace(Replace.IDX, commands[2]);
		case "pointer":
			return (commands[2].equals("0") ? POINTER_THIS : POINTER_THAT);
		case "static":
			return STATIC.replace(Replace.IDX, commands[2]).replace(Replace.FILENAME, Translator.getFileName());
		case "temp":
			return TEMP.replace(Replace.IDX, commands[2]);
		default: 
			throw new ParseException(commands[2] + " is not a valid pop command!", line);
		}
	}
}
