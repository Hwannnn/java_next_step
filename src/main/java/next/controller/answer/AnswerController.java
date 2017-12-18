package next.controller.answer;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.util.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Result;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    private AnswerDao answerDao;
    private QuestionDao questionDao;

    public AnswerController() {
        answerDao = AnswerDao.getInstance();
        questionDao = QuestionDao.getInstance();
    }

    @RequestMapping("/api/qna/addAnswer")
    public ModelAndView addAnswer(HttpServletRequest request, HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return null;
        }

        User user = UserSessionUtils.getUserFromSession(request.getSession());
        Answer answer = new Answer(user.getUserId(), request.getParameter("contents"), Long.parseLong(request.getParameter("questionId")));
        log.debug("AnswerController addAnswer Answer : {}", answer);

        Answer savedAnswer = answerDao.insert(answer);
        questionDao.updateCountOfAnswer(savedAnswer.getQuestionId());

        return jsonView().addObject("answer", savedAnswer).addObject("result", Result.ok());
    }

    @RequestMapping("/api/qna/deleteAnswer")
    public ModelAndView deleteAnswer(HttpServletRequest request, HttpServletResponse response) {
        Long answerId = Long.parseLong(request.getParameter("answerId"));

        ModelAndView modelAndView = jsonView();
        try {
            answerDao.delete(answerId);
            modelAndView.addObject("result", Result.ok());
        } catch (DataAccessException e) {
            modelAndView.addObject("result", Result.fail(e.getMessage()));
        }
        return modelAndView;
    }

}
