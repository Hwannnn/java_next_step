package common;

/**
 * @author changhwan-sin on 2017-11-13.
 */

public enum HttpHeader {
	METHOD("Method"),
	URL("Url"),
	ACCEPT("Accept"),
	CONTENT_TYPE("Content-Type"),
	CONTENT_LENGTH("Content-Length"),
	SET_COOKIE("Set-Cookie"),
	COOKIE("Cookie");

	private final String name;
	HttpHeader(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
