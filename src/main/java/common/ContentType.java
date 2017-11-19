package common;

public enum ContentType {
	TEXT_PLAIN("text/plain"),
	TEXT_CSS("text/css"),
	TEXT_JS("text/js"),
	TEXT_HTML("text/html");
	
	
	private final String value;
	ContentType(String value) {
		this.value = value;
	}
	
	public static String getContentType(String viewName) {
		for (ContentType contentType : values()) {
			if (viewName.endsWith(".html")) {
				return TEXT_HTML.value;
			}
		
			if(viewName.endsWith(".css")) {
				return TEXT_CSS.value;
			}
			
			if(viewName.endsWith(".js")) {
				return TEXT_JS.value;
			}
		}
		
		return TEXT_PLAIN.value;	
	}
	
}
