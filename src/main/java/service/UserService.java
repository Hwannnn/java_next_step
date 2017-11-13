package service;

import model.User;
import repository.UserRepository;

import java.io.BufferedReader;
import java.util.Map;

/**
 * @author changhwan-sin on 2017-11-13.
 */

public class UserService {
	public void joinUser(Map<String, String> body) {
		String userId = body.get("userId");
		String password = body.get("password");
		String name = body.get("name");
		String email = body.get("email");

		User user = new User(userId, password, name, email);
		UserRepository.getRepository().add(user);
	}
	
	public boolean loginUser(Map<String, String> body) {
		
		
		return false;
	}
	
}
