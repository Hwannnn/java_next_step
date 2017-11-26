package next;


import next.controller.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author changhwan-sin on 2017-11-26.
 */

public class RequestMapping {
	private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);

	public static Controller findController(String url) {
		log.debug("RequestMapping findController findController: {}", url);

		if (url.startsWith("/user/form")) {
			return new FormController("/user/form.jsp");
		}

		if (url.startsWith("/user/create")) {
			return new UserCreateController();
		}

		if (url.startsWith("/user/loginForm")) {
			return new FormController("/user/login.jsp");
		}

		if (url.startsWith("/user/login")) {
			return new LoginController();
		}

		if (url.startsWith("/user/logout")) {
			return new LogoutController();
		}

		if (url.startsWith("/user/list")) {
			return new UserListController();
		}

		if (url.startsWith("/user/profile")) {
			return new UserProfileController();
		}

		if (url.startsWith("/user/updateForm")) {
			return new UserProfileController();
		}

		if(url.startsWith("/user/update")) {
			return new UserUpdateController();
		}

		if (url.startsWith("/") || url.startsWith("")) {
			return new HomeController();
		}

		return null;
	}

}