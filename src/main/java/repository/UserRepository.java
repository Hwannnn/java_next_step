package repository;

import model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author changhwan-sin on 2017-11-13.
 */

public class UserRepository {
	private static Map<String, User> userRepository = new HashMap<>();

	private UserRepository() {}

	public static Map<String, User> getRepository() {
		return userRepository;
	}
}
