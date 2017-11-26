package util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public class HttpSessions {
	private static final Map<String, HttpSession> SESSIONS = new HashMap<>();

	public static HttpSession getHttpSession(String sessionId) {
		return SESSIONS.get(sessionId);
	}

	public static void setHttpSession(String sessionId) {
		SESSIONS.put(sessionId, new HttpSession());
	}

}
