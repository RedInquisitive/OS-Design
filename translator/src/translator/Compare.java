package translator;

import main.Replace;

public class Compare extends Command {
	
	/**
	 * Prefix to a comparison operator.
	 * Note: UNIQUE must be replaced with a unique number
	 * Note: COMP must be replaced with the name of the comparison.
	 */
	public static final String COMPARE_PRE = 
		Command.BINARY +
		"D=M-D\n" +
		"@" + Replace.COMPARE + ".true." + Replace.UNIQUE + "\n";

	/**
	 * Suffix to a comparison operator. Check "see also" for notes.
	 * @see COMPARE_PRE
	 */
	public static final String COMPARE_POST = 
		"@SP\n" +
		"A=M-1\n" +
		"M=0\n" +
		"@" + Replace.COMPARE + ".after." + Replace.UNIQUE + "\n" +
		"0;JMP\n" +
		"(" + Replace.COMPARE + ".true." + Replace.UNIQUE + ")\n" +
		"@SP\n" +
		"A=M-1\n" +
		"M=-1\n" +
		"(" + Replace.COMPARE + ".after." + Replace.UNIQUE + ")\n";
	
	private int unique = 0;
	
	public Compare(String name, String asm) {
		super(name, (COMPARE_PRE + asm + COMPARE_POST).replace(Replace.COMPARE, name));
	}
	
	public String getAsm() {
		return asm.replace(Replace.UNIQUE, "" + unique++);
	}
}
