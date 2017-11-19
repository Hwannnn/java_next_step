package controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import common.HttpHeader;
import common.HttpMethod;
import util.Request;
import util.Response;
import db.DataBase;
import model.User;

public class UserLoginController implements Controller {
	@Override
	public String service(Request request, Response response) {
		String requestMethod = request.getMethod();
		
		if(StringUtils.equalsIgnoreCase(requestMethod, HttpMethod.GET.name())) {
			return "/user/login.html";
		}
		
		if(StringUtils.equalsIgnoreCase(requestMethod, HttpMethod.POST.name())) {
			Map<String, String> body = request.getBody();
			
			String userId = body.get("userId");
			String password = body.get("password");
			
			if(canLoginUser(userId, password) == false) {
				return "/user/login_failed.html";
			}
			
			response.addHeaders(HttpHeader.SET_COOKIE, "logined=true");
			
			return "redirect:/index.html";
		}
		
		return null;
	}
	
	private boolean canLoginUser(String userId, String userPassword) {
		User user = DataBase.findUserById(userId);
		
		if((user == null) || (StringUtils.equals(user.getPassword(), userPassword) == false)) {
			return false;
		}
		
		return true;
	}

}
