package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Lexical;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class ClassVarDec extends Base{

	protected ClassVarDec(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		Token next;
		if(!verify(header))
			throw new ParseException(
					"Expected static or field for a class variable name!", Reader.getCount());
		append(header);
		
		new Type(root).run(Main.read.next());
		
		while(true) {
			next = Main.read.next();
			if(next.getLexical() != Lexical.IDENTIFIER) 
				throw new ParseException("Expected an identifier for class variable name!", Reader.getCount());
			append(next);
			
			next = Main.read.next();
			if(next.getSymbol() == Symbol.COMMA) {
				append(next);
				continue;
			}
			if(next.getSymbol() == Symbol.SEMI) {
				append(next);
				break;
			}
			
			throw new ParseException("Expected a semicolon to end class variable declaration!", Reader.getCount());
		}
	}

	public static boolean verify(Token header) {
		try {
			return 
					header.getKeyword() == Keyword.STATIC || 
					header.getKeyword() == Keyword.FIELD;
		} catch (ParseException e) {
			return false;
		}
	}
}
