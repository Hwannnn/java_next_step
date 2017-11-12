package com.example2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringCalculator {
	private List<String> separators;
	private static final String CUSTOM_SEPARATOR_FORMAT = "//(.)\n(.*)";
	private static final String ZERO = "0";
	
	public StringCalculator() {
		separators = new ArrayList<String>();
		separators.add(",");
		separators.add(":");
	}
	
	public int add(String expression) {
		if(StringUtils.isBlank(expression)) {
			return 0;
		}
		
		String[] arguments = extractArguments(expression);
		
		checkNegativeNumber(arguments);
		
		int argumentSum = 0;
		for(String argument : arguments) {
			if(StringUtils.isNumeric(argument) == false) {
				String errorMessage = String.format("Argument should be Numeric, argument : %s" + argument );
				throw (new IllegalArgumentException(errorMessage));
			}
			argumentSum += Integer.parseInt(argument);
		}
		
		return argumentSum;
	}
	
	private String[] extractArguments(String expression) {
		String pureExpression = splitCustomSeparator(expression);
		String[] arguments = splitMultipleSeparator(pureExpression);
		
		return arguments;
	}
	
	private String splitCustomSeparator(String expression) {
		Matcher matcher = Pattern.compile(CUSTOM_SEPARATOR_FORMAT).matcher(expression);
		if(matcher.find()) {
			setCustomSeparator(matcher.group(1));
			return matcher.group(2);
		}
		
		return expression;
	}
	
	private void setCustomSeparator(String customSeparator) {
		separators.add(customSeparator);
	}
	
	private String[] splitMultipleSeparator(String expression) {
		String multipleSeparator = StringUtils.join(separators, '|');
		String[] separatedArguments = expression.split(multipleSeparator);
		
		return separatedArguments;
	}
	
	private void checkNegativeNumber(String[] arguments) {
		for(String argument : arguments) {
			if(Integer.parseInt(argument) < 0) {
				String errorMessage = String.format("Argument should be not negative number, argument : %s", argument);
				throw (new IllegalArgumentException(errorMessage));
			}
		}
	}

	public static void main(String[] args) {
		StringCalculator asd = new StringCalculator();
		String test = null;
		
		System.out.println(asd.add(test));
	}
}
