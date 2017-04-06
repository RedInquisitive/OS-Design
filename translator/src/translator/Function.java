package translator;

public class Function extends Command {
	public Function(String name) {
		super(name, "");
	}
	
	  private String FUNCTION(String f, String k) {
		    String s =
		      "(" + f + ")\n" +
		      "@SP\n" +
		      "A=M\n";
		    int kk = Integer.parseInt(k);
		    for (int i = 0; i < kk; i += 1) {
		      s +=
		        "M=0\n" +
		        "A=A+1\n";
		    }
		    return s +
		      "D=A\n" +
		      "@SP\n" +
		      "M=D\n";
		}
}
