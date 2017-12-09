package next.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.web.view.ModelAndView;
import next.controller.AbstractController;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public class FormController extends AbstractController {
	private String viewName;

	public FormController(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return jspView(this.viewName);
	}

}
