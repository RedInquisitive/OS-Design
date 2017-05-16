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

	public void run(Token header) throws ParseException {
		Token next;
		
		if(!Term.verify(header))
			throw new ParseException("Expected a term for the expression!", Reader.getCount());
		Element term = decend(Express.TERM);
		new Term(term).run(header);
		root.appendChild(term);
		
		while(true) {
			next = Main.read.next();
			if(!Op.verify(next))
				break;
			new Op(root).run(next);
			
			next = Main.read.next();
			if(!Term.verify(next))
				throw new ParseException("Expected a term for the expression!", Reader.getCount());
			Element subterm = decend(Express.TERM);
			new Term(subterm).run(next);
			root.appendChild(subterm);
		}
		Main.read.abort();
	}
}
