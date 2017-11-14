package common;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;

import util.LoggerInstanceFactory;

public class Response {
	private Logger log = LoggerInstanceFactory.getInstance(Response.class);
	
	DataOutputStream response;
	
	public Response(OutputStream responseStream) {
		response = new DataOutputStream(responseStream);
	}
	
	public void setHttpStatus(HttpStatus httpStatus) {
		try {
			this.response.writeBytes(String.format("HTTP/1.1 %s\r\n", httpStatus.getStatusCode()));
			
		} catch (IOException e) {
			String errorMessage = String.format("Response setHttpStatus ERROR httpStatus: %s", httpStatus.getStatusCode());
			log.debug(errorMessage, e);
		}
	}
	
	public void setContentType(String contentType) {
		try {
			this.response.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", contentType));
			
		} catch (IOException e) {
			String errorMessage = String.format("Response setContentType ERROR contentType: %s", contentType);
			log.debug(errorMessage, e);
		}
	}
	
	public void setContentLength(int contentLength) {
		try {
			this.response.writeBytes(String.format("Content-Length: %d\r\n", contentLength));
			
		} catch (IOException e) {
			String errorMessage = String.format("Response setContentLength ERROR contentLength: %s", contentLength);
			log.debug(errorMessage, e);
		}
	}
	
	public void setLocation(String location) {
		try {
			this.response.writeBytes(String.format("Location: %s\r\n", location));
			
		} catch (IOException e) {
			String errorMessage = String.format("Response setLocation ERROR location: %s", location);
			log.debug(errorMessage, e);
		}
	}
	
	public void setBody(byte[] viewFileBytes) {
		try {
			response.write(viewFileBytes, 0, viewFileBytes.length);
			
		} catch (IOException e) {
			String errorMessage = String.format("Response setBody ERROR viewFileBytes.length: %d", viewFileBytes.length);
			log.debug(errorMessage, e);
		}
		
	}
	
	public void flush() {
		try {
			response.writeBytes("\r\n");
			response.flush();
		} catch (IOException e) {
			log.debug("Response flushResponse ERROR", e);
		}
		
	}

}
