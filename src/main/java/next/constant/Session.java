package next.constant;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public enum Session {
	LOGIN_ID("loginId");

	private final String value;
	Session(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}
