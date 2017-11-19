package controller;

import util.Request;
import util.Response;

public class IndexController implements Controller {
	@Override
	public String service(Request request, Response response) {
		return "./webapp/index.html";
	}
}
