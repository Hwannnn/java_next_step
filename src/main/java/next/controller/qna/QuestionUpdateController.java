package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.util.CertifyUtils;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionUpdateController extends AbstractController {
    private QuestionDao questionDao;

    public QuestionUpdateController() {
        this.questionDao = new QuestionDao();
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean isSameUser = CertifyUtils.isSameUser(request.getSession(), request.getParameter("writer"));
        if(isSameUser == false) {
            return jspView("redirect:/error.jsp");
        }

        long questionId = Long.parseLong(request.getParameter("questionId"));
        Question question = new Question(questionId, request.getParameter("writer"), request.getParameter("title"), request.getParameter("contents"));
        questionDao.update(question);

        return jspView("redirect:/");
    }
}
