package main;

public enum Mode {
	/**
	 * Read from standard in, write to standard out.
	 * Easy as that.
	 */
	STANDARD,
	
	/**
	 * There appears to be a Sys.vm file, so we must deal with it.
	 */
	BOOTSTRAP,
	
	/**
	 * Write every file input to individual output files.
	 */
	INDIVIDUAL
}
