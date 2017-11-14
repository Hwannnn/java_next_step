package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import util.HttpRequestUtils;
import util.IOUtils;
import util.LoggerInstanceFactory;

public class Request {
	private Logger log = LoggerInstanceFactory.getInstance(Request.class);
	
	private Map<String, String> header;
	private Map<String, String> parameter;
	private Map<String, String> body;
	
	public Request(BufferedReader requestBuffer) {
		header = new HashMap<>();
		parameter = new HashMap<>();
		body = new HashMap<>();
		
		parseRequestHeader(requestBuffer);
		parseRequestParameter();
		parseRequestBody(requestBuffer);
	}
	
	public String getMethod() {
		return header.get(HttpHeader.METHOD.getName());
	}
	
	public String getRequestUrl() {
		return header.get(HttpHeader.URL.getName());
	}
	
	public int getContentLength() {
		String contentLength =  header.get(HttpHeader.CONTENT_LENGTH.getName());
		
		if(contentLength == null) {
			return 0;
		}
	
		return Integer.parseInt(contentLength);
	}
	
	public String getParameter(String key) {
		return parameter.get(key);
	}
	
	public Map<String, String> getBody() {
		return body;
	}
	
	private void parseRequestHeader(BufferedReader requestBuffer) {
		try {
			String headerLine = requestBuffer.readLine();
			String[] headerInfo = headerLine.split(Separator.WHITESPACE.getValue());
			
			this.header.put(HttpHeader.METHOD.getName(), headerInfo[0]);
			this.header.put(HttpHeader.URL.getName(), headerInfo[1]);
			
			while(StringUtils.equals(headerLine, "") == false) {
				if(headerLine.startsWith(HttpHeader.CONTENT_LENGTH.getName())) {
					String contentLength = HttpRequestUtils.parseHeader(headerLine).getValue();
					
					this.header.put(HttpHeader.CONTENT_LENGTH.getName(), contentLength);
				}
				
				headerLine = requestBuffer.readLine();
			}

		} catch (IOException e) {
			log.error("BadRequest! Don't include Header Infomation", e);
		}
	}
	
	private void parseRequestParameter() {
		String[] queryStrings = getRequestUrl().split(Separator.QUESTION_MARK.getValue());
		
		if(queryStrings.length == 1) {
			return;
		}
		
		this.parameter.putAll(HttpRequestUtils.parseQueryString(queryStrings[1]));
	}
	
	private void parseRequestBody(BufferedReader requestBuffer) {
		Integer contentLength = getContentLength();
		if(contentLength == null) {
			return;
		}
		
		try {	
			String bodyData = IOUtils.readData(requestBuffer, contentLength);
			
			body.putAll(HttpRequestUtils.parseQueryString(bodyData));
		} catch (IOException e) {
			log.error("BadRequest! Don't include Body Infomation", e);
		}
	}

}
