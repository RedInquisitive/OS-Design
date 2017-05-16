package structure;

import java.text.ParseException;

import tokenizer.Main;
import xml.XmlName;

public enum Express implements XmlName {
	EXPRESSION_LIST("expressionList"),
	EXPRESSION("expression"),
	TERM("term"),
	
	//non terminal
	OP("op");
	
	private final String name;
	private Express(String name) {
		this.name= name;
	}

	public String xml() {
		return name;
	}
	
	public String xmlText() throws ParseException {
		throw Main.textlessXML(getClass().getName());
	}
}
