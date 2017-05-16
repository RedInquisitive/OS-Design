package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Express;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class ReturnStatement extends Base {

	protected ReturnStatement(Element root) {
		super(root);
	}

	/**
	 * Parse a return statement. May contain zero or one 
	 * expressions at the end
	 */
	public void run(Token header) throws ParseException {
		Token next;
		
		//for the return statement
		append(header);
		if(!verify(header))
			throw new ParseException("Expected a return statement!", Reader.getCount());
		
		//get zero or more expressions
		next = Main.read.next();
		if(Term.verify(next)) {
			Element expression = decend(Express.EXPRESSION);
			new Expression(expression).run(next);
			root.appendChild(expression);
			next = Main.read.next();
		}
		
		//check for semicolon
		append(next);
		if(next.getSymbol() != Symbol.SEMI)
			throw new ParseException("Expected a semi colon at end of return statement!", Reader.getCount());
	}

	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.RETURN;
	}
}
