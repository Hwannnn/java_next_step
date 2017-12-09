package core.web.view;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.constant.CommonView;
import next.constant.Gap;

public class JspView implements View {
	private String viewName;

	public JspView(String viewName) {
		if (viewName == null) {
			viewName = CommonView.ERROR_VIEW.name();
		}

		this.viewName = viewName;
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		if (viewName.startsWith("redirect:")) {
			response.sendRedirect(viewName.replace("redirect:", Gap.EMPTY.value()));
			return;
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewName);
		requestDispatcher.forward(request, response);

	}

}
