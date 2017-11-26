package next.controller;

import core.db.DataBase;
import next.constant.CommonView;
import next.constant.Session;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserProfileController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute(Session.LOGIN_ID.value());

		User loginedUser = DataBase.findUserById(loginId);
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
