package next.constant;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public enum Gap {
	EMPTY("");

	private final String value;
	Gap(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
}
