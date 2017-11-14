package service;

import model.User;
import repository.UserRepository;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author changhwan-sin on 2017-11-13.
 */

public class UserService {
	public String joinForm() {
		return "/user/form.html";
	}
	
	public String joinUser(Map<String, String> body) {
		String userId = body.get("userId");
		String password = body.get("password");
		String name = body.get("name");
		String email = body.get("email");

		User user = new User(userId, password, name, email);
		UserRepository.getRepository().put(userId, user);
		
		return "/index.html";
	}
	
	public String loginUser(Map<String, String> body) {
		String userId = body.get("userId");
		String password = body.get("password");
		
		if(canLoginUser(userId, password) == false) {
			return "/user/login_failed.html";
		}
		
		return "/index.html";
	}
	
	private boolean canLoginUser(String userId, String userPassword) {
		User user = UserRepository.getRepository().get(userId);
		if(user == null || StringUtils.equals(user.getPassword(), userPassword)) {
			return false;
		}
		
		return true;
	}
	
}
