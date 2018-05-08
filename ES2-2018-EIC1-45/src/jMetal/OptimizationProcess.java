package jMetal;

import java.io.IOException;
import java.lang.Boolean;

import jMetal.binaryProblems.ExperimentsBinary;
import jMetal.doubleProblems.ExperimentsDouble;
import jMetal.integerProblems.ExperimentsInteger;

public class OptimizationProcess extends Thread{

	private static String[] AlgorithmsForDoubleProblemType = new String[] { "NSGAII", "SMSEMOA", "GDE3", "IBEA",
			"MOCell", "MOEAD", "PAES", "RandomSearch" };
	private static String[] AlgorithmsForIntegerProblemType = new String[] { "NSGAII", "SMSEMOA", "MOCell", "PAES",
			"RandomSearch" };
	private static String[] AlgorithmsForBinaryProblemType = new String[] { "NSGAII", "SMSEMOA", "MOCell", "MOCH",
			"PAES", "RandomSearch", "SPEA2" };
	private static int variable_count = 0;

	private static Object[][] data;
	private static String algorithm;
	private static boolean isJar;
	
	/**
	 * Executes a problem depending on the data fed from the GUI's table, the chosen
	 * algorithm, and whether a jar is used or not.
	 * 
	 * @param data
	 * @param algorithm
	 * @param isJar
	 * @author pvmpa-iscteiulpt
	 */
	
	@Override
	public void run() {
		runOptimization(data, algorithm, isJar);
	}
	
	public static void runOptimization(Object[][] data, String algorithm, boolean isJar) {
		try {
			variable_count = 0;
			//debugSysout_Start(data, algorithm, isJar);

			boolean integerProblem = false, doubleProblem = false, binaryProblem = false;

			for (int i = 0; i < data.length; i++) {
				if (data[i][1].equals("Integer") && (data[i][5].equals(Boolean.TRUE))) {
					System.out.println("Detected Integer Problem!");
					variable_count++;
					integerProblem = true;
				} else if (data[i][1].equals("Double") && (data[i][5].equals(Boolean.TRUE))) {
					System.out.println("Detected Double Problem!");
					variable_count++;
					doubleProblem = true;
				} else if (data[i][1].equals("Binary") && (data[i][5].equals(Boolean.TRUE))) {
					System.out.println("Detected Binary Problem!");
					variable_count++;
					binaryProblem = true;
				}
			}

			if (!integerProblem && !doubleProblem && !binaryProblem) {
				System.out.println("WARNING: No variables detected. Cannot proceed!");
				return;
			}

			Object[][] true_data = parseTrueData(data);

			verifyAlgorithmAndTypes(algorithm, integerProblem, doubleProblem, binaryProblem);

			launchProblem(true_data, algorithm, integerProblem, doubleProblem, binaryProblem, isJar);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**Prepares the data selected by the user.
	 * 
	 * @param data
	 * @return true data
	 * @author pvmpa-iscteiulpt
	 */
	private static Object[][] parseTrueData(Object[][] data) {
		Object[][] true_data = new Object[variable_count][5];
		int true_data_iterator = 0;
		for (int i = 0; i < data.length; i++) {
			if(data[i][5].equals(Boolean.TRUE)) {
				true_data[true_data_iterator] = data[i];
				true_data_iterator++;
			}
		}
		//debugSysout_Parser(true_data);
		return true_data;
	}

	/**
	 * Verifies whether a valid algorithm was chosen or not. Also verifies if there
	 * are any datatypes mixed together.
	 * 
	 * @param algorithm
	 * @param integerProblem
	 * @param doubleProblem
	 * @param binaryProblem
	 * @author pvmpa-iscteiulpt
	 */
	private static void verifyAlgorithmAndTypes(String algorithm, boolean integerProblem, boolean doubleProblem,
			boolean binaryProblem) {
		if (integerProblem && !doubleProblem && !binaryProblem) {
			for (int i = 0; i < AlgorithmsForIntegerProblemType.length; i++) {
				if (algorithm.equals(AlgorithmsForIntegerProblemType[i])) {
					System.out.println("Valid algorithm detected: " + AlgorithmsForIntegerProblemType[i]);
					break;
				}
				if (i == AlgorithmsForIntegerProblemType.length - 1) {
					throw new IllegalArgumentException("Invalid algorithm for problem type!");
				}
			}
		} else if (!integerProblem && doubleProblem && !binaryProblem) {
			for (int i = 0; i < AlgorithmsForDoubleProblemType.length; i++) {
				if (algorithm.equals(AlgorithmsForDoubleProblemType[i])) {
					System.out.println("Valid algorithm detected: " + AlgorithmsForDoubleProblemType[i]);
					break;
				}
				if (i == AlgorithmsForDoubleProblemType.length - 1) {
					throw new IllegalArgumentException("Invalid algorithm for problem type!");
				}
			}
		} else if (!integerProblem && !doubleProblem && binaryProblem) {
			for (int i = 0; i < AlgorithmsForBinaryProblemType.length; i++) {
				if (algorithm.equals(AlgorithmsForBinaryProblemType[i])) {
					System.out.println("Valid algorithm detected: " + AlgorithmsForBinaryProblemType[i]);
					break;
				}
				if (i == AlgorithmsForBinaryProblemType.length - 1) {
					throw new IllegalArgumentException("Invalid algorithm for problem type!");
				}
			}
		}
		else
			throw new IllegalArgumentException("Multiple data types detected!");
	}

	/**
	 * Launches a problem after parsing which datatype it pertains to.
	 * 
	 * @param data
	 * @param algorithm
	 * @param integerProblem
	 * @param doubleProblem
	 * @param binaryProblem
	 * @throws IOException
	 * @author pvmpa-iscteiulpt
	 */
	private static void launchProblem(Object[][] data, String algorithm, boolean integerProblem, boolean doubleProblem,
			boolean binaryProblem, boolean isJar) throws IOException {
		//is an integer Problem
		if (integerProblem && !doubleProblem && !binaryProblem) {
			int[][] limits = new int[variable_count][2];
			for (int i = 0; i < limits.length; i++) {
				if (!(data[i][2].equals(null)) && !(data[i][3].equals(null))) {
					limits[i][0] = Integer.parseInt((String) data[i][2]);
					limits[i][1] = Integer.parseInt((String) data[i][3]);
				} else {
					System.out.println("WARNING: No min/max detected, cannot iterate safely. Assuming min/max -100/100");
					limits[i][0] = -100;
					limits[i][1] = 100;
				}
			}
			ExperimentsInteger.execute(limits, algorithm, isJar);
		}
		
		//is a double Problem
		else if (doubleProblem && !integerProblem && !binaryProblem) {
			double[][] limits = new double[variable_count][2];
			for (int i = 0; i < limits.length; i++) {
				if (!(data[i][2].equals(null)) && !(data[i][3].equals(null))) {
					limits[i][0] = Double.parseDouble((String) data[i][2]);
					limits[i][1] = Double.parseDouble((String) data[i][3]);
				} else {
					System.out.println("WARNING: No min/max detected, cannot iterate safely. Assuming min/max -100/100");
					limits[i][0] = -100;
					limits[i][1] = 100;
				}
			}
			ExperimentsDouble.execute(limits, algorithm, isJar);
		} 
		
		//is a binary Problem
		else if (binaryProblem && !integerProblem && !doubleProblem) {
			// TODO: WARNING WARNING WARNING THIS IS ASSUMING THAT 8 IS THE NUMBER OF BITS
			ExperimentsBinary.execute(8, algorithm, data.length, isJar);
		} else
			throw new IllegalStateException("How in the world did this happen???");

	}
	
	private static void debugSysout_Start(Object[][] data, String algorithm, boolean isJar) {
		System.out.println("============================================================");
		System.out.println("BEGINNING runOptimization WITH THE FOLLOWING PARAMETERS:");
		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i][0] + " " + data[i][1] + " " + data[i][2] + " " + data[i][3] + " " + data[i][4] + " "
					+ data[i][5]);
		}
		System.out.println("============================================================");

		System.out.println("Algorithm: " + algorithm);
		System.out.println("Use jar: " + isJar);
	}
	
	private static void debugSysout_Parser(Object[][] true_data) {
		System.out.println("============================================================");
		System.out.println("PARSING SELECTED PARAMETERS:");
		for (int i = 0; i < true_data.length; i++) {

			System.out.println(true_data[i][0] + " " + true_data[i][1] + " " + true_data[i][2] + " " + true_data[i][3] + " "
					+ true_data[i][4] + " " + true_data[i][5]);

		}
		System.out.println("True Data length: " +true_data.length);
		System.out.println("============================================================");
	}
	
	public static Object[][] getData() {
		return data;
	}

	public static void setData(Object[][] data) {
		OptimizationProcess.data = data;
	}

	public static String getAlgorithm() {
		return algorithm;
	}

	public static void setAlgorithm(String algorithm) {
		OptimizationProcess.algorithm = algorithm;
	}

	public static boolean isJar() {
		return isJar;
	}

	public static void setJar(boolean isJar) {
		OptimizationProcess.isJar = isJar;
	}

}
