package blocks;

import java.text.ParseException;

import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import structure.Express;
import tokenizer.Base;
import tokenizer.Main;

public class Expression extends Base {

	protected Expression(Element root) {
		super(root);
	}

	/**
	 * checks the syntax of an expression.
	 */
	public void run(Token header) throws ParseException {
		Token next;
		
		if(!Term.verify(header))
			throw new ParseException("Expected a term for the expression!", Reader.getCount());
		
		//get term
		Element term = decend(Express.TERM);
		new Term(term).run(header);
		root.appendChild(term);
		
		while(true) {
			
			//get operator
			next = Main.read.next();
			if(!Op.verify(next)) break;
			new Op(root).run(next);
			
			//get term if operator is present
			next = Main.read.next();
			if(!Term.verify(next))
				throw new ParseException("Expected a term for the expression!", Reader.getCount());
			
			//Descend into term
			Element subterm = decend(Express.TERM);
			new Term(subterm).run(next);
			root.appendChild(subterm);
		}
		
		//end with ;
		Main.read.abort();
	}
}
