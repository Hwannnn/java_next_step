package core.util;

import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CertifyUtils {
    private static boolean isVaildSession(HttpSession session) {
        if(session == null) {
            return false;
        }

        return true;
    }

    public static boolean isLogined(HttpSession session) {
        if(isVaildSession(session) == false) {
            return false;
        }

        User loginUser = (User) session.getAttribute("user");
        if(loginUser == null) {
            return false;
        }

        return true;
    }

    public static String getLoginUserName(HttpSession session) {
        if(isLogined(session) == false) {
            return null;
        }

        return ((User) session.getAttribute("user")).getName();
    }

    public static boolean isSameUser(HttpSession session, String writer) {
        String loginUserName = getLoginUserName(session);
        if(writer.equals(loginUserName) == false) {
            return false;
        }

        return true;
    }

    public static boolean isDeletedQUestion(HttpServletRequest request) {
        boolean isSameUser = isSameUser(request.getSession(), request.getParameter("writer"));
        if(isSameUser == false) {
            return false;
        }

        int countOfAnswer = Integer.parseInt(request.getParameter("countOfAnswer"));
        if(countOfAnswer > 0) {
            return false;
        }

        return true;
    }
    
}
