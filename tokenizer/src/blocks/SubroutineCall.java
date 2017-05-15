package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Token;
import tokenizer.Base;

public class SubroutineCall extends Base {

	public SubroutineCall(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
	}

	public static boolean verify(Token header) {
		return false;
	}
}
