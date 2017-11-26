package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author changhwan-sin on 2017-11-26.
 */

public interface Controller {
	String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
