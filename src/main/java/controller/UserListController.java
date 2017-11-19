package controller;

import util.Request;
import util.Response;
import db.DataBase;
import model.User;
import util.HttpRequestUtils;

public class UserListController implements Controller {
	@Override
	public String service(Request request, Response response) {
		String cookies = request.getCookie();
		String isLogined = HttpRequestUtils.parseCookies(cookies).get("logined");
		
		if (Boolean.parseBoolean(isLogined)) {
			StringBuilder users = new StringBuilder();

			users.append("<html><head></head><body>");
			for (User user : DataBase.findAll()) {
				users.append(user.getUserId() + "/" + user.getName() + "/" + user.getEmail() + "\r\n");	
			}
			users.append("</body></html>");
			
			return "responseBody:" + users.toString();
		}
		
		return "/user/login.html";
	}

}
