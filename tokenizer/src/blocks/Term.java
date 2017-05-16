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
				next = Main.read.next();
				Element expression = decend(Express.EXPRESSION);
				new Expression(expression).run(next);
				root.appendChild(expression);
				
				//check for ending bracket
				next = Main.read.next();
				if(next.getSymbol() != Symbol.RBRAK)
					throw new ParseException("Expected right bracket to end array address!", Reader.getCount());
				append(next);
				return;
			}
			
			if(next.getSymbol() == Symbol.DOT) {
				append(next);
				
				//get real subroutine
				next = Main.read.next();
				if(next.getLexical() != Lexical.IDENTIFIER) 
					throw new ParseException("Expected a subroutine name!", Reader.getCount());
				append(next);
				
				//Parenthesis to start list
				next = Main.read.next();
				if(next.getSymbol() != Symbol.LPER)
					throw new ParseException("Expected a parenthesis to start expression list!", Reader.getCount());
				append(next);
				
				//Get the expressions list
				next = Main.read.next();
				Element expressions = decend(Express.EXPRESSION_LIST);
				new ExpressionList(expressions).run(next);
				root.appendChild(expressions);
				
				//close parenthesis
				next = Main.read.next();
				if(next.getSymbol() != Symbol.RPER)
					throw new ParseException("Expected a closing parenthesis to end expression list", Reader.getCount());
				append(next);
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
			next = Main.read.next();
			Element expression = decend(Express.EXPRESSION);
			new Expression(expression).run(next);
			root.appendChild(expression);
			
			//check for ending parenthesis!
			next = Main.read.next();
			if(next.getSymbol() != Symbol.RPER)
				throw new ParseException("Expected right parenthesis to end expression!", Reader.getCount());
			append(next);
			return;
		}
		
		//unary
		if(header.getSymbol() == Symbol.MINUS || header.getSymbol() == Symbol.NOT) {
			append(header);
			
			//Recursion!!!
			next = Main.read.next();
			Element term = decend(Express.TERM);
			new Term(term).run(next);
			root.appendChild(term);
			return;
		}
			
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
