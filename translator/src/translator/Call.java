package translator;

import java.text.ParseException;

import main.Replace;

public class Call extends Command {

	/**
	 * Call a function
	 */
	public static final String CALL = 
		// SP -> R13
		"@SP\n" +
		"D=M\n" +
		"@R13\n" +
		"M=D\n" +
		// @RET -> *SP
		"@RET." + Replace.UNIQUE + "\n" +
		"D=A\n" +
		"@SP\n" +
		"A=M\n" +
		"M=D\n" +
		// SP++
		"@SP\n" +
		"M=M+1\n" +
		// LCL -> *SP
		"@LCL\n" +
		"D=M\n" +
		"@SP\n" +
		"A=M\n" +
		"M=D\n" +
		// SP++
		"@SP\n" +
		"M=M+1\n" +
		// ARG -> *SP
		"@ARG\n" +
		"D=M\n" +
		"@SP\n" +
		"A=M\n" +
		"M=D\n" +
		// SP++
		"@SP\n" +
		"M=M+1\n" +
		// THIS -> *SP
		"@THIS\n" +
		"D=M\n" +
		"@SP\n" +
		"A=M\n" +
		"M=D\n" +
		// SP++
		"@SP\n" +
		"M=M+1\n" +
		// THAT -> *SP
		"@THAT\n" +
		"D=M\n" +
		"@SP\n" +
		"A=M\n" +
		"M=D\n" +
		// SP++
		"@SP\n" +
		"M=M+1\n" +
		// R13 - n -> ARG
		"@R13\n" +
		"D=M\n" +
		"@" + Replace.IDX + "\n" +
		"D=D-A\n" +
		"@ARG\n" +
		"M=D\n" +
		// SP -> LCL
		"@SP\n" +
		"D=M\n" +
		"@LCL\n" +
		"M=D\n" +
		"@" + Replace.FUNCTION  + "\n" +
		"0;JMP\n" +
		"(RET." + Replace.UNIQUE + ")\n";
	
	private int unique = 0;
	
	public Call(String name) {
		super(name, "");
	}
	
	public void setParameters(String[] commands) throws ParseException {
		super.stringWithNumber(commands);
		super.setParameters(commands);
	}
	
	public String getAsm() {
		return CALL
			.replaceAll(Replace.UNIQUE, "" + unique++)
			.replaceAll(Replace.IDX, commands[2])
			.replaceAll(Replace.FUNCTION, commands[1]);
	}
}
