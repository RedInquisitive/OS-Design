package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Token;
import structure.Statements;
import tokenizer.Base;
import tokenizer.Main;

public class Statement extends Base {

	protected Statement(Element root) {
		super(root);
	}

	/**
	 * Will read until an invalid keyword is found.
	 * 
	 * Will call abort if no token is found.
	 */
	public void run(Token header) throws ParseException {
		Main.read.abort();
		while(true) {
			Token next = Main.read.next();
			Element statement = null;
			
			if(LetStatement.verify(next)) {
				statement = decend(Statements.LET_STATEMENT);
				new LetStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			if(ConditionalStatement.verifyIf(next)) {
				statement = decend(Statements.IF_STATEMENT);
				new ConditionalStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			if(ConditionalStatement.verifyWhile(next)) {
				statement = decend(Statements.WHILE_STATEMENT);
				new ConditionalStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			if(DoStatement.verify(next)) {
				statement = decend(Statements.DO_STATEMENT);
				new DoStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			if(ReturnStatement.verify(next)) {
				statement = decend(Statements.RETURN_STATEMENT);
				new ReturnStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			break;
		}
		Main.read.abort();
	}
}
