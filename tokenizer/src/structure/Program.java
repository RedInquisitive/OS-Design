package structure;

import java.text.ParseException;

import tokenizer.Main;
import xml.XmlName;

public enum Program implements XmlName {
	DEC_VAR_CLASS("classVarDec"),
	DEC_SUBROUTINE("subroutineDec"),
	DEC_VAR("varDec"),
	NAME_CLASS("className"),
	NAME_SUBROUTINE("subroutineName"),
	NAME_VAR("varName"),
	TYPE("type"),
	PARAM_LIST("parameterList"),
	BODY_SUBROUTINE("subroutineBody");
	
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
