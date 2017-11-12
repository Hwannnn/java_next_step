package com.example2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
	private Calculator calculator;
	
	@Before
	public void setBeforeJob() {
		System.out.println("Before");
		
		calculator = new Calculator();	
	}
	
	@Test
	public void addTest() {
		System.out.println("Add Test");
		
		assertEquals(9, calculator.add(6, 3));
	}
	
	@Test
	public void subtractTest() {
		System.out.println("Subtract Test");

		assertEquals(3, calculator.subtract(6, 3));
	}
	
	@After
	public void setAfterJob() {
		System.out.println("After");
	}
	
}
