package translator;

import java.text.ParseException;

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
		"@IDX\n" +
		"D=D+A\n" +
		POP;
	
	/**
	 * @see Push#ARGUMENT
	 */
	public static final String ARGUMENT = 
		"@ARG\n" +
		"D=M\n" +
		"@IDX\n" +
		"D=D+A\n" +
		POP;
	
	/**
	 * @see Push#THIS
	 */
	public static final String THIS =
		"@THIS\n" +
		"D=M\n" +
		"@IDX\n" +
		"D=D+A\n" +
		POP;
	
	/**
	 * @see Push#THAT
	 */
	public static final String THAT =
		"@THAT\n" +
		"D=M\n" +
		"@IDX\n" +
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
		"@FILENAME.IDX\n" +
		"D=A\n" +
		POP;
	
	/**
	 * @see Push#TEMP
	 */
	public static final String TEMP = 
		"@R5\n" +
		"D=A\n" +
		"@IDX\n" +
		"D=D+A\n" +
		POP;

	public Pop(String name) {
		super(name, "");
	}
	
	public void parameters(String[] commands) throws ParseException {
		if(commands.length != 2) {
			throw new ParseException("You need 2 arguments for a pop command!", line);
		}
		
		try {
			Integer.parseInt(commands[1]);
		} catch (NumberFormatException e) {
			throw new ParseException("The second parameter of a pop command needs to be a number!", line);
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
		case "static":
			asm = STATIC.replaceAll("IDX", commands[1]).replaceAll("FILENAME", Translator.getFileName());
			break;
		case "temp":
			asm = TEMP.replace("IDX", commands[1]);
			break;
		default: 
			throw new ParseException(commands[1] + " is not a valid pop command!", line);
		}
	}
}
