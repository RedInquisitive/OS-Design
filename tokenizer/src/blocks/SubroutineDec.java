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
			throw new ParseException("Expected a parenthesis for a parameter list!", Reader.getCount());
		append(next);
		
		//Get the parameter list
		next = Main.read.next();
		Element parameters = decend(Program.PARAM_LIST);
		new ParameterList(parameters).run(next);
		root.appendChild(parameters);
		
		//if the parameter list fails, check if it is a parenthesis
		next = Main.read.next();
		if(next.getSymbol() != Symbol.RPER)
			throw new ParseException("Expected a closing parenthesis for a parameter list!", Reader.getCount());
		
		//parse the body of the subroutine.
		next = Main.read.next();
		if(!SubroutineBody.verify(next)) 
			throw new ParseException("Expected a subroutine body. They open with a '{'!", Reader.getCount());
		
		//create sub for body
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
