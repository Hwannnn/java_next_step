package common;

public enum Separator {
	WHITESPACE(" "),
	QUESTION_MARK("\\?");
	
	private final String value;
	Separator(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}

}
