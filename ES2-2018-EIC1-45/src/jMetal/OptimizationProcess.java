package jMetal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import jMetal.binaryProblems.ExperimentsBinary;
import jMetal.doubleProblems.ExperimentsDouble;
import jMetal.integerProblems.ExperimentsInteger;

/**Handles the interfacing between user and jMetal.
 * 
 * @author pvmpa-iscteiulpt
 *
 */
public class OptimizationProcess extends Thread {

	private static String[] AlgorithmsForDoubleProblemType = new String[] { "NSGAII", "SMSEMOA", "GDE3", "IBEA",
			"MOCell", "MOEAD", "PAES", "RandomSearch" };
	private static String[] AlgorithmsForIntegerProblemType = new String[] { "NSGAII", "SMSEMOA", "MOCell", "PAES",
			"RandomSearch" };
	private static String[] AlgorithmsForBinaryProblemType = new String[] { "NSGAII", "SMSEMOA", "MOCell", "MOCH",
			"PAES", "RandomSearch", "SPEA2" };
	private static int variable_count = 0;

	private static Object[][] data;
	private static Object[][] objectives;
	private static String algorithm;
	private static boolean isJar;
	private static String problemName;
	private static String jarPath;
	private static long timelimit;
	
	@Override
	public void run() {
		runOptimization();
	}
	
	/**
	 * Executes a problem depending on the data fed from the GUI's table, the chosen
	 * algorithm, and whether a jar is used or not along with its path.
	 * 
	 * @param data
	 * @param objectives
	 * @param algorithm
	 * @param problemName
	 * @param isJar
	 * @author pvmpa-iscteiulpt
	 */
	public static void runOptimization() {
		try {
			variable_count = 0;
			//debugSysout_Start(data, algorithm, isJar);

			boolean integerProblem = false, doubleProblem = false, binaryProblem = false;
			
			for (int i = 0; i < data.length; i++) {
				if ((data[i][1].equals("Integer") && (data[i][5].equals(Boolean.TRUE)))) {
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
			
			Object[][] true_objectives = parseTrueObjectives(objectives, integerProblem, doubleProblem, binaryProblem);
	
			if (!integerProblem && !doubleProblem && !binaryProblem) {
				System.out.println("WARNING: No variables detected. Cannot proceed!");
				return;
			}

			Object[][] true_data = parseTrueData(data);

			verifyAlgorithmAndTypes(algorithm, integerProblem, doubleProblem, binaryProblem);
			
			System.out.println("============================================================");
			System.out.println("BEGINNING runOptimization WITH THE FOLLOWING PARAMETERS:");
			for (int i = 0; i < true_objectives.length; i++) {
				System.out.println(true_objectives[i][0] + " " + true_objectives[i][1] + " " + true_objectives[i][2]);
			}
			System.out.println("============================================================");
			
			long startTime = System.currentTimeMillis();
			launchProblem(true_data, true_objectives, algorithm, integerProblem, doubleProblem, binaryProblem, isJar);
			long endTime = System.currentTimeMillis() - startTime;
			
			double endTime_inSeconds = ((double) endTime)/1000;
			
			writeNamesOfResultsAndTime(integerProblem, doubleProblem, binaryProblem, true_objectives,
					true_data, endTime_inSeconds);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**Writes the variables' names, the objectives' names and the elapsed time onto a file in order
	 * to display them in the Graphics tab.
	 * 
	 * @param integerProblem
	 * @param doubleProblem
	 * @param binaryProblem
	 * @param true_objectives
	 * @param true_data
	 * @param endTime_inSeconds
	 * @author pvmpa-iscteiulpt
	 */
	private static void writeNamesOfResultsAndTime(boolean integerProblem,
			boolean doubleProblem, boolean binaryProblem, Object[][] true_objectives, Object[][] true_data,
			double endTime_inSeconds) {
		PrintWriter writer = null;
		String variable_names = new String();
		String objective_names = new String();
		
		
		for (int i = 0; i < true_data.length; i++) {
			variable_names += true_data[i][0] + " ";
		}
		variable_names = variable_names.substring(0, variable_names.length()-1);
		
		for (int i = 0; i < true_objectives.length; i++) {
			objective_names += true_objectives[i][0] + " ";
		}
		objective_names = objective_names.substring(0, objective_names.length()-1);
		
		try {
			if (integerProblem) {
				final String directory_expInt = "experimentBaseDirectory\\ExperimentsInteger\\data\\";
				
				writer = new PrintWriter(new File(directory_expInt + algorithm + "\\" + problemName + "\\BEST_VAR_NAMES.txt"));
				writer.print(variable_names);
				writer.close();

				writer = new PrintWriter(new File(directory_expInt + algorithm + "\\" + problemName + "\\BEST_FUN_NAMES.txt"));
				writer.print(objective_names);
				writer.close();

				writer = new PrintWriter(new File(directory_expInt + algorithm + "\\" + problemName + "\\exec_time.txt"));
				writer.print(endTime_inSeconds);
				writer.close();
			} else if (doubleProblem) {
				final String directory_expDouble = "experimentBaseDirectory\\ExperimentsDouble\\data\\";
				
				writer = new PrintWriter(new File(directory_expDouble + algorithm + "\\" + problemName + "\\BEST_VAR_NAMES.txt"));
				writer.print(variable_names);
				writer.close();

				writer = new PrintWriter(new File(directory_expDouble + algorithm + "\\" + problemName + "\\BEST_FUN_NAMES.txt"));
				writer.print(objective_names);
				writer.close();

				writer = new PrintWriter(new File(directory_expDouble + algorithm + "\\" + problemName + "\\exec_time.txt"));
				writer.print(endTime_inSeconds);
				writer.close();

			} else if (binaryProblem) {
				final String directory_expBinary = "experimentBaseDirectory\\ExperimentsBinary\\data\\";
				
				writer = new PrintWriter(new File(directory_expBinary + algorithm + "\\" + problemName + "\\BEST_VAR_NAMES.txt"));
				writer.print(variable_names);
				writer.close();

				writer = new PrintWriter(new File(directory_expBinary + algorithm + "\\" + problemName + "\\BEST_FUN_NAMES.txt"));
				writer.print(objective_names);
				writer.close();

				writer = new PrintWriter(new File(directory_expBinary + algorithm + "\\" + problemName + "\\exec_time.txt"));
				writer.print(endTime_inSeconds);
				writer.close();
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			writer.close();
			throw new IllegalStateException("This is NOT ok.");
		} finally {
			writer.close();
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
	
	/**Prepares the objectives selected by the user.
	 * 
	 * @param objectives
	 * @param integerProblem
	 * @param doubleProblem
	 * @param binaryProblem
	 * @return true objectives
	 * @author pvmpa-iscteiulpt
	 */
	private static Object[][] parseTrueObjectives(Object[][] objectives, boolean integerProblem, boolean doubleProblem,
												  boolean binaryProblem){
		int objectives_count = 0;
		for (int i = 0; i < objectives.length; i++) {
			if(objectives[i][2].equals(Boolean.TRUE)) {
				objectives_count++;
			}
		}
		
		Object[][] true_objectives = new Object[objectives_count][3];
		int true_objectives_iterator = 0;
		for (int i = 0; i < objectives.length; i++) {
			if(objectives[i][2].equals(Boolean.TRUE)) {
				checkDatatypeCombo(objectives, integerProblem, doubleProblem, binaryProblem, i);
				true_objectives[true_objectives_iterator] = objectives[i];
				true_objectives_iterator++;
			}
		}
		return true_objectives;
	}

	/**
	 * Verifies whether the selected objective is in conflict with the problem at hand or not.
	 * 
	 * @param objectives
	 * @param integerProblem
	 * @param doubleProblem
	 * @param binaryProblem
	 * @param i
	 * @author pvmpa-iscteiulpt
	 */
	private static void checkDatatypeCombo(Object[][] objectives, boolean integerProblem, boolean doubleProblem,
			boolean binaryProblem, int i) {
		if(objectives[i][1].equals("Integer") && (doubleProblem || binaryProblem)) {
			throw new IllegalStateException("Invalid objective datatype for specified variable-objective combo!");
		}
		else if(objectives[i][1].equals("Double") && (integerProblem || binaryProblem)) {
			throw new IllegalStateException("Invalid objective datatype for specified variable-objective combo!");
		}
		else if(objectives[i][1].equals("Binary") && (doubleProblem || integerProblem)) {
			throw new IllegalStateException("Invalid objective datatype for specified variable-objective combo!");
		}
	}

	/**
	 * Verifies whether a valid algorithm was chosen or not. Also verifies if there
	 * are any datatypes mixed together, and throws an exception if that is true.
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
	private static void launchProblem(Object[][] data, Object[][] objectives, String algorithm, boolean integerProblem,
			boolean doubleProblem, boolean binaryProblem, boolean isJar) throws IOException {
		//is an integer Problem
		if (integerProblem && !doubleProblem && !binaryProblem) {
			ExperimentsInteger e = new ExperimentsInteger();
			int[][] limits = new int[variable_count][2];
			for (int i = 0; i < limits.length; i++) {
				if (!(data[i][2].equals(null)) && !(data[i][3].equals(null))) {
					limits[i][0] = Integer.parseInt((String) data[i][2]);
					limits[i][1] = Integer.parseInt((String) data[i][3]);
					//debug_Sysoutlimits_INT(limits, i);
				} else {
					System.out.println("WARNING: No min/max detected, cannot iterate safely. Assuming min/max -100/100");
					limits[i][0] = -100;
					limits[i][1] = 100;
				}
			}
			e.setLimits_Int(limits);
			e.setAlgorithm(algorithm);
			e.setJar(isJar);
			e.setJarPath(jarPath);
			e.setProblemName(problemName);
			e.setNumber_of_objectives(objectives.length);
			e.setTimelimit(timelimit);
			e.execute();
			
		}
		
		//is a double Problem
		else if (doubleProblem && !integerProblem && !binaryProblem) {
			ExperimentsDouble e = new ExperimentsDouble();
			double[][] limits = new double[variable_count][2];
			for (int i = 0; i < limits.length; i++) {
				if (!(data[i][2].equals(null)) && !(data[i][3].equals(null))) {
					limits[i][0] = Double.parseDouble((String) data[i][2]);
					limits[i][1] = Double.parseDouble((String) data[i][3]);
					//debug_Sysoutlimits_DOUBLE(limits, i);
				} else {
					System.out.println("WARNING: No min/max detected, cannot iterate safely. Assuming min/max -100/100");
					limits[i][0] = -100;
					limits[i][1] = 100;
				}
			}
			e.setLimits_Double(limits);
			e.setAlgorithm(algorithm);
			e.setJar(isJar);
			e.setJarPath(jarPath);
			e.setProblemName(problemName);
			e.setNumber_of_objectives(objectives.length);
			e.setTimelimit(timelimit);
			e.execute();
		} 
		
		//is a binary Problem
		else if (binaryProblem && !integerProblem && !doubleProblem) {
			ExperimentsBinary e = new ExperimentsBinary();
			// TODO: WARNING WARNING WARNING THIS IS ASSUMING THAT 8 IS THE NUMBER OF BITS
			e.setLimits_Binary(8);
			// TODO: WARNING WARNING WARNING THIS IS ASSUMING THAT 8 IS THE NUMBER OF BITS
			e.setAlgorithm(algorithm);
			e.setNumber_of_variables(data.length);
			e.setJar(isJar);
			e.setJarPath(jarPath);
			e.setProblemName(problemName);
			e.setNumber_of_objectives(objectives.length);
			e.setTimelimit(timelimit);
			e.execute();
		} else
			throw new IllegalStateException("How in the world did this happen???");

	}

	/**
	 * This is a debugging function. Please don't touch it.
	 * @param limits
	 * @param i
	 */
	@SuppressWarnings("unused")
	private static void debug_Sysoutlimits_DOUBLE(double[][] limits, int i) {
		System.out.println(limits[i][0]);
		System.out.println(limits[i][1]);
	}

	/**
	 * This is a debugging function. Please don't touch it.
	 * @param limits
	 * @param i
	 */
	@SuppressWarnings("unused")
	private static void debug_Sysoutlimits_INT(int[][] limits, int i) {
		System.out.println(limits[i][0]);
		System.out.println(limits[i][1]);
	}
	
	/**
	 * This is a debugging function. Please don't touch it.
	 * @param data
	 * @param algorithm
	 * @param isJar
	 */
	@SuppressWarnings("unused")
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
	
	/**
	 * This is a debugging function. Please don't touch it.
	 * @param true_data
	 */
	@SuppressWarnings("unused")
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
	
	public static void setJarPath(String path) {
		jarPath = path;
	}

	public static void setObjectives(Object[][] objectives) {
		OptimizationProcess.objectives = objectives;
	}

	public static void setProblemName(String problemName) {
		OptimizationProcess.problemName = problemName;
	}

	public static long getTimelimit() {
		return timelimit;
	}

	public static void setTimelimit(long timelimit) {
		OptimizationProcess.timelimit = timelimit;
	}
	
	
}
