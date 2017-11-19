package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import util.Request;
import util.Response;
import controller.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.RequestMapping;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

		try (InputStream in = connection.getInputStream();
			OutputStream out = connection.getOutputStream()) {
			
			BufferedReader requestBuffer = new BufferedReader(new InputStreamReader(in, "utf-8"));
			DataOutputStream responseStream = new DataOutputStream(out);
		
			Request request = new Request(requestBuffer);
			Response response = new Response(responseStream);
				
			service(request, response);
			
		} catch (IOException e) {
			log.debug("RequestHandler run ERROR", e);
		}
	}
	
	public void service(Request request, Response response) {
		String requestUrl = request.getRequestUrl();
		Controller controller = RequestMapping.findController(requestUrl);
		
		String view;
		if(controller == null) {
			view = requestUrl;
		} else {
			view = controller.service(request, response);
		}

		if(view.startsWith("redirect:")) {
			response.sendRedirect(view.replace("redirect:", ""));
			
		} else if(view.startsWith("responseBody:")) {
			response.sendForwardBody(view.replace("responseBody:", ""));
			
		} else {
			response.sendForward(view);
		}
	}

}
