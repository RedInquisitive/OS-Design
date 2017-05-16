package tokenizer;

import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import io.Reader;
import io.Token;
import blocks.Class;

public class Main {

	public static Reader read;
	
	public static void main(String[]args) {
		try {
			read = new Reader(System.in);
			DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = df.newDocumentBuilder();
			Document doc = db.newDocument();
			
			Element root = doc.createElement("class");
			Token next = read.next();
			if(Class.verify(next)) {
				new Class(root).run(next);
			}
			doc.appendChild(root);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer tr = tf.newTransformer();
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty(OutputKeys.METHOD, "xml");
			tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tr.setOutputProperty(OutputKeys.STANDALONE, "yes");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(System.out);
			tr.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch(ParseException e) {
			System.err.println("Error on symbol " + e.getErrorOffset() + " during parsing!");
			e.printStackTrace();
		}
	}
	
	public static ParseException textlessXML(String className) {
		return new ParseException("A " + className + " has no body text. This is a programmer's fault.", Reader.getCount());
	}
}
