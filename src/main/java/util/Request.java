package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import common.HttpCookie;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import common.HttpHeader;
import common.Separator;
import util.HttpRequestUtils;
import util.IOUtils;
import util.LoggerInstanceFactory;

public class Request {
	private Logger log = LoggerInstanceFactory.getInstance(Request.class);

	private Map<String, String> header;
	private Map<String, String> cookies;
	private Map<String, String> parameter;
	private Map<String, String> body;

	private HttpSession session;

	public Request(BufferedReader requestBuffer) {
		this.header = new HashMap<>();
		this.parameter = new HashMap<>();
		this.body = new HashMap<>();

		this.cookies = new HashMap<>();
		this.session = new HttpSession();

		parseRequestHeader(requestBuffer);
		parseRequestParameter();
		parseRequestBody(requestBuffer);
	}

	public String getMethod() {
		return this.header.get(HttpHeader.METHOD.getName());
	}

	public String getRequestUrl() {
		return this.header.get(HttpHeader.URL.getName());
	}

	public int getContentLength() {
		String contentLength = this.header.get(HttpHeader.CONTENT_LENGTH.getName());
		if (contentLength == null) {
			return 0;
		}
		return Integer.parseInt(contentLength);
	}

	public String getParameter(String key) {
		return this.parameter.get(key);
	}

	public Map<String, String> getBody() {
		return this.body;
	}

	public String getCookie(String key) {
		return this.cookies.get(key);
	}

	public void addCookies(String key, String value) {
		this.cookies.put(key, value);
	}

	public HttpSession getSession() {
		String sessionId = this.cookies.get(HttpCookie.JSESSIONID.name());

		return HttpSessions.getHttpSession(sessionId);
	}

	private void parseRequestHeader(BufferedReader requestBuffer) {
		try {
			String headerLine = requestBuffer.readLine();
			this.parseRequestLine(headerLine);

			while (StringUtils.equals(headerLine, "") == false) {
				if (headerLine.startsWith(HttpHeader.CONTENT_LENGTH.getName())) {
					String contentLength = HttpRequestUtils.parseHeader(headerLine).getValue();
					this.header.put(HttpHeader.CONTENT_LENGTH.getName(), contentLength);

				} else if (headerLine.startsWith(HttpHeader.COOKIE.getName())) {
					String cookieValues = HttpRequestUtils.parseHeader(headerLine).getValue();
					this.cookies.putAll(HttpRequestUtils.parseCookies(cookieValues));
				}

				headerLine = requestBuffer.readLine();
			}

		} catch (IOException e) {
			log.error("BadRequest! Don't include Header Infomation", e);
		}
	}

	private void parseRequestLine(String requestLine) {
		String[] headerInfo = requestLine.split(Separator.WHITESPACE.getValue());

		this.header.put(HttpHeader.METHOD.getName(), headerInfo[0]);
		this.header.put(HttpHeader.URL.getName(), headerInfo[1]);
	}

	private void parseRequestParameter() {
		String[] queryStrings = getRequestUrl().split(Separator.QUESTION_MARK.getValue());

		if (queryStrings.length == 1) {
			return;
		}

		this.parameter.putAll(HttpRequestUtils.parseQueryString(queryStrings[1]));
	}

	private void parseRequestBody(BufferedReader requestBuffer) {
		Integer contentLength = getContentLength();
		if (contentLength == null) {
			return;
		}

		try {
			String bodyData = IOUtils.readData(requestBuffer, contentLength);

			this.body.putAll(HttpRequestUtils.parseQueryString(bodyData));
		} catch (IOException e) {
			log.error("BadRequest! Don't include Body Infomation", e);
		}
	}

}
