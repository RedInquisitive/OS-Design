package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Token;
import structure.Lexical;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;

public class Term extends Base {

	public Term(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		
	}

	public static boolean verify(Token header) {
		return  header.getLexical() == Lexical.INTEGER ||
				header.getLexical() == Lexical.STRING ||
				header.getLexical() == Lexical.IDENTIFIER ||
				header.getKeyword() == Keyword.TRUE ||
				header.getKeyword() == Keyword.FALSE ||
				header.getKeyword() == Keyword.NULL ||
				header.getKeyword() == Keyword.THIS ||
				header.getSymbol() == Symbol.LPER ||
				header.getSymbol() == Symbol.MINUS ||
				header.getSymbol() == Symbol.NOT ||
				SubroutineCall.verify(header);
	}
}
