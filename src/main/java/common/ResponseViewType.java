package common;

public enum ResponseViewType {
	HTML(".html"),
	JS(".js"),
	CSS(".css"),
	ICO(".ico");
	
	private final String extension;
	ResponseViewType(String extension) {
		this.extension = extension;
	}
	
	public static boolean isResponseViewType(String viewName) {
		ResponseViewType[] viewTypes = values();
		
		for(ResponseViewType viewType : viewTypes) {
			if(viewName.endsWith(viewType.extension)) {
				return true;
			}
		}
		
		return false;
	}
	
}
