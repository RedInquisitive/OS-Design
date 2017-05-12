package structure;

import java.text.ParseException;

import tokenizer.Main;
import xml.XmlName;

public enum Statements implements XmlName {
	STATEMENTS("statements");
	
	private final String name;
	private Statements(String name) {
		this.name= name;
	}

	public String xml() {
		return name;
	}
	
	public String xmlText() throws ParseException {
		throw Main.textlessXML(getClass().getName());
	}
}
