package com.sanskar.test.controllertest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import com.sanskar.test.controller.Calculator;

//change it to LINUX and it skips all the tests
@EnabledOnOs(OS.MAC)
//changing default behaviour of creating instances per test/method
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalculatorTest {

   private Calculator calculator ;
   TestInfo testInfo;
   TestReporter testReporter;
	
	@BeforeEach
	void initEach (TestInfo testInfo,TestReporter testReporter){
		calculator = new Calculator();
		this.testInfo= testInfo;
		this.testReporter=testReporter;
	}

	@Nested
	class AddTest {
		@Test
		void testAddingTwoPositives() {
			int expected =2;
			int actual = calculator.add(1, 1);
			//using supplier (lambdas) to only run our string if the test fails as
			//generally it runs every time and is expensive piece of strung to calculate
			assertEquals(expected, actual, 
					()->"Add method should return the sum of two numbers");
		}
		
		@Test
		void testAddingTwoNegatives() {
			//using assumptions
			boolean maanlo=false;
			assumeTrue(maanlo);
			//test gets skipped as maanlo evaluates to false, only if it says true, JUnit runs the test
			assertEquals(-2, calculator.add(-1, -1), 
					"Add method should return the sum of two numbers");
		}
		
		@Test
		void testAddingAPositiveAndANegative() {
			assertEquals(0, calculator.add(-1, 1), 
					"Add method should return the sum of two numbers");
		}
	}
	
	@Test 
	@Tag("Math")
	@DisplayName("Multiply Method")
	void testMultiply() {
		System.out.println("Running" + testInfo.getDisplayName() + "with tag" + testInfo.getTags() + "in classs" + testInfo.getClass());
		testReporter.publishEntry("Running" + testInfo.getDisplayName() + "with tag" + testInfo.getTags() + "in classs" + testInfo.getClass());
		assertAll(
				() -> assertEquals(0, calculator.multiply(1, 0)),
				() -> assertEquals(1, calculator.multiply(1, 1)),
				() -> assertEquals(6, calculator.multiply(2, 3))
				);
	}
	

	@RepeatedTest(5)
	void computeCircleArea(RepetitionInfo repInfo) {
		//getting hold of repetitions and playing programmatically with it
		if(repInfo.getCurrentRepetition()==1) {
			assertEquals(314.1592653589793, calculator.computeCircleArea(10));
			//shouldn't use sopln but using just to demonstrate power of @RepeatedTest
			System.out.println("Should return right circle area using Repetition Argument");
			}
		assertEquals(314.1592653589793, calculator.computeCircleArea(10), "Should return right circle area");
		System.out.println(repInfo.getCurrentRepetition());
	}

	
	@Test
	void testDivide() {
		//put IOException at place of ArithmeticException and this test won't run, it fails
		assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0), 
				"Divide should throw ArithmeticException when denominator is zero");
	}
	
}
