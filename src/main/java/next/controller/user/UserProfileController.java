package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.web.view.ModelAndView;
import next.constant.CommonView;
import next.constant.Session;
import next.controller.AbstractController;
import next.dao.UserDao;
import next.model.User;

public class UserProfileController extends AbstractController {
	private UserDao userDao;
	
	public UserProfileController() {
		userDao = new UserDao();
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute(Session.LOGIN_ID.value());

		User loginedUser = userDao.selectUser(loginId);
		if (loginedUser == null) {
			return jspView(CommonView.ERROR_VIEW.value());
		}

		request.setAttribute("user", loginedUser);

		if(request.getRequestURI().contains("update")) {
			return jspView("/user/updateForm.jsp");
		} else {
			return jspView("/user/profile.jsp");
		}
	}
}
