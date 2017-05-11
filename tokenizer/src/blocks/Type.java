package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Lexical;
import symbols.Keyword;
import tokenizer.Base;

public class Type extends Base {

	protected Type(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		if(!verify(header))
			throw new ParseException("Expected the keyword int, char, boolean, or at least a plain-ol' identifier!", Reader.getCount());
		append(header);
	}
	
	public static boolean verify(Token header) {
		try {
			return	header.getKeyword() == Keyword.INT || 
					header.getKeyword() == Keyword.CHAR || 
					header.getKeyword() == Keyword.BOOLEAN;
		} catch (ParseException e) {
			try {
				return header.getLexical() == Lexical.IDENTIFIER;
			} catch (ParseException f) {
				return false;
			}
		}
	}
}
