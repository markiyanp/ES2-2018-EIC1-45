package jMetal.tests;

import jMetal.OptimizationProcess;

public class TestJar {

	public static void main(String[] args) {

//		Object[][] testD = { { "test1-should-not-appear", "Double", "-1.0", "2.0", null, false },
//				{ "test2-should-appear", "Double", "-2.0", "5.0", null, true },
//				{ "test3-should-appear", "Double", "-10.0", "10.0", null, true },
//				{ "test4-should-not-appear", "Integer", "-10", "10", null, false } };
//
//		OptimizationProcess.setJarPath("D:\\Kursawe.jar");
//		OptimizationProcess.runOptimization(testD, "NSGAII", true);

		Object[][] testI = { { "test1-should-appear", "Integer", "-1", "2", null, true },
				{ "test1-should-appear", "Integer", "-1", "2", null, true },
				{ "test1-should-appear", "Integer", "-1", "2", null, true }
				 };
		OptimizationProcess.setJarPath("D:\\NMMin.jar");
		OptimizationProcess.setData(testI);
		OptimizationProcess.setAlgorithm("NSGAII");
		OptimizationProcess.setJar(true);
		OptimizationProcess.runOptimization();

//		 Object[][] testB = { { "test1-should-appear", "Binary", "-1", "2", null, true
//		 },
//		 { "test2-should-appear", "Binary", "-2", "5", null, true },
//		 { "test3-should-appear", "Binary", "-10", "10", null, true },
//		 { "test4-should-not-appear", "Binary", "-10", "10", null, false } };
//		 OptimizationProcess.setJarPath("D:\\OneZeroMax.jar");
//		 OptimizationProcess.runOptimization(testB, "NSGAII", true);

	}

}
