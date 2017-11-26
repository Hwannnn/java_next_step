package next.util;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public class HttpSessions {
	private static final Map<String, HttpSession> SESSIONS = new HashMap<>();

	public HttpSession getHttpSession(String sessionId) {
		return SESSIONS.get(sessionId);
	}

	public void setHttpSession(String sessionId, HttpSession session) {
		SESSIONS.put(sessionId, session);
	}

}
