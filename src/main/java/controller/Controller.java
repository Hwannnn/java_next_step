package controller;

import util.Request;
import util.Response;

public interface Controller {
	String service(Request request, Response response);
}
