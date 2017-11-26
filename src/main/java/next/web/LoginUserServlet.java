package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author changhwan-sin on 2017-11-26.
 */

@WebServlet("/user/login")
public class LoginUserServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String userId = request.getParameter("userId");
		String userPassword = request.getParameter("password");

		User foundUser = DataBase.findUserById(userId);

		boolean canNotLogin = (foundUser == null) || (!foundUser.getPassword().equals(userPassword));
		if(canNotLogin) {
			response.sendRedirect("/user/login.jsp");
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute("loginId", userId);

		response.sendRedirect("/index.jsp");
	}

}
