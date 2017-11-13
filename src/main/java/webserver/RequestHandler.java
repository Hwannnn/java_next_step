package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import common.HttpHeader;
import common.HttpMethod;
import common.ResponseViewType;
import common.Separator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.UserService;
import util.HttpRequestUtils;
import util.HttpResponseUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;
	
	private UserService userService;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
		this.userService = new UserService();
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
				connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			BufferedReader request = new BufferedReader(new InputStreamReader(in));
			int httpStatusCode = 200;

			Map<String, String> requestHeader = parseReqeustHeader(request);
			String requestMethod = requestHeader.get(HttpHeader.METHOD.getName());
			String requestUrl = requestHeader.get(HttpHeader.URL.getName());
			String requestViewName = StringUtils.substringBefore(requestUrl, Separator.QUESTION_MARK.getValue());

			if (StringUtils.equalsIgnoreCase(requestMethod, HttpMethod.POST.name())) {
				int contentLength = Integer.parseInt(requestHeader.get(HttpHeader.CONTENT_LENGTH.getName()));
				Map<String, String> body = parseRequestBody(request, contentLength);
				
				if (requestUrl.startsWith("/user/create")) {
					userService.joinUser(body);
					
					httpStatusCode = 302;
					requestViewName = "/index.html";
					
				} else if (requestUrl.startsWith("/user/login")) {
					userService.loginUser(body);
					
					
				}
			}
			
			byte[] responseBodyDate = null;
			if(ResponseViewType.isResponseViewType(requestViewName)) {
				responseBodyDate = Files.readAllBytes(Paths.get("./webapp", requestViewName));
			} else {
				httpStatusCode = 404;
			}
			
			DataOutputStream dos = new DataOutputStream(out);
			
			
			if(httpStatusCode == 200) {
				HttpResponseUtils.response200Header(dos, responseBodyDate.length);
				HttpResponseUtils.responseBody(dos, responseBodyDate);
				return;
			}
			
			if(httpStatusCode == 302) {
				HttpResponseUtils.response302Header(dos, responseBodyDate.length, requestViewName);
				return;
			}
			
			
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private Map<String, String> parseReqeustHeader(BufferedReader request) {
		Map<String, String> requestHeader = new HashMap<>();

		try {
			String header = request.readLine();
			String[] headerInfo = header.split(Separator.WHITESPACE.getValue());
			
			requestHeader.put(HttpHeader.METHOD.getName(), headerInfo[0]);
			requestHeader.put(HttpHeader.URL.getName(), headerInfo[1]);
			
			while(StringUtils.equals(header, "") == false) {
				if(header.startsWith(HttpHeader.CONTENT_LENGTH.getName())) {
					String contentLength = HttpRequestUtils.parseHeader(header).getValue();
					requestHeader.put(HttpHeader.CONTENT_LENGTH.getName(), contentLength);
				}
				header = request.readLine();
			}

		} catch (IOException e) {
			log.error("BadRequest! Don't include Header Infomation", e);
		}

		return requestHeader;
	}

	private Map<String, String> parseRequestBody(BufferedReader bufferedReader, int contentLength) {
		try {
			String bodyData = IOUtils.readData(bufferedReader, contentLength);
			return HttpRequestUtils.parseQueryString(bodyData);

		} catch (IOException e) {
			log.error("BadRequest! Don't include Body Infomation", e);
		}

		return null;
	}

}
