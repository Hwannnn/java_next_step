package next.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.view.ModelAndView;
import next.controller.AbstractController;
import next.dao.QnaDao;

public class HomeController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	private QnaDao qnaDao;

	public HomeController() {
		qnaDao = new QnaDao();
	}

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("HomeController execute");

		request.setAttribute("qnas", qnaDao.selectQnas());

		return jspView("/index.jsp");
	}
}
