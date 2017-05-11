package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class SubroutineDec extends Base {

	protected SubroutineDec(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		Token next;
		if(!verify(header))
			throw new ParseException(
					"Expected a constructor, method, or function for a subroutine!", Reader.getCount());
		append(header);
		
		next = Main.read.next();
		if(next.getKeyword() == Keyword.VOID) {
			append(next);
		} else if(Type.verify(next)) {
			new Type(root).run(next);
		} else {
			throw new ParseException("A subroutine requires a type!", Reader.getCount());
		}
		
		next = Main.read.next();
		if(next.getSymbol() != Symbol.LPER)
			throw new ParseException("Expected a perenthesis for a parameter list!", Reader.getCount());
		append(next);
	}
	
	public static boolean verify(Token header) {
		try {
			return 
					header.getKeyword() == Keyword.CONSTRUCTOR || 
					header.getKeyword() == Keyword.FUNCTION || 
					header.getKeyword() == Keyword.METHOD;
		} catch (ParseException e) {
			return false;
		}
	}
}
