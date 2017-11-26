package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.constant.Session;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("LoginController execute");

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		User user = DataBase.findUserById(userId);
		boolean canNotLogin = (user == null) || (user.matchPassword(password) == false);
		if (canNotLogin) {
			return "/user/login.jsp";
		}

		HttpSession session = request.getSession();
		session.setAttribute(Session.LOGIN_ID.value(), user.getUserId());

		return "redirect:/user/list";
	}
}
