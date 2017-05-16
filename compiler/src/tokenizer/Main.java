package tokenizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

import blocks.Class;
import io.Reader;
import io.Token;

public class Main {

	public static Reader read;
	
	public static void main(String[] args) {
		List<File> files = new ArrayList<>();
		for(String path : args) {
			files.add(new File(path));
		}
		
		//read files
		if(files.size() == 0) {
			parse(System.in, System.out);
		} else {
			for(File file : files) {
				try {
					parse(new FileInputStream(file), new FileOutputStream(new File(file.getAbsoluteFile() + ".xml")));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void parse(InputStream in, OutputStream out) {
		try {
			Document doc = input(in);
			Element root = doc.createElement("class");
			
			//get the root class
			Token next = read.next();
			if(!Class.verify(next))
				throw new ParseException("A class must be the first element in a file!", Reader.getCount());
			new Class(root).run(next);
			doc.appendChild(root);

			output(out, doc);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch(ParseException e) {
			System.err.println("Error on symbol " + e.getErrorOffset() + " during parsing!");
			e.printStackTrace();
		}
	}
	
	public static Document input(InputStream in) throws ParserConfigurationException {
		read = new Reader(in);
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = df.newDocumentBuilder();
		Document doc = db.newDocument();
		return doc;
	}

	public static void output(OutputStream out, Document doc) throws TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer tr = tf.newTransformer();
		tr.setOutputProperty(OutputKeys.INDENT, "yes");
		tr.setOutputProperty(OutputKeys.METHOD, "xml");
		tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tr.setOutputProperty(OutputKeys.STANDALONE, "yes");
		tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(out);
		tr.transform(source, result);
	}
	
	public static ParseException textlessXML(String className) {
		return new ParseException("A " + className + " has no body text. This is a programmer's fault.", Reader.getCount());
	}
}
