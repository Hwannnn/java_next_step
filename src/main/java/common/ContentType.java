package common;

public enum ContentType {
	TEXT_HTML("text/html");
	
	
	private final String value;
	ContentType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
}
