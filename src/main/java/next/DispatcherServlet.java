package next;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.view.ModelAndView;
import core.web.view.View;
import next.constant.HttpStatus;
import next.controller.Controller;

/**
 * @author changhwan-sin on 2017-11-26.
 */

@WebServlet(urlPatterns = "/", name="dispatcher", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		String requestUrl = request.getRequestURI();
		
		try {
			log.debug("DispatcherServlet service requestUrl : {}", requestUrl);
			
			Controller controller = RequestMapping.findController(requestUrl);
			if(controller == null) {
				response.sendError(HttpStatus.NOT_FOUND.getValue());
				return;
			}

			ModelAndView modelAndView = controller.execute(request, response);
			
			View view = modelAndView.getView();
			view.render(request, response, modelAndView.getModel());

		} catch (Exception e) {
			log.debug("DispatcherServlet service Error url : {}", requestUrl, e);
			
		}
	}
	
}
