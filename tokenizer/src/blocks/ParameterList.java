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

			//get type in parameter list
			//might not exist.
			if(!Type.verify(next)) break;
			new Type(root).run(next);
			
			//now get variable name
			append(next = Main.read.next(), Program.VAR_NAME);
			if(next.getLexical() != Lexical.IDENTIFIER)
				throw new ParseException("Expected a variable name in parameter list!", Reader.getCount());

			//Comma separator
			append(next = Main.read.next());
			if(next.getSymbol() != Symbol.COMMA) break;
			
			//prepare for next type
			next = Main.read.next();
		}
		
		//abort close parenthesis or other symbol.
		Main.read.abort();
	}
}
