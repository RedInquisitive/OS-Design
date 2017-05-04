package tolkenizer;

import java.util.Scanner;
import java.util.regex.Pattern;

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

import enums.Symbol;

public class Main {
	
	static public final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
	
	public static void main(String[]args) {
		
		StringBuilder regex = new StringBuilder();
		for(Symbol s : Symbol.values()) {
			regex.append("|" + String.format(WITH_DELIMITER, Pattern.quote(s.name + "")));
		}
		String find = "(\\s+)|([\\r\\n]+)" + regex.toString();
		System.out.println(find);
		Pattern pattern = Pattern.compile(find);
		
		Scanner reader = new Scanner(System.in);
		reader.useDelimiter(pattern);
		while(reader.hasNext()) {
			System.out.println(reader.next());
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
