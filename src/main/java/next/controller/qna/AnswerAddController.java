package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;

public class AnswerAddController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AnswerAddController.class);

    private AnswerDao answerDao;
    private QuestionDao questionDao;

    public AnswerAddController() {
        answerDao = new AnswerDao();
        questionDao = new QuestionDao();
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long questionId = Long.parseLong(request.getParameter("questionId"));
        Answer answer = new Answer(request.getParameter("writer"), request.getParameter("contents"), questionId);

        log.debug("answer : {}", answer);

        questionDao.increaseCountOfAnswer(questionId);

        ModelAndView modelAndView = jsonView();
        modelAndView.addObject("answer", answerDao.insert(answer));
        modelAndView.addObject("countOfAnswer", questionDao.findById(questionId).getCountOfComment());

        return modelAndView;
    }
}
