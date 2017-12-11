package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QuestionUpdateFormController extends AbstractController {
    private QuestionDao questionDao;

    public QuestionUpdateFormController() {
        this.questionDao = new QuestionDao();
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(request.getParameter("questionId"));

        Question question = questionDao.findById(questionId);

        ModelAndView modelAndView = jspView("/qna/update.jsp");
        modelAndView.addObject("question", question);

        return modelAndView;
    }
}
