package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.constant.Session;
import next.dao.UserDao;
import next.model.User;

public class LoginController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	private UserDao userDao;
	
	public LoginController() {
		userDao = new UserDao();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("LoginController execute");

		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		User user = userDao.selectUser(userId);
		boolean canNotLogin = (user == null) || (user.matchPassword(password) == false);
		if (canNotLogin) {
			return "/user/login.jsp";
		}

		HttpSession session = request.getSession();
		session.setAttribute(Session.LOGIN_ID.value(), user.getUserId());

		return "redirect:/user/list";
	}
}
