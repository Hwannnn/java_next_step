package next;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.constant.CommonView;
import next.constant.Gap;
import next.constant.HttpStatus;
import next.controller.Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String viewName = "";
		
		try {
			log.debug("DispatcherServlet service requestUrl : {}", requestUrl);
			
			Controller controller = RequestMapping.findController(requestUrl);
			if(controller == null) {
				response.sendError(HttpStatus.NOT_FOUND.getValue());
				return;
			}

			viewName = controller.execute(request, response);

		} catch (Exception exception) {
			log.debug("DispatcherServlet service requestUrl Error url : {}", requestUrl, exception);
			viewName = CommonView.ERROR_VIEW.value();
		}
		
		showView(request, response, viewName);
	}

	public void showView(HttpServletRequest request, HttpServletResponse response, String viewName) {
		try {
			if(viewName.startsWith("redirect:")) {
				response.sendRedirect(viewName.replace("redirect:", Gap.EMPTY.value()));
				return;
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
			requestDispatcher.forward(request, response);

		} catch (Exception exception) {
			log.debug("DispatcherServlet redirect, forward viewName: {}", viewName, exception);
		}
	}
}
