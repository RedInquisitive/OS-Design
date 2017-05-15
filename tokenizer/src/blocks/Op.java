package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Express;
import symbols.Symbol;
import tokenizer.Base;

public class Op extends Base{
	protected Op(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		if(!verify(header))
			throw new ParseException("Expected an operator!", Reader.getCount());
		append(header, Express.OP);
	}
	
	public static boolean verify(Token header) {
		return  header.getSymbol() == Symbol.PLUS ||
				header.getSymbol() == Symbol.MINUS ||
				header.getSymbol() == Symbol.MULT ||
				header.getSymbol() == Symbol.DIV ||
				header.getSymbol() == Symbol.AND ||
				header.getSymbol() == Symbol.OR ||
				header.getSymbol() == Symbol.LPOINT ||
				header.getSymbol() == Symbol.RPOINT ||
				header.getSymbol() == Symbol.EQ;
	}
}
