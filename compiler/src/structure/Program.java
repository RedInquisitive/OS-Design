package structure;

import java.text.ParseException;

import tokenizer.Main;
import xml.XmlName;

public enum Program implements XmlName {
	CLASS_VAR_DEC("classVarDec"),
	SUBROUTINE_DEC("subroutineDec"),
	VAR_DEC("varDec"),
	CLASS_NAME("className"),
	SUBROUTINE_NAME("subroutineName"),
	VAR_NAME("varName"),
	TYPE("type"),
	PARAM_LIST("parameterList"),
	SUBROUTINE_BODY("subroutineBody");
	
	private final String name;
	private Program(String name) {
		this.name= name;
	}

	public String xml() {
		return name;
	}
	
	public String xmlText() throws ParseException {
		throw Main.textlessXML(getClass().getName());
	}
}
