package next;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.Controller;
import next.controller.answer.AnswerDeleteController;
import next.controller.answer.AnswerRegisterController;
import next.controller.common.FormController;
import next.controller.common.HomeController;
import next.controller.qna.QnaRegisterController;
import next.controller.qna.QnaShowController;
import next.controller.user.LoginController;
import next.controller.user.LogoutController;
import next.controller.user.UserCreateController;
import next.controller.user.UserListController;
import next.controller.user.UserProfileController;
import next.controller.user.UserUpdateController;


/**
 * @author changhwan-sin on 2017-11-26.
 */

public class RequestMapping {
	private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);

	public static Controller findController(String url) {
		log.debug("RequestMapping findController url: {}", url);

		if (url.startsWith("/index") || url.startsWith("/index.jsp")) {
			return new HomeController();
		}
		
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
		
		if(url.startsWith("/qna/form")) {
			return new FormController("/qna/form.jsp");
		}

		if(url.startsWith("/qna/register")) {
			return new QnaRegisterController();
		}
		
		if(url.startsWith("/qna/show")) {
			return new QnaShowController();
		}
		
		if(url.startsWith("/answer/register")) {
			return new AnswerRegisterController();
		}
		
		if(url.startsWith("/answer/delete")) {
			return new AnswerDeleteController();
		}
		
		if(url.startsWith("/") || url.startsWith("")) {
			return new HomeController();
		}
		
		return null;
	}

}