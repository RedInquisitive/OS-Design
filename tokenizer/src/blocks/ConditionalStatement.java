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

	/**
	 * checks the syntax of a conditional statement, like while or if
	 */
	public void run(Token header) throws ParseException {
		Token next;
		
		//Requires a while keyword
		append(header);
		if(!(verifyWhile(header) || verifyIf(header)))
			throw new ParseException("Expected a conditional!", Reader.getCount());
		
		//open '('
		append(next = Main.read.next());
		if(next.getSymbol() != Symbol.LPER) 
			throw new ParseException("Expected an open perenthesis in conditional!", Reader.getCount());
		
		//get expression
		next = Main.read.next();
		Element expression = decend(Express.EXPRESSION);
		new Expression(expression).run(next);
		root.appendChild(expression);
		
		//close ')'
		append(next = Main.read.next());
		if(next.getSymbol() != Symbol.RPER) 
			throw new ParseException("Expected a closed perenthesis to end conditional!", Reader.getCount());
		
		//obtain body
		body();
		
		//check for else
		next = Main.read.next();
		if(verifyIf(header) && next.getKeyword() == Keyword.ELSE) {
			append(next);
			body();
			return;
		}
		
		//retract closing }
		Main.read.abort();
	}
	
	private void body() throws ParseException {
		
		//open '{'
		Token next;
		append(next = Main.read.next());
		if(next.getSymbol() != Symbol.LBRACE) 
			throw new ParseException("Expected open { for conditional body!", Reader.getCount());
		
		//Descend
		next = Main.read.next();
		Element state = decend(Statements.STATEMENTS);
		new Statement(state).run(next);
		root.appendChild(state);
		
		//close '}'
		append(next = Main.read.next());
		if(next.getSymbol() != Symbol.RBRACE) 
			throw new ParseException("Expected closed } for conditional body!", Reader.getCount());
	}

	public static boolean verifyWhile(Token header) {
		return header.getKeyword() == Keyword.WHILE;
	}
	
	public static boolean verifyIf(Token header) {
		return header.getKeyword() == Keyword.IF;
	}
}
