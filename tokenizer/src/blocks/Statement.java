package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Statements;
import symbols.Keyword;
import tokenizer.Base;
import tokenizer.Main;

public class Statement extends Base {

	protected Statement(Element root) {
		super(root);
	}

	@Override
	public void run(Token header) throws ParseException {
		Token next;
		
		//Requires any of the headers as stated in the verify function
		if(!verify(header))
			throw new ParseException("Expected any statement!", Reader.getCount());
		
		next = Main.read.next();
		Element statement = null;
		if(!LetStatement.verify(next)) {
			statement = decend(Statements.LET_STATEMENT);
			new LetStatement(statement).run(next);
			return;
		}
		if(!IfStatement.verify(next)) {
			statement = decend(Statements.IF_STATEMENT);
			new IfStatement(statement).run(next);
			return;
		}
		if(!WhileStatement.verify(next)) {
			statement = decend(Statements.WHILE_STATEMENT);
			new WhileStatement(statement).run(next);
			return;
		}
		if(!DoStatement.verify(next)) {
			statement = decend(Statements.DO_STATEMENT);
			new DoStatement(statement).run(next);
			return;
		}
		if(!ReturnStatement.verify(next)) {
			statement = decend(Statements.RETURN_STATEMENT);
			new ReturnStatement(statement).run(next);
			return;
		}
		root.appendChild(statement);
	}
	
	public static boolean verify(Token header) {
		return header.getKeyword() == Keyword.LET ||
			   header.getKeyword() == Keyword.IF ||
			   header.getKeyword() == Keyword.WHILE ||
			   header.getKeyword() == Keyword.DO ||
			   header.getKeyword() == Keyword.RETURN;
	}
}
