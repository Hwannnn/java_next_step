package next.constant;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public enum CommonView {
	ERROR_VIEW("/error.jsp");

	private final String value;
	CommonView(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}
