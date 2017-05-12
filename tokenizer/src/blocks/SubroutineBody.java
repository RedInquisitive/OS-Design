package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Lexical;
import structure.Program;
import structure.Statements;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class SubroutineBody extends Base {

	protected SubroutineBody(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		Token next;
		
		//Requires a left brace for opening a body
		if(!verify(header))
			throw new ParseException("Expected a open brace!", Reader.getCount());
		append(header);
		
		//Read variable headers
		next = Main.read.next();
		while(true) {
			if(!VarDec.verifyVar(next)) break;
			Element varDec = decend(Program.VAR_DEC);
			new VarDec(varDec, Program.VAR_DEC).run(next);
			next = Main.read.next();
		}
		
		//read statements
		/**
		while(true) {
			if(!Statement.verify(next)) break;
			Element statement = decend(Statements.STATEMENTS);
			new Statement(statement).run(next);
			next = Main.read.next();
		}
		*/
		
		if(next.getSymbol() != Symbol.RBRACE) 
			throw new ParseException("Expected a closing brace to subroutine body!", Reader.getCount());
		append(next);
	}
	
	public static boolean verify(Token header) {
		return header.getSymbol() == Symbol.LBRACE;
	}
}
