package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import repository.UserRepository;
import common.HttpHeader;
import common.HttpStatusCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import service.UserService;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private static final String EMPTY = "";
	private static final String WHITESPACE = " ";
	private static final String WEBAPP_DIRECTORY = "./webapp";
	private static final String URL_QUERY_SEPARATOR = "?";

	private Socket connection;
	private UserService userService;

	public RequestHandler(Socket connectionSocket) {
		userService = new UserService();
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
				connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			BufferedReader request = new BufferedReader(new InputStreamReader(in));
			boolean isRedirect = false;
			String httpStatusCode = 200 + " OK";

			Map<String, String> requestHeader = parseHeader(request);
			String requestMethod = requestHeader.get(HttpHeader.METHOD.getName());
			String requestUrl = requestHeader.get(HttpHeader.URL.getName());
			String requestViewName = StringUtils.substringBefore(requestUrl, URL_QUERY_SEPARATOR);

			if (StringUtils.equalsIgnoreCase(requestMethod, "POST")) {
				HttpRequestUtils.Pair contentLengthInfo = HttpRequestUtils.parseHeader(HttpHeader.CONTENT_LENGTH.getName());
				int contentLength = Integer.parseInt(contentLengthInfo.getValue());

				if (requestUrl.startsWith("/user/form")) {
					Map<String, String> body = parseBody(request, contentLength);
					userService.joinUser(body);
					httpStatusCode = 302 + " Found";
				}
			}

			byte[] resposeBody = Files.readAllBytes(Paths.get(WEBAPP_DIRECTORY, requestViewName));

			DataOutputStream dos = new DataOutputStream(out);
			responseHeader(dos, resposeBody.length, httpStatusCode);
			responseBody(dos, resposeBody);

		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private Map<String, String> parseHeader(BufferedReader request) {
		Map<String, String> requestHeader = new HashMap<>();

		try {
			String header = request.readLine();
			String[] headerInfo = header.split(WHITESPACE);

			requestHeader.put(HttpHeader.METHOD.name(), headerInfo[0]);
			requestHeader.put(HttpHeader.URL.name(), headerInfo[1]);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return requestHeader;
	}

	private Map<String, String> parseBody(BufferedReader bufferedReader, int contentLength) {
		try {
			String bodyData = IOUtils.readData(bufferedReader, contentLength);
			return HttpRequestUtils.parseQueryString(bodyData);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, HttpStatusCode statuscode) {
		try {
			dos.writeBytes("HTTP/1.1 " + statuscode.getStatusCode() + " \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");

			if (statuscode == HttpStatusCode.STATUS_302) {
				dos.writeBytes("Location: /index.html\r\n");
			}

			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

}
