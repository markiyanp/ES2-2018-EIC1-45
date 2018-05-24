package jMetal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import jMetal.binaryProblems.ExperimentsBinary;
import jMetal.doubleProblems.ExperimentsDouble;
import jMetal.integerProblems.ExperimentsInteger;

/**
 * Handles the interfacing between user and jMetal.
 * 
 * @author pvmpa-iscteiulpt
 *
 */
public class OptimizationProcess extends Thread {

	private static final String WARNING_TITLE_INVALID_MINMAX = "Invalid min/max";
	private static final String WARNING_TITLE_MULTIPLE_DATA_TYPES = "Multiple data types fail";
	private static final String WARNING_TITLE_INVALID_ALGORITHM_SELECTED = "Invalid algorithm selected";
	private static final String WARNING_TITLE_INVALID_DATA_TYPE_COMBO = "Invalid data type combination";
	private static final String WARNING_TITLE_WRITE_FAIL = "Write fail";
	private static final String WARNING_TITLE_DATA_VALIDATION = "Data Validation Warning";
	
	private static final String WARNING_INVALID_MINMAX = "WARNING: Invalid min/max on a variable, assuming -100/100";
	private static final String WARNING_MULTIPLE_DATA_TYPES = "Multiple data types detected! Please keep only ONE data type "
			+ "per problem!";
	private static final String WARNING_INVALID_ALGORITHM = "Invalid algorithm for problem type!";
	private static final String WARNING_INVALID_DATA_TYPE_COMBO = "Invalid objective data type for specified variable-objective combo!";
	private static final String WARNING_FAILED_TO_WRITE_VARIABLE_OBJECTIVE_NAMES = "Failed to write variable/objective names and "
			+ "elapsed time to file!";
	private static final String WARNING_DEFAULT_TO_UNTITLED = "WARNING: For some reason, the program couldn't "
			+ "catch the problem's name. Default to 'Untitled' and proceed?";
	
	private static final String WARNING_UNREASONABLE_TIMELIMIT = "WARNING: Unreasonable timelimit! Normally, Optimization Processes "
			+ "take more than 10 seconds! \nThe process can continue, but coherent results are NOT guaranteed!\n"
			+ "Do you wish to continue with the optimization process?";

	private static final String WARNING_NO_JAR_PATH = "WARNING: Specified jar usage, but "
			+ "no path to jar has been specified! Aborting!";
	private static final String WARNING_NO_ALGORITHM_SPECIFIED = "WARNING: No algorithm specified. Aborting!";

	private String[] AlgorithmsForDoubleProblemType = new String[] { "NSGAII", "SMSEMOA", "GDE3", "IBEA", "MOCell",
			"MOEAD", "PAES", "RandomSearch" };
	private String[] AlgorithmsForIntegerProblemType = new String[] { "NSGAII", "SMSEMOA", "MOCell", "PAES",
			"RandomSearch" };
	private String[] AlgorithmsForBinaryProblemType = new String[] { "NSGAII", "SMSEMOA", "MOCell", "MOCH", "PAES",
			"RandomSearch", "SPEA2" };
	private int variable_count = 0;

	private Object[][] data;
	private Object[][] objectives;
	private String algorithm;
	private boolean isJar;
	private String problemName;
	private String jarPath;
	private String expectedAlgorithm = "";
	private long timelimit;

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
	public void runOptimization() {
		try {
			variable_count = 0;
			// debugSysout_Start(data, algorithm, isJar);

			validateData();

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

			long startTime = System.currentTimeMillis();
			launchProblem(true_data, true_objectives, algorithm, integerProblem, doubleProblem, binaryProblem, isJar);
			long endTime = System.currentTimeMillis() - startTime;

			double endTime_inSeconds = ((double) endTime) / 1000;

			writeNamesOfResultsAndTime(integerProblem, doubleProblem, binaryProblem, true_objectives, true_data,
					endTime_inSeconds);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void validateData() {
		if (algorithm.equals(null) || algorithm.equals("")) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null, WARNING_NO_ALGORITHM_SPECIFIED, WARNING_TITLE_DATA_VALIDATION,
							2);
				}
			}.start();

			throw new IllegalStateException("ABORTED ON DATA VALIDATION: No algorithm specified");
		}
		if (isJar() && (jarPath.equals(null) || jarPath.equals(""))) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null, WARNING_NO_JAR_PATH, WARNING_TITLE_DATA_VALIDATION, 2);
				}
			}.start();

			throw new IllegalStateException("ABORTED ON DATA VALIDATION: No jar specified");
		}
		if (timelimit <= 10000) {
			Object[] options = { "Yes", "No" };
			int n = JOptionPane.showOptionDialog(null, WARNING_UNREASONABLE_TIMELIMIT, WARNING_TITLE_DATA_VALIDATION,
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

			switch (n) {
			case 0:
				break;
			case 1:
				throw new IllegalStateException("ABORTED ON DATA VALIDATION: User aborted on bad timelimit prompt");
			}
		}
		if (problemName.equals(null) || problemName.equals("")) {
			Object[] options = { "Yes", "No" };

			int n = JOptionPane.showOptionDialog(null, WARNING_DEFAULT_TO_UNTITLED, WARNING_TITLE_DATA_VALIDATION,
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

			switch (n) {
			case 0:
				this.problemName = "Untitled";
				break;
			case 1:
				throw new IllegalStateException("ABORTED ON DATA VALIDATION: User aborted on bad name prompt");
			}
		}

	}

	/**
	 * Writes the variables' names, the objectives' names and the elapsed time onto
	 * a file in order to display them in the Graphics tab.
	 * 
	 * @param integerProblem
	 * @param doubleProblem
	 * @param binaryProblem
	 * @param true_objectives
	 * @param true_data
	 * @param endTime_inSeconds
	 * @author pvmpa-iscteiulpt
	 */
	private void writeNamesOfResultsAndTime(boolean integerProblem, boolean doubleProblem, boolean binaryProblem,
			Object[][] true_objectives, Object[][] true_data, double endTime_inSeconds) {
		PrintWriter writer = null;
		String variable_names = new String();
		String objective_names = new String();

		for (int i = 0; i < true_data.length; i++) {
			variable_names += true_data[i][0] + " ";
		}
		variable_names = variable_names.substring(0, variable_names.length() - 1);

		for (int i = 0; i < true_objectives.length; i++) {
			objective_names += true_objectives[i][0] + " ";
		}
		objective_names = objective_names.substring(0, objective_names.length() - 1);

		try {
			if (integerProblem) {
				final String directory_expInt = "experimentBaseDirectory\\ExperimentsInteger\\data\\";

				writer = new PrintWriter(
						new File(directory_expInt + algorithm + "\\" + problemName + "\\BEST_VAR_NAMES.txt"));
				writer.print(variable_names);
				writer.close();

				writer = new PrintWriter(
						new File(directory_expInt + algorithm + "\\" + problemName + "\\BEST_FUN_NAMES.txt"));
				writer.print(objective_names);
				writer.close();

				writer = new PrintWriter(
						new File(directory_expInt + algorithm + "\\" + problemName + "\\exec_time.txt"));
				writer.print(endTime_inSeconds);
				writer.close();
			} else if (doubleProblem) {
				final String directory_expDouble = "experimentBaseDirectory\\ExperimentsDouble\\data\\";

				writer = new PrintWriter(
						new File(directory_expDouble + algorithm + "\\" + problemName + "\\BEST_VAR_NAMES.txt"));
				writer.print(variable_names);
				writer.close();

				writer = new PrintWriter(
						new File(directory_expDouble + algorithm + "\\" + problemName + "\\BEST_FUN_NAMES.txt"));
				writer.print(objective_names);
				writer.close();

				writer = new PrintWriter(
						new File(directory_expDouble + algorithm + "\\" + problemName + "\\exec_time.txt"));
				writer.print(endTime_inSeconds);
				writer.close();

			} else if (binaryProblem) {
				final String directory_expBinary = "experimentBaseDirectory\\ExperimentsBinary\\data\\";

				writer = new PrintWriter(
						new File(directory_expBinary + algorithm + "\\" + problemName + "\\BEST_VAR_NAMES.txt"));
				writer.print(variable_names);
				writer.close();

				writer = new PrintWriter(
						new File(directory_expBinary + algorithm + "\\" + problemName + "\\BEST_FUN_NAMES.txt"));
				writer.print(objective_names);
				writer.close();

				writer = new PrintWriter(
						new File(directory_expBinary + algorithm + "\\" + problemName + "\\exec_time.txt"));
				writer.print(endTime_inSeconds);
				writer.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			writer.close();
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null, WARNING_FAILED_TO_WRITE_VARIABLE_OBJECTIVE_NAMES,
							WARNING_TITLE_WRITE_FAIL, 2);
				}
			}.start();
		} finally {
			writer.close();
		}
	}

	/**
	 * Prepares the data selected by the user.
	 * 
	 * @param data
	 * @return true data
	 * @author pvmpa-iscteiulpt
	 */
	private Object[][] parseTrueData(Object[][] data) {
		Object[][] true_data = new Object[variable_count][5];
		int true_data_iterator = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i][5].equals(Boolean.TRUE)) {
				true_data[true_data_iterator] = data[i];
				true_data_iterator++;
			}
		}
		// debugSysout_Parser(true_data);
		return true_data;
	}

	/**
	 * Prepares the objectives selected by the user.
	 * 
	 * @param objectives
	 * @param integerProblem
	 * @param doubleProblem
	 * @param binaryProblem
	 * @return true objectives
	 * @author pvmpa-iscteiulpt
	 */
	private Object[][] parseTrueObjectives(Object[][] objectives, boolean integerProblem, boolean doubleProblem,
			boolean binaryProblem) {
		int objectives_count = 0;
		for (int i = 0; i < objectives.length; i++) {
			if (objectives[i][2].equals(Boolean.TRUE)) {
				objectives_count++;
			}
		}

		Object[][] true_objectives = new Object[objectives_count][3];
		int true_objectives_iterator = 0;
		for (int i = 0; i < objectives.length; i++) {
			if (objectives[i][2].equals(Boolean.TRUE)) {
				checkDatatypeCombo(objectives, integerProblem, doubleProblem, binaryProblem, i);
				true_objectives[true_objectives_iterator] = objectives[i];
				true_objectives_iterator++;
			}
		}
		return true_objectives;
	}

	/**
	 * Verifies whether the selected objective is in conflict with the problem at
	 * hand or not.
	 * 
	 * @param objectives
	 * @param integerProblem
	 * @param doubleProblem
	 * @param binaryProblem
	 * @param i
	 * @author pvmpa-iscteiulpt
	 */
	private void checkDatatypeCombo(Object[][] objectives, boolean integerProblem, boolean doubleProblem,
			boolean binaryProblem, int i) {
		if (objectives[i][1].equals("Integer") && (doubleProblem || binaryProblem)) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null,
							WARNING_INVALID_DATA_TYPE_COMBO + "\nExpected Integer, got something else",
							WARNING_TITLE_INVALID_DATA_TYPE_COMBO, 2);
				}
			}.start();
			
			throw new IllegalStateException(WARNING_INVALID_DATA_TYPE_COMBO);

		} else if (objectives[i][1].equals("Double") && (integerProblem || binaryProblem)) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null,
							WARNING_INVALID_DATA_TYPE_COMBO + "\nExpected Double, got something else",
							WARNING_TITLE_INVALID_DATA_TYPE_COMBO, 2);
				}
			}.start();
			
			throw new IllegalStateException(WARNING_INVALID_DATA_TYPE_COMBO);
		} else if (objectives[i][1].equals("Binary") && (doubleProblem || integerProblem)) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null,
							WARNING_INVALID_DATA_TYPE_COMBO + "\nExpected Binary, got something else",
							WARNING_TITLE_INVALID_DATA_TYPE_COMBO, 2);
				}
			}.start();
			
			throw new IllegalStateException(WARNING_INVALID_DATA_TYPE_COMBO);
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
	private void verifyAlgorithmAndTypes(String algorithm, boolean integerProblem, boolean doubleProblem,
			boolean binaryProblem) {
		expectedAlgorithm = "";
		if (integerProblem && !doubleProblem && !binaryProblem) {
			for (int i = 0; i < AlgorithmsForIntegerProblemType.length; i++) {
				if (algorithm.equals(AlgorithmsForIntegerProblemType[i])) {
					System.out.println("Valid algorithm detected: " + AlgorithmsForIntegerProblemType[i]);
					break;
				}
				expectedAlgorithm += AlgorithmsForIntegerProblemType[i] + ", ";
				if (i == AlgorithmsForIntegerProblemType.length - 1) {
					
					new Thread() {
						public void run() {
							JOptionPane.showMessageDialog(null,
									WARNING_INVALID_ALGORITHM + "\nExpected one of the following algorithms: " 
									+ expectedAlgorithm,
									WARNING_TITLE_INVALID_ALGORITHM_SELECTED, 2);
						}
					}.start();
					
					throw new IllegalArgumentException(WARNING_INVALID_ALGORITHM);
				}
			}
		} else if (!integerProblem && doubleProblem && !binaryProblem) {
			for (int i = 0; i < AlgorithmsForDoubleProblemType.length; i++) {
				if (algorithm.equals(AlgorithmsForDoubleProblemType[i])) {
					System.out.println("Valid algorithm detected: " + AlgorithmsForDoubleProblemType[i]);
					break;
				}
				expectedAlgorithm += AlgorithmsForDoubleProblemType[i] + " ";
				if (i == AlgorithmsForDoubleProblemType.length - 1) {
					
					new Thread() {
						public void run() {
							JOptionPane.showMessageDialog(null,
									WARNING_INVALID_ALGORITHM + "\nExpected one of the following algorithms: " 
									+ expectedAlgorithm,
									WARNING_TITLE_INVALID_ALGORITHM_SELECTED, 2);
						}
					}.start();
					
					throw new IllegalArgumentException(WARNING_INVALID_ALGORITHM);
				}
			}
		} else if (!integerProblem && !doubleProblem && binaryProblem) {
			for (int i = 0; i < AlgorithmsForBinaryProblemType.length; i++) {
				if (algorithm.equals(AlgorithmsForBinaryProblemType[i])) {
					System.out.println("Valid algorithm detected: " + AlgorithmsForBinaryProblemType[i]);
					break;
				}
				expectedAlgorithm += AlgorithmsForBinaryProblemType[i] + ", ";
				if (i == AlgorithmsForBinaryProblemType.length - 1) {
					
					new Thread() {
						public void run() {
							JOptionPane.showMessageDialog(null,
									WARNING_INVALID_ALGORITHM + "\nExpected one of the following algorithms: " 
									+ expectedAlgorithm,
									WARNING_TITLE_INVALID_ALGORITHM_SELECTED, 2);
						}
					}.start();
					
					throw new IllegalArgumentException(WARNING_INVALID_ALGORITHM);
				}
			}
		} else {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null,
							WARNING_MULTIPLE_DATA_TYPES,
							WARNING_TITLE_MULTIPLE_DATA_TYPES, 2);
				}
			}.start();
			throw new IllegalArgumentException(WARNING_MULTIPLE_DATA_TYPES);
		}
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
	private void launchProblem(Object[][] data, Object[][] objectives, String algorithm, boolean integerProblem,
			boolean doubleProblem, boolean binaryProblem, boolean isJar) throws IOException {
		// is an integer Problem
		if (integerProblem && !doubleProblem && !binaryProblem) {
			ExperimentsInteger e = new ExperimentsInteger();
			int[][] limits = new int[variable_count][2];
			for (int i = 0; i < limits.length; i++) {
				if (!(data[i][2].equals(null)) && !(data[i][3].equals(null))) {
					limits[i][0] = Integer.parseInt((String) data[i][2]);
					limits[i][1] = Integer.parseInt((String) data[i][3]);
					// debug_Sysoutlimits_INT(limits, i);
				} else {
					new Thread() {
						public void run() {
							JOptionPane.showMessageDialog(null,
									WARNING_INVALID_MINMAX,
									WARNING_TITLE_INVALID_MINMAX, 2);
						}
					}.start();
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

		// is a double Problem
		else if (doubleProblem && !integerProblem && !binaryProblem) {
			ExperimentsDouble e = new ExperimentsDouble();
			double[][] limits = new double[variable_count][2];
			for (int i = 0; i < limits.length; i++) {
				if (!(data[i][2].equals(null)) && !(data[i][3].equals(null))) {
					limits[i][0] = Double.parseDouble((String) data[i][2]);
					limits[i][1] = Double.parseDouble((String) data[i][3]);
					// debug_Sysoutlimits_DOUBLE(limits, i);
				} else {
					new Thread() {
						public void run() {
							JOptionPane.showMessageDialog(null,
									WARNING_INVALID_MINMAX,
									WARNING_TITLE_INVALID_MINMAX, 2);
						}
					}.start();
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

		// is a binary Problem
		else if (binaryProblem && !integerProblem && !doubleProblem) {
			ExperimentsBinary e = new ExperimentsBinary();
			// TODO: WARNING WARNING WARNING THIS IS ASSUMING THAT THE FIRST VARIABLE HOLDS
			// THE ONE TRUE MAX VALUE
			e.setLimits_Binary(Integer.parseInt((String) data[0][3]));
			e.setAlgorithm(algorithm);
			e.setNumber_of_variables(data.length);
			e.setJar(isJar);
			e.setJarPath(jarPath);
			e.setProblemName(problemName);
			e.setNumber_of_objectives(objectives.length);
			e.setTimelimit(timelimit);
			e.execute();
		} else
			throw new IllegalStateException(
					"How in the world did this happen??? Couldn't find out what the problem was even though"
							+ " it should've already been specified?!?!");

	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public boolean isJar() {
		return isJar;
	}

	public void setJar(boolean isJar) {
		this.isJar = isJar;
	}

	public void setJarPath(String path) {
		jarPath = path;
	}

	public void setObjectives(Object[][] objectives) {
		this.objectives = objectives;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public long getTimelimit() {
		return timelimit;
	}

	public void setTimelimit(long timelimit) {
		this.timelimit = timelimit;
	}

}
