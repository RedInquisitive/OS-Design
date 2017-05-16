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

	public void run(Token header) throws ParseException {
		Token next;
		
		//Requires a while keyword
		if(!verify(header))
			throw new ParseException("Expected let!", Reader.getCount());
		append(header);
		
		//get subroutine call
		next = Main.read.next();
		if(next.getLexical() != Lexical.IDENTIFIER)
			throw new ParseException("Expected a variable name!", Reader.getCount());
		append(next, Program.VAR_NAME);
		
		next = Main.read.next();
		if(next.getSymbol() == Symbol.LBRAK) {
			append(next);
			
			next = Main.read.next();
			Element expression = decend(Express.EXPRESSION);
			new Expression(expression).run(next);
			root.appendChild(expression);
			
			next = Main.read.next();
			if(next.getSymbol() != Symbol.RBRAK)
				throw new ParseException("Expected a close bracket at end of let statement expression!", Reader.getCount());
			append(next);
			
			next = Main.read.next();
		}
		
		if(next.getSymbol() != Symbol.EQ)
			throw new ParseException("Expected an = sign in let statement!", Reader.getCount());
		append(next);
		
		//get expression
		next = Main.read.next();
		Element expression = decend(Express.EXPRESSION);
		new Expression(expression).run(next);
		root.appendChild(expression);
		
		next = Main.read.next();
		if(next.getSymbol() != Symbol.SEMI) 
			throw new ParseException("Expected a semicolon to end let!", Reader.getCount());
		append(next);
	}

	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.LET;
	}
}
