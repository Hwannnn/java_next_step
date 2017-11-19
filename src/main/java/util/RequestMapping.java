package util;

import controller.Controller;
import controller.UserCreateController;
import controller.UserListController;
import controller.UserLoginController;

public enum RequestMapping {
	USER_FORM("/user/form") {
		@Override
		public Controller getController() {
			return new UserCreateController();
		}
		
	},
	
	USER_JOIN("/user/create") {
		@Override
		public Controller getController() {
			return new UserCreateController();
		}
	},
	
	USER_LOGIN("/user/login") {
		@Override
		public Controller getController() {
			return new UserLoginController();
		}
	},
	
	USER_LIST("/user/list") {
		@Override
		public Controller getController() {
			return new UserListController();
		}
	};
	
	private final String url;
	RequestMapping(String url) {
		this.url = url;
	}
	
	private String getUrl() {
		return this.url;
	}
	
	public static Controller findController(String requestUrl) {
		for(RequestMapping url : values()) {
			if(requestUrl.startsWith(url.getUrl())) {
				return url.getController();
			}
		}
		
		return null;
	}
	
	
	abstract protected Controller getController();
}
