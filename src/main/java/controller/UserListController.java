package controller;

import util.Request;
import util.Response;
import db.DataBase;
import model.User;
import util.HttpRequestUtils;

public class UserListController implements Controller {
	@Override
	public String service(Request request, Response response) {
		String isLogined = (String) request.getSession().getAttribute("loginId");
		
		if (isLogined == null) {
			return "/user/login.html";
		}

		StringBuilder users = new StringBuilder();

		users.append("<html><head></head><body>");
		for (User user : DataBase.findAll()) {
			users.append(user.getUserId() + "/" + user.getName() + "/" + user.getEmail() + "\r\n");
		}
		users.append("</body></html>");

		return "responseBody:" + users.toString();
	}

}
