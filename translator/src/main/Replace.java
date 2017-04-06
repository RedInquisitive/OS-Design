package main;

import translator.Goto;

/**
 * Various strings for replacements during runtime.
 */
public class Replace {
	/**
	 * Replaces a function with its actual name.
	 */
	public static final String FUNCTION = "REGEXFUNCTION";
	
	/**
	 * Replaces a label with the actual label.
	 */
	public static final String LABEL = "REGEXLABEL";
	
	/**
	 * Replaces a function AND a label with an actual function and label
	 * The replacement should be in the form FUNCTION + "$" + LABEL
	 * for compatibility with Goto.
	 * 
	 * @see Goto#getAsm()
	 */
	public static final String FUNKYLABEL = "REGEXFUNKYLABEL";
	
	/**
	 * During push and pop, this value is replaced with a numerical
	 * constant
	 */
	public static final String IDX = "REGEXIDX";
	
	/**
	 * During a compare, this value is replaced with the comparison name
	 */
	public static final String COMPARE = "REGEXCOMPARE";
	
	/**
	 * This value is replaced with a unique number identifying
	 * a comparison.
	 */
	public static final String UNIQUE = "REGEXUNIQUE";
	
	/**
	 * This will be replaced by a filename.
	 */
	public static final String FILENAME = "REGEXFILENAME";
}
