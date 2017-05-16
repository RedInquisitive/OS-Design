package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Lexical;
import structure.Program;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class ParameterList extends Base {

	protected ParameterList(Element root) {
		super(root);
	}

	/**
	 * Reads tokens until either invalid syntax (exception) or
	 * missing comma. In the case of the missing comma, the reader
	 * will abort.
	 */
	public void run(Token header) throws ParseException {
		Token next = header;
		
		while(true) {

			if(!Type.verify(next)) 
				break;
			new Type(root).run(next);
			
			next = Main.read.next();
			if(next.getLexical() != Lexical.IDENTIFIER)
				throw new ParseException("Expected a variable name in parameter list!", Reader.getCount());
			append(next, Program.VAR_NAME);
			
			next = Main.read.next();
			if(next.getSymbol() != Symbol.COMMA)
				break;
			append(next);
			
			next = Main.read.next();
		}
		Main.read.abort();
	}
}
