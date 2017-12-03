package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.constant.Session;
import next.dao.UserDao;

public class UserListController implements Controller {
	private UserDao userDao;
	
	public UserListController() {
		userDao = new UserDao();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute(Session.LOGIN_ID.value());

		boolean isNotLogined = (loginId == null);
		if (isNotLogined) {
			return "redirect:/user/loginForm";
		}

		request.setAttribute("users", userDao.selectUsers());

		return "/user/list.jsp";
	}
}
