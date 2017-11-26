package next.controller;

import core.db.DataBase;
import next.constant.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserListController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute(Session.LOGIN_ID.value());

		boolean isNotLogined = (loginId == null);
		if (isNotLogined) {
			return "redirect:/user/loginForm";
		}

		request.setAttribute("users", DataBase.findAll());

		return "/user/list.jsp";
	}
}
