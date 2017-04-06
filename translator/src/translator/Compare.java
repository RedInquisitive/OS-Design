package translator;

public class Compare extends Command {
	
	/**
	 * Prefix to a comparison operator.
	 * Note: UNIQUE must be replaced with a unique number
	 * Note: COMP must be replaced with the name of the comparison.
	 */
	public static final String COMPARE_PRE = 
		Command.BINARY +
		"D=M-D\n" +
		"@COMP.true.UNIQUE\n";

	/**
	 * Suffix to a comparison operator. Check "see also" for notes.
	 * @see COMPARE_PRE
	 */
	public static final String COMPARE_POST = 
		"@SP\n" +
		"A=M-1\n" +
		"M=0\n" +
		"@COMP.after.UNIQUE\n" +
		"0;JMP\n" +
		"(COMP.true.UNIQUE)\n" +
		"@SP\n" +
		"A=M-1\n" +
		"M=-1\n" +
		"(COMP.after.UNIQUE)\n";
	
	private int unique = 0;
	
	public Compare(String name, String asm) {
		super(name, (COMPARE_PRE + asm + COMPARE_POST).replaceAll("COMP", name));
	}
	
	public String getAsm() {
		return asm.replaceAll("UNIQUE", "" + unique++);
	}
}
