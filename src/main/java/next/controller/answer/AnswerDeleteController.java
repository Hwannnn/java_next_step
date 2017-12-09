package next.controller.answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.view.ModelAndView;
import next.constant.Result;
import next.controller.AbstractController;
import next.dao.AnswerDao;

public class AnswerDeleteController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(AnswerDeleteController.class);
	private AnswerDao answerDao;
	
	public AnswerDeleteController() {
		answerDao = new AnswerDao();
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("AnswerRegisterController execute");
		
		int answerId = Integer.parseInt(request.getParameter("answerId"));
		answerDao.deleteAnswer(answerId);
		
		ModelAndView jsonView = jsonView();
		jsonView.addAttribute("result", Result.SUCCESS.name());
		
		return jsonView;
	}

}
