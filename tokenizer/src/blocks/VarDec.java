package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Lexical;
import structure.Program;
import symbols.Keyword;
import symbols.Symbol;
import tokenizer.Base;
import tokenizer.Main;

public class VarDec extends Base{

	private Program construct;
	
	protected VarDec(Element root, Program construct) {
		super(root);
		this.construct = construct;
	}

	public void run(Token header) throws ParseException {
		Token next;
		
		//Expect either static or field for a class variable
		if(construct == Program.VAR_DEC) {
			if(!verifyVar(header))
				throw new ParseException("Expected static or field for a class variable name!", Reader.getCount());
		} else {
			if(!verifyClass(header))
				throw new ParseException("Expected keyword var for a local variable name!", Reader.getCount());
		}
		append(header);
		
		//Obtain the type of the variable
		new Type(root).run(Main.read.next());
		
		//require internal
		while(true) {
			
			//Require a variable name
			next = Main.read.next();
			if(next.getLexical() != Lexical.IDENTIFIER) 
				throw new ParseException("Expected an identifier for this variable name!", Reader.getCount());
			append(next, Program.VAR_NAME);
			
			//Require either a comma (and continue the loop)
			next = Main.read.next();
			if(next.getSymbol() == Symbol.COMMA) {
				append(next);
				continue;
			}
			
			//or break on a semicolon
			if(next.getSymbol() == Symbol.SEMI) {
				append(next);
				break;
			}
			
			//if it is neither, die
			throw new ParseException("Expected a semicolon to end variable declaration!", Reader.getCount());
		}
	}

	public static boolean verifyClass(Token header) {
		return header.getKeyword() == Keyword.STATIC || 
			   header.getKeyword() == Keyword.FIELD;
	}
	
	public static boolean verifyVar(Token header) {
		return header.getKeyword() == Keyword.VAR;
	}
}
