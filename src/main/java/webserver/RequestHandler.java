package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import common.ContentType;
import common.HttpMethod;
import common.HttpStatus;
import common.Request;
import common.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.UserService;
import util.ViewUtils;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;
	
	private UserService userService;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.userService = new UserService();
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			BufferedReader requestBuffer = new BufferedReader(new InputStreamReader(in));
			DataOutputStream responseStream = new DataOutputStream(out);
			
			Request request = new Request(requestBuffer);
			Response response = new Response(responseStream);
			
			String requestMethod = request.getMethod();
			if (StringUtils.equalsIgnoreCase(requestMethod, HttpMethod.POST.name())) {	
				doPost(request, response);
				
			} else if(StringUtils.equalsIgnoreCase(requestMethod, HttpMethod.GET.name())) {
				doGet(request, response);		
				
			}
			
		} catch (IOException e) {
			log.debug("RequestHandler run ERROR", e);
		}
	}
	
	public void doPost(Request request, Response response) {
		String requestViewName = null;
		
		String requestUrl = request.getRequestUrl();
		Map<String, String> requestBody = request.getBody();
		
		if (requestUrl.startsWith("/user/create")) {
			requestViewName = userService.joinUser(requestBody);
			
			response.setHttpStatus(HttpStatus.STATUS_302);
			response.setLocation(requestViewName);
			response.flush();
			
			return;
			
		} else if (requestUrl.startsWith("/user/login")) {
			requestViewName = userService.loginUser(requestBody);
			
		}
		
		byte[] viewFileBytes = ViewUtils.viewFileBytes(requestViewName);
		response.setHttpStatus(HttpStatus.STATUS_200);
		response.setContentType(ContentType.TEXT_HTML.getValue());
		response.setContentLength(viewFileBytes.length);
		response.setBody(viewFileBytes);
		
	}
	
	public void doGet(Request request, Response response) {
		String requestViewName = null;
		
		String requestUrl = request.getRequestUrl();
		if(StringUtils.equals(requestUrl, "/index.html")) {
			requestViewName = "/index.html";
			
		} else if (requestUrl.startsWith("/user/form")) {
			requestViewName = userService.joinForm();
			
		}
		
		byte[] viewFileBytes = ViewUtils.viewFileBytes(requestViewName);
		response.setHttpStatus(HttpStatus.STATUS_200);
		response.setContentType(ContentType.TEXT_HTML.getValue());
		response.setBody(viewFileBytes);
	}

}
