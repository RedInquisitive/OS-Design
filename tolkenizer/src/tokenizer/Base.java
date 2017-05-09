package tokenizer;

import java.text.ParseException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import io.Lex;
import io.XmlName;

public abstract class Base implements XmlName {
	public ArrayList<Base> stack = new ArrayList<>();

	public final String xml;
	public final Element root;
	
	public Base(String xml, Element root) {
		this.xml = xml;
		this.root = root;
		run();
	}
	
	public abstract void run();
	
	protected final void requires(Element root, XmlName require, Lex lex) throws ParseException {
		if(lex == null || require.xml().equals(lex.xml())) 
			throw new ParseException(lex.xmlText() + " is not a " + require.xml() + " " + require.xmlText(), Lex.getCount());
		Element e = root.getOwnerDocument().createElement(lex.xml());
		e.setTextContent(lex.xmlText());
		root.appendChild(e);
	}
	
	protected final Element decend(Element root, XmlName name) {
		Element e = root.getOwnerDocument().createElement(name.xml());
		return e;
	}
}
