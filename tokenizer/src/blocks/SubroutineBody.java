package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Program;
import structure.Statements;
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
		append(header);
		if(!verify(header))
			throw new ParseException("Expected a open brace!", Reader.getCount());

		
		//Read variable headers
		next = Main.read.next();
		while(true) {
			if(!VarDec.verifyVar(next)) break;
			Element varDec = decend(Program.VAR_DEC);
			new VarDec(varDec, Program.VAR_DEC).run(next);
			root.appendChild(varDec);
			next = Main.read.next();
		}
		
		//read statements
		Element statements = decend(Statements.STATEMENTS);
		new Statement(statements).run(next);
		root.appendChild(statements);
			
		//check right brace
		append(next = Main.read.next());
		if(next.getSymbol() != Symbol.RBRACE) 
			throw new ParseException("Expected a closing brace to subroutine body!", Reader.getCount());
	}
	
	public static boolean verify(Token header) {
		return header.getSymbol() == Symbol.LBRACE;
	}
}
