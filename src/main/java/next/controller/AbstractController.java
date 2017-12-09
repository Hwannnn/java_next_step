package next.controller;

import core.web.view.JsonView;
import core.web.view.JspView;
import core.web.view.ModelAndView;

public abstract class AbstractController implements Controller {
	protected ModelAndView jspView(String url) {
		return new ModelAndView(new JspView(url));
	}
	
	protected ModelAndView jsonView() {
		return new ModelAndView(new JsonView());
	}
	
}
