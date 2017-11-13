package common;

/**
 * @author changhwan-sin on 2017-11-13.
 */

public enum HttpHeader {
	METHOD("Method"),
	URL("Url"),
	CONTENT_LENGTH("Content-Length");

	private final String name;
	HttpHeader(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
