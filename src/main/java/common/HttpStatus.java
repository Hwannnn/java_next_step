package common;

/**
 * @author changhwan-sin on 2017-11-13.
 */

public enum HttpStatus {
	STATUS_200("200 OK"),
	STATUS_302("302 FOUND");
	
	private final String statusCode;
	HttpStatus(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getCode() {
		return this.statusCode;
	}
	
}
