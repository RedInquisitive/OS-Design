package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Token;
import structure.Express;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class ExpressionList extends Base {

	protected ExpressionList(Element root) {
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
			
			//get term. If not present, end immediately
			if(!Term.verify(next)) break;
			
			//Descend into term
			Element expression = decend(Express.EXPRESSION);
			new Expression(expression).run(next);
			root.appendChild(expression);
			
			//append comma, if present
			append(next = Main.read.next());
			if(next.getSymbol() != Symbol.COMMA) break;
			
			next = Main.read.next();
		}
		
		//abort last symbol (likely a parenthesis or ; )
		Main.read.abort();
	}
}
