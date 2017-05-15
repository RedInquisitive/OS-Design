package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Lexical;
import structure.Program;
import symbols.Keyword;
import tokenizer.Base;

public class Type extends Base {

	protected Type(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		if(!verify(header))
			throw new ParseException("Expected the keyword int, char, boolean, or identifier!", Reader.getCount());

		//Require built in types or a class name
		if(header.getLexical() == Lexical.KEYWORD) {
			append(header);
		} else {
			append(header, Program.CLASS_NAME);
		}
	}
	
	public static boolean verify(Token header) {
		return	header.getKeyword() == Keyword.INT || 
				header.getKeyword() == Keyword.CHAR || 
				header.getKeyword() == Keyword.BOOLEAN ||
				header.getLexical() == Lexical.IDENTIFIER;
	}
}
