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

	public void run(Token header) throws ParseException {
		Token next;
		if(!verify(header))
			throw new ParseException("Expected the word class!", Reader.getCount());
		append(header);
		
		next = Main.read.next();
		if(next.getLexical() != Lexical.IDENTIFIER) 
			throw new ParseException("Class name is not a valid identifier!", Reader.getCount());
		append(next);
		
		next = Main.read.next();
		if(next.getSymbol() != Symbol.LBRACE) 
			throw new ParseException("Class requires open brace after identifier!", Reader.getCount());
		append(next);
		
		while(true) {
			next = Main.read.next();
			
			if(ClassVarDec.verify(next)) {
				Element classVarDec = decend(Program.DEC_VAR_CLASS);
				new ClassVarDec(classVarDec).run(next);
				root.appendChild(classVarDec);
				continue;
			}
			
			if(SubroutineDec.verify(next)) {
				Element subroutineDec = decend(Program.DEC_SUBROUTINE);
				new SubroutineDec(subroutineDec).run(next);
				root.appendChild(subroutineDec);
				continue;
			}
			break;
		}
		
		if(next.getSymbol() != Symbol.RBRACE) 
			throw new ParseException("Class requires closed brace after body!", Reader.getCount());
		append(next);
	}

	public static boolean verify(Token header) {
		try {
			return header.getKeyword() == Keyword.CLASS;
		} catch (ParseException e) {
			return false;
		}
	}
}
