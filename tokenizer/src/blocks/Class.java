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

public class Class extends Base {

	public Class(Element root) {
		super(root);
	}

	/**
	 * Checks for the syncax of a class.
	 */
	public void run(Token header) throws ParseException {
		Token next;
		
		//Classes start with the keyword "class"
		if(!verify(header))
			throw new ParseException("Expected the word class!", Reader.getCount());
		append(header);
		
		//A class requires a name
		next = Main.read.next();
		if(next.getLexical() != Lexical.IDENTIFIER) 
			throw new ParseException("Class name is not a valid identifier!", Reader.getCount());
		append(next, Program.CLASS_NAME);
		
		//A class requires an open {
		next = Main.read.next();
		if(next.getSymbol() != Symbol.LBRACE) 
			throw new ParseException("Class requires open brace after identifier!", Reader.getCount());
		append(next);
		
		//Read any amount, including 0, of
		while(true) {
			next = Main.read.next();
			
			//Class variables
			if(VarDec.verifyClass(next)) {
				Element classVarDec = decend(Program.CLASS_VAR_DEC);
				new VarDec(classVarDec, Program.CLASS_VAR_DEC).run(next);
				root.appendChild(classVarDec);
				continue;
			}
			
			//Class subroutines
			if(SubroutineDec.verify(next)) {
				Element subroutineDec = decend(Program.SUBROUTINE_DEC);
				new SubroutineDec(subroutineDec).run(next);
				root.appendChild(subroutineDec);
				continue;
			}
			
			//neither
			break;
		}
		
		//Class closing brace
		if(next.getSymbol() != Symbol.RBRACE) 
			throw new ParseException("Class requires closed brace after body!", Reader.getCount());
		append(next);
	}

	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.CLASS;
	}
}
