package next;

import next.constant.CommonView;
import next.constant.Gap;
import next.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {
		String requestUrl = request.getRequestURI();
		try {
			Controller controller = RequestMapping.findController(requestUrl);
			if(controller == null) {
				response.sendError(404);
				return;
			}

			String viewName = controller.execute(request, response);
			showView(request, response, viewName);

		} catch (Exception exception) {
			log.debug("DispatcherServlet service requestUrl: {}", requestUrl, exception);
			showView(request, response, CommonView.ERROR_VIEW.value());
		}
	}

	public void showView(HttpServletRequest request, HttpServletResponse response, String viewName) {
		try {
			if(viewName.startsWith("redirect:")) {
				response.sendRedirect(viewName.replace("redirect:", Gap.EMPTY.value()));
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
			requestDispatcher.forward(request, response);

		} catch (Exception exception) {
			log.debug("DispatcherServlet forwardView viewName: {}", viewName, exception);
		}
	}
}
