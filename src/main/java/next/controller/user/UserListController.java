package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.web.view.ModelAndView;
import next.constant.Session;
import next.controller.AbstractController;
import next.dao.UserDao;

public class UserListController extends AbstractController {
	private UserDao userDao;
	
	public UserListController() {
		userDao = new UserDao();
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute(Session.LOGIN_ID.value());

		boolean isNotLogined = (loginId == null);
		if (isNotLogined) {
			return jspView("redirect:/user/loginForm");
		}

		request.setAttribute("users", userDao.selectUsers());

		return jspView("/user/list.jsp");
	}
}
