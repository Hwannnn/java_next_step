package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.constant.CommonView;
import next.dao.UserDao;
import next.model.User;

public class UserUpdateController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(UserUpdateController.class);
	private UserDao userDao;
	
	public UserUpdateController() {
		userDao = new UserDao();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");
		String userId = request.getParameter("userId");

		boolean canNotUpdate = (loginId == null) || (userDao.selectUser(loginId) == null);

		if (canNotUpdate) {
			return CommonView.ERROR_VIEW.value();
		}

		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		User updatedUser = new User(userId, password, name, email);
		
		UserDao userDao = new UserDao();
		userDao.updateUser(updatedUser);

		log.debug("Update User : {}", updatedUser);

		return "redirect:/index.jsp";
	}
}
