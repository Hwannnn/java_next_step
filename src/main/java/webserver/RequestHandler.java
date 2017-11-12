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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    
    private static final String EMPTY = "";
    private static final String SPACE = " ";
    private static final String SUFFIX_HTML = ".html";
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
        	String requestUrl = extractRequestUrl(in);
        	String requestPath = StringUtils.substringBefore(requestUrl, "?");
        	String requestParameters = StringUtils.substringAfter(requestUrl, "?");
        	
        	User user = putUserInfo(requestParameters);
        	
        	File readResponseFile = readResponseFile(requestPath);
        	byte[] resposeBody = Files.readAllBytes(readResponseFile.toPath());
        	
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, resposeBody.length);
            responseBody(dos, resposeBody);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    
    private User putUserInfo(String parameters) {
    	Map<String, String> mappingParameters = HttpRequestUtils.parseQueryString(parameters);
    	String userId = mappingParameters.get("userId");
    	String password = mappingParameters.get("password");
    	String name = mappingParameters.get("name");
    	String email = mappingParameters.get("email");
    	
    	return new User(userId, password, name, email);
    }

    private File readResponseFile(String requestUrl) {
    	String htmlFilePath = String.format("./webapp%s", requestUrl);
    	File htmlFile = new File(htmlFilePath);
    	
    	return htmlFile;
    }
    
    private String extractRequestUrl(InputStream in) {
    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
    	String headerLine;
    	
		try {
			headerLine = bufferedReader.readLine();
			
			//while(StringUtils.equals(headerLine, EMPTY) == false) {
			//	System.out.println(headerLine);
			//	headerLine = bufferedReader.readLine();
			//}
			
			return StringUtils.substringBetween(headerLine, SPACE, SPACE);
			//return null;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
    	
    	return null;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
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
