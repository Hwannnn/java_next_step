package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.view.ModelAndView;
import next.controller.AbstractController;
import next.dao.UserDao;
import next.model.User;

public class UserCreateController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(UserCreateController.class);
	private UserDao userDao;
	
	public UserCreateController() {
		userDao = new UserDao();
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		User user = new User(userId, password, name, email);
		userDao.insertUser(user);
		
		log.debug("User : {}", user);

		return jspView("redirect:/index");
	}
}