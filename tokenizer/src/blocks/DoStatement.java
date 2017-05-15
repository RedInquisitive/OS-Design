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

	public void run(Token header) throws ParseException {
		Token next;
		
		//Requires a while keyword
		if(!verify(header))
			throw new ParseException("Expected do!", Reader.getCount());
		append(header);
		
		//get subroutine call
		next = Main.read.next();
		new SubroutineCall(root).run(next);
		
		next = Main.read.next();
		if(next.getSymbol() != Symbol.SEMI) 
			throw new ParseException("Expected a semicolon to end do!", Reader.getCount());
		append(next);
	}

	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.DO;
	}
}
