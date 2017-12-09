package core.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonView implements View {
	private ObjectMapper mapper;

	public JsonView() {
		this.mapper = new ObjectMapper();
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(mapper.writeValueAsString(model));
	}

}
