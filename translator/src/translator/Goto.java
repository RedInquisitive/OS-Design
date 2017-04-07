package translator;

import java.text.ParseException;

import main.Replace;
import main.Translator;

public class Goto extends Command {
	public Goto(String name, String asm) {
		super(name, asm);
	}
	
	public void setParameters(String[] commands) throws ParseException {
		if(commands.length != 2) throw new ParseException("A goto command must have a label name to go to!", line);
		super.setParameters(commands);
	}
	
	public String getAsm() {
		return asm.replace(Replace.FUNKYLABEL, Translator.getFunctionName() + "$" + commands[1]);
	}
}
