package main;

import java.io.InputStream;
import java.io.PrintStream;

public class BundledStream {
	
	public final InputStream in;
	public final PrintStream out;
	public final String name;
	public final String path;
	
	public BundledStream(InputStream input, PrintStream output, String name, String path) {
		this.in = input;
		this.out = output;
		this.name = name;
		this.path = path;
	}
}
