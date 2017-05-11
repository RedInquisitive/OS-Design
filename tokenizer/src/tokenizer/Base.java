package tokenizer;

import java.text.ParseException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import io.Token;
import xml.XmlName;

public abstract class Base {
	public ArrayList<Base> stack = new ArrayList<>();

	public final Element root;
	
	protected Base(Element root) {
		this.root = root;
	}
	
	protected final void append(XmlName name) throws ParseException {
		Element e = root.getOwnerDocument().createElement(name.xml());
		e.setTextContent(name.xmlText());
		root.appendChild(e);
	}

	protected final Element decend(XmlName name) {
		Element e = root.getOwnerDocument().createElement(name.xml());
		return e;
	}

	public abstract void run(Token header) throws ParseException;
}
