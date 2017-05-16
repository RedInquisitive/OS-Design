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
	 * Checks for all statements. All of them.
	 * Will read until an invalid keyword is found.
	 * Will call abort if no token is found.
	 */
	public void run(Token header) throws ParseException {
		Main.read.abort();
		while(true) {
			Token next = Main.read.next();
			Element statement = null;
			
			//let something equal something
			if(LetStatement.verify(next)) {
				statement = decend(Statements.LET_STATEMENT);
				new LetStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			//if something then something, optional else
			if(ConditionalStatement.verifyIf(next)) {
				statement = decend(Statements.IF_STATEMENT);
				new ConditionalStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			//while something is true
			if(ConditionalStatement.verifyWhile(next)) {
				statement = decend(Statements.WHILE_STATEMENT);
				new ConditionalStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			//do something
			if(DoStatement.verify(next)) {
				statement = decend(Statements.DO_STATEMENT);
				new DoStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			//return something (or nothing, I won't judge)
			if(ReturnStatement.verify(next)) {
				statement = decend(Statements.RETURN_STATEMENT);
				new ReturnStatement(statement).run(next);
				root.appendChild(statement);
				continue;
			}
			
			//nothing valid
			break;
		}
		
		//abort closing }
		Main.read.abort();
	}
}
