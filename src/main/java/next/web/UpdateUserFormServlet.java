package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author changhwan-sin on 2017-11-26.
 */
@WebServlet("/user/updateForm")
public class UpdateUserFormServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String loginId = (String) session.getAttribute("loginId");
		String userId = request.getParameter("userId");

		boolean canUpdate = (loginId != null) && (loginId.equals(userId));
		if(canUpdate) {
			User foundUser = DataBase.findUserById(loginId);
			request.setAttribute("user", foundUser);
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/update.jsp");
		requestDispatcher.forward(request, response);
	}
}
