package enums;

public enum Type {
	NOTHING(""),
	KEYWORD("keyword"),
	SYMBOL("symbol"),
	INTEGER("integerConstant"),
	STRING("StringConstant"),
	IDENTIFIER("identifier");
	
	public final String name;
	private Type(String name) {
		this.name= name;
	}
}
