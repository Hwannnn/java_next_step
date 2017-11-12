package com.example2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.example2.StringCalculator;

public class StringCalculatorTest {
	@Rule
	public ExpectedException expectedExcetption = ExpectedException.none();
	
	private StringCalculator stringCalculator;
	
	@Before
	public void setBeforeJob() {
		System.out.println("Before");
		
		stringCalculator = new StringCalculator();
	}
	
	@Test
	public void addTest() {
		System.out.println("Add Test");
		
		assertEquals(0, stringCalculator.add(" "));
		assertEquals(0, stringCalculator.add(null));
		assertEquals(1, stringCalculator.add("1"));
		assertEquals(3, stringCalculator.add("1,2"));
		assertEquals(6, stringCalculator.add("1,2,3"));
		assertEquals(6, stringCalculator.add("1,2:3"));
		assertEquals(6, stringCalculator.add("//;\n1:2:3"));
		assertEquals(10, stringCalculator.add("//;\n1,2:3;4"));
		
		expectedExcetption.expect(IllegalArgumentException.class);
	    expectedExcetption.expectMessage("Argument should be not negative number, argument : -1");
	    stringCalculator.add("-1");
	}
	
	
	
	@After
	public void setAfterJob() {
		System.out.println("After");
	}
	
}
