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
			if(!Term.verify(next))
				break;
			
			Element expression = decend(Express.EXPRESSION);
			new Expression(expression).run(next);
			root.appendChild(expression);
			
			next = Main.read.next();
			if(next.getSymbol() != Symbol.COMMA)
				break;
			append(next);
			
			next = Main.read.next();
		}
		Main.read.abort();
	}
}
