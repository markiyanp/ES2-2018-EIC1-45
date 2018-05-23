package jMetal.tests;

import jMetal.OptimizationProcess;

public class TestJar {

	public static void main(String[] args) {
		OptimizationProcess op1 = new OptimizationProcess();

		// Object[][] testD = { { "test1-should-not-appear", "Double", "-1.0", "2.0",
		// null, false },
		// { "test2-should-appear", "Double", "-2.0", "5.0", null, true },
		// { "test3-should-appear", "Double", "-10.0", "10.0", null, true },
		// { "test4-should-not-appear", "Integer", "-10", "10", null, false } };
		//
		// OptimizationProcess op1 = new OptimizationProcess();
		// op1.setJarPath("D:\\Kursawe.jar");
		// op1.runOptimization(testD, "NSGAII", true);

		Object[][] testI = { { "test1-should-appear-VAR-INTEG-JAR", "Integer", "-1", "2", null, true },
				{ "test2-should-appear-VAR-INTEG-JAR", "Integer", "-1", "2", null, true },
				{ "test3-should-appear-VAR-INTEG-JAR", "Integer", "-1", "2", null, true } };
		Object[][] testOI = { { "test1-should-appear-OBJ-INTEG-JAR", "Integer", true },
				{ "test2-should-not-appear-OBJ-INTEG-JAR", "Double", false },
				{ "test3-should-appear-OBJ-INTEG-JAR", "Integer", true },
				{ "test5-should-not-appear-OBJ-INTEG-JAR", "Integer", false } };
		op1 = new OptimizationProcess();
		op1.setJarPath("D:\\NMMin.jar");
		op1.setData(testI);
		op1.setObjectives(testOI);
		op1.setAlgorithm("NSGAII");
		op1.setJar(true);
		op1.setProblemName("TestJar");
		op1.setTimelimit(3600000);
		op1.runOptimization();

		// Object[][] testB = { { "test1-should-appear", "Binary", "-1", "2", null, true
		// },
		// { "test2-should-appear", "Binary", "-2", "5", null, true },
		// { "test3-should-appear", "Binary", "-10", "10", null, true },
		// { "test4-should-not-appear", "Binary", "-10", "10", null, false } };
		// op1 = new OptimizationProcess();
		// op1.setJarPath("D:\\OneZeroMax.jar");
		// op1.runOptimization(testB, "NSGAII", true);

	}

}
