package translator;

import java.text.ParseException;

import main.Translator;

public class Label extends Command {
	public Label(String name) {
		super(name, "");
	}
	
	public void setParameters(String[] commands) throws ParseException {
		if(commands.length != 2) throw new ParseException("A label command must have a label name!", line);
		super.setParameters(commands);
	}
	
	public String getAsm() {
		return "(" + Translator.getFunctionName() + "$" + commands[1] + ")\n";
	}
}
