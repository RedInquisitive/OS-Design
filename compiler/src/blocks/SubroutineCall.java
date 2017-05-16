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

	public void run(Token header) throws ParseException {
		Token next;
		
		if(!verify(header))
			throw new ParseException("Expected a subroutine name or class!", Reader.getCount());
		append(header);
		
		//check for dot access
		next = Main.read.next();
		if(next.getSymbol() == Symbol.DOT) {
			append(next);
			
			//get real subroutine
			next = Main.read.next();
			if(next.getLexical() != Lexical.IDENTIFIER) 
				throw new ParseException("Expected a subroutine name!", Reader.getCount());
			append(next);
			
			//prepare for parenthesis
			next = Main.read.next();
		}
		
		
		//Parenthesis to start list
		if(next.getSymbol() != Symbol.LPER)
			throw new ParseException("Expected a parenthesis to start expression list!", Reader.getCount());
		append(next);
		
		//Get the expressions list
		next = Main.read.next();
		Element expressions = decend(Express.EXPRESSION_LIST);
		new ExpressionList(expressions).run(next);
		root.appendChild(expressions);
		
		//close parenthesis
		next = Main.read.next();
		if(next.getSymbol() != Symbol.RPER)
			throw new ParseException("Expected a closing parenthesis to end expression list", Reader.getCount());
		append(next);
	}

	public static boolean verify(Token header) {
		return header.getLexical() == Lexical.IDENTIFIER;
	}
}
