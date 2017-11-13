package common;

/**
 * @author changhwan-sin on 2017-11-13.
 */

public enum HttpStatusCode {
	STATUS_200("200 OK"),
	STATUS_302("302 DOUNG");

	private final String statuscode;
	HttpStatusCode(String statuscode) {
		this.statuscode = statuscode;
	}

	public String getStatusCode() {
		return this.statuscode;
	}

}
