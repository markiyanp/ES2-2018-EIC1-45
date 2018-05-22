package jMetal.tests;

import jMetal.*;

public class TestjMetal {
	public static void main(String[] args) {
		Object[][] testD = {{"test1-should-not-appear", "Double", "-1.1", "2.2", null, false },
						   {"test2-should-appear", "Double", "-2.0", "2.0", null, true},
						   {"test3-should-appear", "Double", "-10.0", "10.0", null, true},
						   {"test4-should-not-appear", "Integer", "-10", "10", null, false}};
		OptimizationProcess.runOptimization(testD, "NSGAII", false);
		
		Object[][] testI = {{"test1-should-appear", "Integer", "-1", "2", null, true },
				   {"test2-should-appear", "Integer", "-2", "5", null, true},
				   {"test3-should-appear", "Integer", "-10", "10", null, true},
				   {"test4-should-not-appear", "Integer", "-10", "10", null, false}};
		OptimizationProcess.runOptimization(testI, "NSGAII", false);
		//SMSEMOA
		
		Object[][] testB = {{"test1-should-appear", "Binary", "-1", "2", null, true },
				   {"test2-should-appear", "Binary", "-2", "5", null, true},
				   {"test3-should-appear", "Binary", "-10", "10", null, true},
				   {"test4-should-not-appear", "Binary", "-10", "10", null, false}};
		OptimizationProcess.runOptimization(testB, "NSGAII", false);
		
		Object[][] test1 = {{"test1-singular-variable", "Double", "-1.1", "2.2", null, true }};
		OptimizationProcess.runOptimization(test1, "NSGAII", false);
		
		Object[][] testSame = {{"test1-should-crash", "Double", "0", "0", null, true }};
		OptimizationProcess.runOptimization(testSame, "NSGAII", false);
//		
		
	}
}
