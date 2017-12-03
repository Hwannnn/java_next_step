package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.constant.CommonView;
import next.constant.Session;
import next.dao.UserDao;
import next.model.User;

public class UserProfileController implements Controller {
	private UserDao userDao;
	
	public UserProfileController() {
		userDao = new UserDao();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute(Session.LOGIN_ID.value());

		User loginedUser = userDao.selectUser(loginId);
		if (loginedUser == null) {
			return CommonView.ERROR_VIEW.value();
		}

		request.setAttribute("user", loginedUser);

		if(request.getRequestURI().contains("update")) {
			return "/user/updateForm.jsp";
		} else {
			return "/user/profile.jsp";
		}
	}
}
