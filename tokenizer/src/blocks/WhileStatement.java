package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Token;
import symbols.Keyword;
import tokenizer.Base;

public class WhileStatement extends Base {

	protected WhileStatement(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		
	}

	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.WHILE;
	}
}
