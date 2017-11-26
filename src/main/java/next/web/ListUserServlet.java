package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;

@WebServlet("/user/list")
public class ListUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("loginId");

        boolean isNotLogined = (loginId == null);
        if(isNotLogined) {
            response.sendRedirect("/user/login.jsp");
            return;
        }

        request.setAttribute("users", DataBase.findAll());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/list.jsp");
        requestDispatcher.forward(request, response);
    }
}
