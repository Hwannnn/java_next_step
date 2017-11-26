package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author changhwan-sin on 2017-11-26.
 */

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");

		if (DataBase.findUserById(loginId) == null) {
			response.sendRedirect("/user/login");
			return;
		}

		String userPassword = request.getParameter("password");
		String userName = request.getParameter("name");
		String userEmail = request.getParameter("email");

		User updatedUser = new User(loginId, userPassword, userName, userEmail);
		DataBase.addUser(updatedUser);

		response.sendRedirect("/user/list");
	}
}
