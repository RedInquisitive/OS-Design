package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Express;
import structure.Lexical;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class Term extends Base {

	public Term(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		Token next;
		
		if(!verify(header))
			throw new ParseException("Expected a term!", Reader.getCount());
		
		//Generic terms
		if( header.getLexical() == Lexical.INTEGER ||
			header.getLexical() == Lexical.STRING ||
			header.getLexical() == Lexical.IDENTIFIER ||
			header.getKeyword() == Keyword.TRUE ||
			header.getKeyword() == Keyword.FALSE ||
			header.getKeyword() == Keyword.NULL ||
			header.getKeyword() == Keyword.THIS) {
			append(header);
			
			//array
			next = Main.read.next();
			if(next.getSymbol() == Symbol.LBRAK) {
				append(next);
				
				//get expression
				Element expression = decend(Express.EXPRESSION);
				new Expression(expression).run(Main.read.next());
				root.appendChild(expression);
				
				//check for ending bracket
				append(next = Main.read.next());
				if(next.getSymbol() != Symbol.RBRAK)
					throw new ParseException("Expected right bracket to end array address!", Reader.getCount());
				
				//no more array
				return;
			}
			
			//it was a subroutine
			if(next.getSymbol() == Symbol.LPER || next.getSymbol() == Symbol.DOT) {
				Main.read.abort();
				new SubroutineCall(root).run(header);
				return;
			}
			
			//reread bracket
			Main.read.abort();
			return;
		}
		
		//expression
		if(header.getSymbol() == Symbol.LPER) {
			append(header);
			
			//another expression!
			Element expression = decend(Express.EXPRESSION);
			new Expression(expression).run(Main.read.next());
			root.appendChild(expression);
			
			//check for ending parenthesis!
			append(next = Main.read.next());
			if(next.getSymbol() != Symbol.RPER)
				throw new ParseException("Expected right parenthesis to end expression!", Reader.getCount());
			
			//no more expression
			return;
		}
		
		//unary
		if(header.getSymbol() == Symbol.MINUS || header.getSymbol() == Symbol.NOT) {
			append(header);
			
			//Recursion!!!
			Element term = decend(Express.TERM);
			new Term(term).run(Main.read.next());
			root.appendChild(term);
			
			//no more unary operation
			return;
		}
		
		//how did we get here?
		throw new ParseException("Invalid term!", Reader.getCount());
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
