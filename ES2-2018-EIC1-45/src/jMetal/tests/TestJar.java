package jMetal.tests;

import jMetal.OptimizationProcess;

public class TestJar {

	public static void main(String[] args) {
		 try {
			Object[][] testD = { { "test1-should-not-appear", "Double", "-1.1", "2.2", null, false },
					{ "test2-should-appear", "Double", "-2.0", "2.0", null, true },
					{ "test3-should-appear", "Double", "-10.0", "10.0", null, true },
					{ "test4-should-not-appear", "Integer", "-10", "10", null, false } };
			
			String path = "D:\\Kursawe.jar";
			
			OptimizationProcess.setJarPath(path);
			OptimizationProcess.runOptimization(testD, "NSGAII", true);
		} catch (Exception e) {
			System.out.println("WARNING WARNING WARNING WARNING WARNING THIS ISN'T GOOD ");
			e.printStackTrace();
		}
//		Object[][] testI = {{"test1-should-appear", "Integer", "-1", "2", null, true
//		 },
//		 {"test2-should-appear", "Integer", "-2", "5", null, true},
//		 {"test3-should-appear", "Integer", "-10", "10", null, true},
//		 {"test4-should-not-appear", "Integer", "-10", "10", null, false}};
//		 OptimizationProcess.runOptimization(testI, "NSGAII", false, null);
//		// SMSEMOA
//
//		Object[][] testB = { { "test1-should-appear", "Binary", "-1", "2", null, true },
//				{ "test2-should-appear", "Binary", "-2", "5", null, true },
//				{ "test3-should-appear", "Binary", "-10", "10", null, true },
//				{ "test4-should-not-appear", "Binary", "-10", "10", null, false } };
//		OptimizationProcess.runOptimization(testB, "NSGAII", false, null);

	}

}
