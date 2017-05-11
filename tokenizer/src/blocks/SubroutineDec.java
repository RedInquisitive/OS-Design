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

public class SubroutineDec extends Base {

	protected SubroutineDec(Element root) {
		super(root);
	}

	public void run(Token header) throws ParseException {
		Token next;
		
		//Require either constructor, function, or method.
		if(!verify(header))
			throw new ParseException("Expected a constructor, method, or function for a subroutine!", Reader.getCount());
		append(header);
		
		//Require the keyword void, or a type.
		next = Main.read.next();
		if(next.getKeyword() == Keyword.VOID) {
			append(next);
		} else if(Type.verify(next)) {
			new Type(root).run(next);
		} else {
			throw new ParseException("A subroutine requires a type!", Reader.getCount());
		}
		
		//require a method name
		next = Main.read.next();
		if(next.getLexical() != Lexical.IDENTIFIER)
			throw new ParseException("A method name is required!", Reader.getCount());
		append(next, Program.SUBROUTINE_NAME);
		
		//Open '(' for parameter list
		next = Main.read.next();
		if(next.getSymbol() != Symbol.LPER)
			throw new ParseException("Expected a perenthesis for a parameter list!", Reader.getCount());
		append(next);
		
		//start parsing parameter list until ')';
		next = Main.read.next();
		Element list = decend(Program.PARAM_LIST);
		while(true) {
			
			//check for a closing ')' in case there are zero parameters
			if(next.getSymbol() == Symbol.RPER) {
				root.appendChild(list);
				append(next);
				break;
			}
			
			//if not, get a type
			new Type(list).run(next);
			
			//get variable name
			next = Main.read.next();
			if(next.getLexical() != Lexical.IDENTIFIER)
				throw new ParseException("Expected a variable name in parameter list!", Reader.getCount());
			append(list, next, Program.VAR_NAME);
			
			//read terminators
			next = Main.read.next();
			if(next.getSymbol() == Symbol.COMMA) {
				
				//Require a comma
				append(list, next);
				next = Main.read.next();
				continue;
			} else if(next.getSymbol() == Symbol.RPER) {
				
				//or an ending ')'
				root.appendChild(list);
				append(next);
				break;
			} else {
				
				//but not neither!
				throw new ParseException("Expected a comma or a close perenthesis!", Reader.getCount());
			}
		}
		
		//parse the body of the subroutine.
		next = Main.read.next();
		if(!SubroutineBody.verify(next)) 
			throw new ParseException("Expected a subroutine body. They open with a '{'!", Reader.getCount());
		Element body = decend(Program.SUBROUTINE_BODY);
		new SubroutineBody(body).run(next);
		root.appendChild(body);
	}
	
	public static boolean verify(Token header) {
		return 
				header.getKeyword() == Keyword.CONSTRUCTOR || 
				header.getKeyword() == Keyword.FUNCTION || 
				header.getKeyword() == Keyword.METHOD;
	}
}
