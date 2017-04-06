package translator;

import java.text.ParseException;

import main.Replace;
import main.Translator;

public class Goto extends Command {
	public Goto(String name, String asm) {
		super(name, asm);
	}
	
	public void parameters(String[] commands) throws ParseException {
		if(commands.length != 1) throw new ParseException("A goto command must have a label name to go to!", line);
		super.parameters(commands);
	}
	
	public String getAsm() {
		return asm.replaceAll(Replace.FUNKYLABEL, Translator.getFunctionName() + "$" + commands[0]);
	}
}
