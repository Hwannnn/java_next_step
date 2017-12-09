package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.web.view.ModelAndView;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public interface Controller {
	ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
