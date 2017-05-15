package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Express;
import structure.Statements;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class ConditionalStatement extends Base {

	protected ConditionalStatement(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		Token next;
		
		//Requires a while keyword
		if(!(verifyWhile(header) || verifyIf(header)))
			throw new ParseException("Expected a while loop!", Reader.getCount());
		append(header);
		
		next = Main.read.next();
		if(next.getSymbol() != Symbol.LPER) 
			throw new ParseException("Expected an open perenthesis!", Reader.getCount());
		append(next);
		
		//get expression
		next = Main.read.next();
		Element expressions = decend(Express.EXPRESSION_LIST);
		new Expression(expression).run(next);
		root.appendChild(expressions);
		
		next = Main.read.next();
		if(next.getSymbol() != Symbol.LPER) 
			throw new ParseException("Expected an open perenthesis!", Reader.getCount());
		append(next);
	}

	public static boolean verifyWhile(Token header) {
		return header.getKeyword() == Keyword.WHILE;
	}
	
	public static boolean verifyIf(Token header) {
		return header.getKeyword() == Keyword.IF;
	}
}
