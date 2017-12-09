package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.view.ModelAndView;
import next.controller.AbstractController;
import next.dao.AnswerDao;
import next.dao.QnaDao;

public class QnaShowController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(QnaShowController.class);
	private QnaDao qnaDao;
	private AnswerDao answerDao;
	
	public QnaShowController() {
		qnaDao = new QnaDao();
		answerDao = new AnswerDao();
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("QnaShowController execute");

		int questionId = Integer.parseInt(request.getParameter("questionId"));

		request.setAttribute("qna", qnaDao.selectQna(questionId));
		request.setAttribute("answers", answerDao.selectAnswers(questionId));

		return jspView("/qna/show.jsp");
	}
}
