package tokenizer;

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

import io.Lex;
import io.Read;

public class Main {

	public static void main(String[]args) {
		try {
			Read read = new Read(System.in);
			while(read.hasNext()) {
				Lex lex = read.next();
				System.out.println(lex.xml() + " " + lex.xmlText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = df.newDocumentBuilder();
			Document doc = db.newDocument();
			
			Element root = doc.createElement("tokens");
			root.appendChild(root.getOwnerDocument().createTextNode("test"));
			doc.appendChild(root);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer tr = tf.newTransformer();
			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty(OutputKeys.METHOD, "xml");
			tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tr.setOutputProperty(OutputKeys.STANDALONE, "yes");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(System.out);
			tr.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
