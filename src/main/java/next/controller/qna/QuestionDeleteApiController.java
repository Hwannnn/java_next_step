package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.util.CertifyUtils;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionDeleteApiController extends AbstractController {
    private QuestionDao questionDao;

    public QuestionDeleteApiController() {
        questionDao = new QuestionDao();
    }


    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(CertifyUtils.isDeletedQUestion(request) == false) {
            return jspView("redirect:/error.jsp");
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        questionDao.deleteQustion(questionId);

        return jspView("redirect:/");
    }
}
