package jMetal.tests;

import jMetal.*;

public class TestjMetal {
	public static void main(String[] args) {

//		Object[][] testD = {{"test1-should-not-appear", "Double", "-1.1", "2.2", null, false },
//						   {"test2-should-appear", "Double", "-2.0", "2.0", null, true},
//						   {"test3-should-appear", "Double", "-10.0", "10.0", null, true},
//						   {"test4-should-not-appear", "Integer", "-10", "10", null, false}};
//		OptimizationProcess.runOptimization(testD, "NSGAII", false);
//
		Object[][] testD = {{"test1-should-not-appear-VAR-DOUBL", "Double", "-1.1", "2.2", null, false},
						   {"test2-should-appear-VAR-DOUBL", "Double", "-2.0", "2.0", null, true},
						   {"test3-should-appear-VAR-DOUBL", "Double", "-10.0", "10.0", null, true},
						   {"test4-should-not-appear-VAR-DOUBL", "Integer", "-10", "10", null, false},
						   {"test5-shoul-appear-VAR-DOUBL", "Double", "-10.0", "10.0", null, true}};
		Object[][] testOD = {
				{"test1-should-appear-OBJ-DOUBL", "Double", true},
				{"test2-should-not-appear-OBJ-DOUBL", "Integer", false},
				{"test3-should-appear-OBJ-DOUBL", "Double", true},
				{"test5-should-not-appear-OBJ-DOUBL", "Double", false}
		};
		OptimizationProcess op1 = new OptimizationProcess();
		op1.setObjectives(testOD);
		op1.setData(testD);
		op1.setAlgorithm("NSGAII");
		op1.setJar(false);
		op1.setProblemName("TestjMetal");
		op1.setTimelimit(100000);
		op1.runOptimization();

		Object[][] testI = { { "test1-should-appear-VAR-INTEG", "Integer", "-1", "2", null, true },
				{ "test2-should-appear-VAR-INTEG", "Integer", "-1", "2", null, true },
				{ "test3-should-appear-VAR-INTEG", "Integer", "-1", "2", null, true }
				 };
		Object[][] testOI = {
		{"test1-should-appear-OBJ-INTEG", "Integer", true},
		{"test2-should-not-appear-OBJ-INTEG", "Double", false},
		{"test3-should-appear-OBJ-INTEG", "Integer", true},
		{"test5-should-not-appear-OBJ-INTEG", "Integer", false}
				};
		op1 = new OptimizationProcess();
		op1.setObjectives(testOI);
		op1.setData(testI);
		op1.setAlgorithm("NSGAII");
		op1.setJar(false);
		op1.setTimelimit(100000);
		op1.setProblemName("TestjMetal");
		op1.runOptimization();
		//SMSEMOA
		

//		Object[][] testB = {{"test1-should-appear", "Binary", "-1", "2", null, true },
//				   {"test2-should-appear", "Binary", "-2", "5", null, true},
//				   {"test3-should-appear", "Binary", "-10", "10", null, true},
//				   {"test4-should-not-appear", "Binary", "-10", "10", null, false}};
//		OptimizationProcess.runOptimization(testB, "NSGAII", false);
//
		Object[][] testB = {{"test1-should-appear-VAR-BINAR", "Binary", "-1", "2", null, true},
				   {"test2-should-appear-VAR-BINAR", "Binary", "-2", "5", null, true},
				   {"test3-should-appear-VAR-BINAR", "Binary", "-10", "10", null, true},
				   {"test4-should-not-appear-VAR-BINAR", "Binary", "-10", "10", null, false}};
		Object[][] testOB = {
				{"test1-should-appear-OBJ-VAR-BINAR", "Binary", true},
				{"test2-should-not-appear-OBJ-VAR-BINAR", "Integer", false},
				{"test3-should-appear-OBJ-VAR-BINAR", "Binary", true},
				{"test5-should-not-appear-OBJ-VAR-BINAR", "Binary", false}
		};
		op1 = new OptimizationProcess();
		op1.setData(testB);
		op1.setObjectives(testOB);
		op1.setAlgorithm("SMSEMOA");
		op1.setJar(false);
		op1.setTimelimit(100000);
		op1.setProblemName("TestjMetal");
		op1.runOptimization();
//
//		
//		Object[][] test1 = {{"test1-singular-variable", "Double", "-1.1", "2.2", null, true }};
//		OptimizationProcess.runOptimization(test1, "NSGAII", false);
//		
//		Object[][] testSame = {{"test1-should-crash", "Double", "0", "0", null, true }};
//		OptimizationProcess.runOptimization(testSame, "NSGAII", false);
//		
		
	}
}
