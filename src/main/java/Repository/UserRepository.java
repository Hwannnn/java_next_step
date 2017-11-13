package repository;

import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author changhwan-sin on 2017-11-13.
 */

public class UserRepository {
	private static List<User> userRepository = new ArrayList<>();

	private UserRepository() {}

	public static List<User> getRepository() {
		return userRepository;
	}
}
