package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import symbols.Keyword;
import tokenizer.Base;

public class ReturnStatement extends Base {

	protected ReturnStatement(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		Token next;
		
		//for the return statement
		if(!verify(header))
			throw new ParseException("Expected a return statement!", Reader.getCount());
		
		
	}

	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.RETURN;
	}
}
