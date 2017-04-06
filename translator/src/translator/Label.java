package translator;

import java.text.ParseException;

import main.Translator;

public class Label extends Command {
	public Label(String name) {
		super(name, "");
	}
	
	public void parameters(String[] commands) throws ParseException {
		if(commands.length != 1) throw new ParseException("A label command must have a label name!", line);
		super.parameters(commands);
	}
	
	public String getAsm() {
		return "(" + Translator.getFunctionName() + "$" + commands[0] + ")\n";
	}
}
