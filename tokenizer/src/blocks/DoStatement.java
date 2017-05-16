package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class DoStatement extends Base {

	protected DoStatement(Element root) {
		super(root);
	}

	/**
	 * checks the syntax of a do statement
	 */
	public void run(Token header) throws ParseException {
		Token next;
		
		//Requires a while keyword
		append(header);
		if(!verify(header))
			throw new ParseException("Expected do!", Reader.getCount());
		
		//get subroutine call
		new SubroutineCall(root).run(Main.read.next());
		
		//end;
		append(next = Main.read.next());
		if(next.getSymbol() != Symbol.SEMI) 
			throw new ParseException("Expected a semicolon to end do!", Reader.getCount());
	}

	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.DO;
	}
}
