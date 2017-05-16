package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Express;
import structure.Lexical;
import structure.Program;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class LetStatement extends Base {

	protected LetStatement(Element root) {
		super(root);
	}

	/**
	 * Compiles a let statement. Might consist of setting a variable
	 * or calling a function.
	 */
	public void run(Token header) throws ParseException {
		Token next;
		
		//Requires a while keyword
		append(header);
		if(!verify(header))
			throw new ParseException("Expected let!", Reader.getCount());

		
		//get variable name
		append(next = Main.read.next(), Program.VAR_NAME);
		if(next.getLexical() != Lexical.IDENTIFIER)
			throw new ParseException("Expected a variable name!", Reader.getCount());
		
		//check for array
		next = Main.read.next();
		if(next.getSymbol() == Symbol.LBRAK) {
			append(next);
			
			//Descend into array
			Element expression = decend(Express.EXPRESSION);
			new Expression(expression).run(next = Main.read.next());
			root.appendChild(expression);
			
			//array close
			append(next = Main.read.next());
			if(next.getSymbol() != Symbol.RBRAK)
				throw new ParseException("Expected a close bracket at end of let statement expression!", Reader.getCount());
			
			//prepare for =
			next = Main.read.next();
		}
		
		// = symbol
		append(next);
		if(next.getSymbol() != Symbol.EQ)
			throw new ParseException("Expected an = sign in let statement!", Reader.getCount());
		
		//get expression
		Element expression = decend(Express.EXPRESSION);
		new Expression(expression).run(Main.read.next());
		root.appendChild(expression);
		
		//end statement
		append(next = Main.read.next());
		if(next.getSymbol() != Symbol.SEMI) 
			throw new ParseException("Expected a semicolon to end let!", Reader.getCount());
	}

	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.LET;
	}
}
