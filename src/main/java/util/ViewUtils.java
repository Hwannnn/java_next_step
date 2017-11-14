package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;

public class ViewUtils {
	private static Logger log = LoggerInstanceFactory.getInstance(ViewUtils.class);
	
	public ViewUtils() {
		
	}
	
	public static byte[] viewFileBytes(String viewName) {
		byte[] viewFilebytes = null;
		
		try {
			viewFilebytes = Files.readAllBytes(Paths.get("./webapp", viewName));
		
		} catch (IOException e) {
			String errorMessage = String.format("ViewUtils viewFileBytes ERROR viewName: %s", viewName);
			log.debug(errorMessage, e);
		}
		
		return viewFilebytes;
	}

}
