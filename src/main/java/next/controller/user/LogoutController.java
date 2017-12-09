package next.controller.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.web.view.ModelAndView;
import next.constant.Session;
import next.controller.AbstractController;

public class LogoutController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute(Session.LOGIN_ID.value());

        return jspView("redirect:/index");
    }
}
