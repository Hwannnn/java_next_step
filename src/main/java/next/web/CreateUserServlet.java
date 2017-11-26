package next.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;

@WebServlet("/user/create")
public class CreateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CreateUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String userPassword = request.getParameter("password");
        String userName = request.getParameter("name");
        String userEmail = request.getParameter("email");

        User newUser = new User(userId, userPassword, userName, userEmail);
        DataBase.addUser(newUser);

        log.debug("Create User : {}", newUser.getUserId());

        response.sendRedirect("/user/list");
    }
}
