package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public class FormController implements Controller {
	private String viewName;

	public FormController(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return this.viewName;
	}

}
