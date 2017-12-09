package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.view.ModelAndView;
import next.controller.AbstractController;
import next.dao.QnaDao;
import next.model.Qna;

public class QnaRegisterController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(QnaRegisterController.class);
	private QnaDao qnaDao;
	
	public QnaRegisterController() {
		qnaDao = new QnaDao();
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("QnaRegisterController execute");
		
		Qna qna = new Qna();
		qna.setTitle(request.getParameter("title"));
		qna.setWriter(request.getParameter("writer"));
		qna.setContents(request.getParameter("contents"));
		
		qnaDao.insertQna(qna);

		return jspView("redirect:/index");
	}
}
