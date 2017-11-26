package next.web;

import core.db.DataBase;
import next.model.User;

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

@WebServlet("/user/logout")
public class LogoutUserServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String userId = request.getParameter("userId");

		HttpSession session = request.getSession();
		session.removeAttribute(userId);

		response.sendRedirect("/index.jsp");
	}

}
