package util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public class HttpSession {
	private Map<String, Object> attribute = new HashMap<>();

	public Object getAttribute(String attributeKey) {
		return attribute.get(attributeKey);
	}

	public void setAttribute(String attributeKey, Object attributeValue) {
		attribute.put(attributeKey, attributeValue);
	}

	public void removeAttribute(String attributeKey) {
		attribute.remove(attributeKey);
	}

	public void invalidate() {
		attribute.clear();
	}

}
