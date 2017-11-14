package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerInstanceFactory {
	public static Logger getInstance(Class<?> classInstance) {
		return LoggerFactory.getLogger(classInstance);
	}
	
}
