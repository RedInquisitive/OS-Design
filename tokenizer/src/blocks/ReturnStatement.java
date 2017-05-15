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

	public void run(Token header) throws ParseException {
		Token next;
		
		//for the return statement
		if(!verify(header))
			throw new ParseException("Expected a return statement!", Reader.getCount());
		append(header);
		
		//get zero or more expressions
		next = Main.read.next();
		if(Expression.verify(next)) {
			Element expression = decend(Express.EXPRESSION);
			new Expression(expression).run(next);
			root.appendChild(expression);
		}
		
		//check for semicolon
		next = Main.read.next();
		if(next.getSymbol() != Symbol.SEMI)
			throw new ParseException("Expected a semi colon at end of return statement!", Reader.getCount());
		append(next);
		
	}

	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.RETURN;
	}
}
