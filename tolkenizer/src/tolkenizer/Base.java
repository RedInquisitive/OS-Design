package tolkenizer;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Base {
	public ArrayList<Base> stack = new ArrayList<>();

	public final String xml;
	public final Element root;
	
	public Base(String xml, Element root) {
		this.xml = xml;
		this.root = root;
	}
	
	
}
