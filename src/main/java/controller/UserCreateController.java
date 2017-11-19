package controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import common.HttpMethod;
import util.Request;
import util.Response;
import db.DataBase;
import model.User;

public class UserCreateController implements Controller {

	@Override
	public String service(Request request, Response response) {
		String requestMethod = request.getMethod();
		
		if(StringUtils.equalsIgnoreCase(requestMethod, HttpMethod.GET.name())) {
			return "/user/form.html";
		}
		
		if(StringUtils.equalsIgnoreCase(requestMethod, HttpMethod.POST.name())) {
			Map<String, String> body = request.getBody();
			
			String userId = body.get("userId");
			String password = body.get("password");
			String name = body.get("name");
			String email = body.get("email");

			User user = new User(userId, password, name, email);
			DataBase.addUser(user);
			
			return "redirect:/index.html";
		}
		
		return null;
	}

}
