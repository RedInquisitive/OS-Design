package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Express;
import structure.Lexical;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class SubroutineCall extends Base {

	public SubroutineCall(Element root) {
		super(root);
	}

	/**
	 * Call for a subroutine. Will auto parse parameters in the call.
	 */
	public void run(Token header) throws ParseException {
		Token next;
		
		append(header);
		if(!verify(header))
			throw new ParseException("Expected a subroutine name or class!", Reader.getCount());

		//check for dot access
		next = Main.read.next();
		if(next.getSymbol() == Symbol.DOT) {
			append(next);
			
			//get real subroutine
			append(next = Main.read.next());
			if(next.getLexical() != Lexical.IDENTIFIER) 
				throw new ParseException("Expected a subroutine name!", Reader.getCount());
			
			//prepare for parenthesis
			next = Main.read.next();
		}
		
		
		//Parenthesis to start list
		append(next);
		if(next.getSymbol() != Symbol.LPER)
			throw new ParseException("Expected a parenthesis to start expression list!", Reader.getCount());

		//Get the expressions list
		Element expressions = decend(Express.EXPRESSION_LIST);
		new ExpressionList(expressions).run(Main.read.next());
		root.appendChild(expressions);
		
		//close parenthesis
		append(next = Main.read.next());
		if(next.getSymbol() != Symbol.RPER)
			throw new ParseException("Expected a closing parenthesis to end expression list", Reader.getCount());
	}

	public static boolean verify(Token header) {
		return header.getLexical() == Lexical.IDENTIFIER;
	}
}
