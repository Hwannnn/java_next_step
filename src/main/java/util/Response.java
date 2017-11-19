package util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;

import common.ContentType;
import common.HttpHeader;
import common.HttpStatus;
import util.LoggerInstanceFactory;

public class Response {
	private Logger log = LoggerInstanceFactory.getInstance(Response.class);
	
	private DataOutputStream response;
	private Map<String, String> headers;
	
	public Response(OutputStream responseStream) {
		response = new DataOutputStream(responseStream);
		headers = new HashMap<>();
	}
	
	public void addHeaders(HttpHeader key, String value) {
		headers.put(key.getName(), value);
	}
	
	public void sendRedirect(String viewName) {
		try {
			response.writeBytes(String.format("HTTP/1.1 %s\r\n", HttpStatus.STATUS_302.getCode()));
			response.writeBytes(String.format("Location: %s\r\n", viewName));
			
			setHeaders();
			
			flush();
		} catch (IOException e) {
			String errorMessage = String.format("Response sendForward ERROR");
			log.debug(errorMessage, e);
		}
	}
	
	public void sendForward(String viewName) {
		try {
			byte[] viewBytes = Files.readAllBytes(Paths.get("webapp", viewName));
			setContentInfo(viewName, viewBytes);
			
			response.writeBytes(String.format("HTTP/1.1 %s\r\n", HttpStatus.STATUS_200.getCode()));
			
			setHeaders();
			setHtmlBody(viewBytes);
			
			flush();
		} catch (IOException e) {
			String errorMessage = String.format("Response sendForward ERROR");
			log.debug(errorMessage, e);
		}
	}
	
	public void sendForwardBody(String viewData) {
		try {
			response.writeBytes(String.format("HTTP/1.1 %s\r\n", HttpStatus.STATUS_200.getCode()));
			
			setHeaders();
			setTextBody(viewData);
			
			flush();
		} catch (IOException e) {
			String errorMessage = String.format("Response sendForwardBody ERROR viewData : %s", viewData);
			log.debug(errorMessage, e);
		}
	}
	
	private void setContentInfo(String viewName, byte[] viewBytes) {
		addHeaders(HttpHeader.CONTENT_TYPE, ContentType.getContentType(viewName));
		addHeaders(HttpHeader.CONTENT_LENGTH, viewBytes.length + "");
	}
	
	private void setHeaders() {
		try {
			for(Entry<String, String> header : headers.entrySet()) {
				response.writeBytes(String.format("%s: %s\r\n", header.getKey(), header.getValue()));
			}
			
			response.writeBytes("\r\n");
		} catch (IOException e) {
			String errorMessage = String.format("Response setHeaders ERROR");
			log.debug(errorMessage, e);
		}
	}

	
	private void setHtmlBody(byte[] viewBytes) {
		try {
			response.write(viewBytes, 0, viewBytes.length);
		} catch (IOException e) {
			String errorMessage = String.format("Response setHtmlBody ERROR");
			log.debug(errorMessage, e);
		}
	}
	
	private void setTextBody(String viewData) {
		try {
			byte[] viewBytes = viewData.getBytes();
			response.write(viewBytes, 0, viewBytes.length);
		} catch (IOException e) {
			String errorMessage = String.format("Response setTextBody ERROR viewData : %s", viewData);
			log.debug(errorMessage, e);
		}
	}
	
	private void flush() {
		try {
			response.writeBytes("\r\n");
			response.flush();
		} catch (IOException e) {
			String errorMessage = String.format("Response flush ERROR");
			log.debug(errorMessage, e);
		}
		
	}

}
