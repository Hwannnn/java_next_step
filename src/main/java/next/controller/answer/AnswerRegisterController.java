package next.controller.answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.view.ModelAndView;
import next.controller.AbstractController;
import next.dao.AnswerDao;
import next.model.Answer;

public class AnswerRegisterController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(AnswerRegisterController.class);
	private AnswerDao answerDao;
	
	public AnswerRegisterController() {
		answerDao = new AnswerDao();
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("AnswerRegisterController execute");
		
		Answer answer = new Answer();
		answer.setWriter(request.getParameter("writer"));
		answer.setContents(request.getParameter("contents"));
		answer.setQuestionId(Integer.parseInt(request.getParameter("questionId")));
		
		int insertedAnswerId = answerDao.insertAnswer(answer);
		
		ModelAndView jsonView = jsonView();
		jsonView.addAttribute("answer", answerDao.selectAnswer(insertedAnswerId));
		
		return jsonView;
	}
}
