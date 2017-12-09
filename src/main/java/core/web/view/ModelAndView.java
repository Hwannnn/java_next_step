package core.web.view;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
	private View view;
	private Map<String, Object> model = new HashMap<>();
	
	public ModelAndView(View view) {
		this.view = view;
	}
	
	public void addAttribute(String key, Object value) {
		this.model.put(key, value);
	}
	
	public View getView() {
		return this.view;
	}
	
	public Map<String, Object> getModel() {
		return this.model;
	}
	
}
