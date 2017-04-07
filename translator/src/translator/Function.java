package translator;

import java.text.ParseException;

import main.Translator;

public class Function extends Command {
	public Function(String name) {
		super(name, "");
	}
	
	public void setParameters(String[] commands) throws ParseException {
		super.stringWithNumber(commands);
		super.setParameters(commands);
		Translator.setFunctionName(commands[1]);
	}
	
	public String getAsm() {
		StringBuilder s = new StringBuilder();
		s.append("("); s.append(commands[1]); s.append(")\n");
		s.append("@SP\n");
		s.append("A=M\n");
		int functions = Integer.parseInt(commands[2]);
		for (int i = 0; i < functions; i++) {
			s.append("M=0\n");
			s.append("A=A+1\n");
		}
		s.append("D=A\n");
		s.append("@SP\n");
		s.append("M=D\n");
		return s.toString();
		}
}
