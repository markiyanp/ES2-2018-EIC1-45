package jMetal;

import java.io.IOException;
import java.lang.Boolean;

import jMetal.Binary.ExperimentsBinary;
import jMetal.Double.ExperimentsDouble;
import jMetal.Integer.ExperimentsInteger;

public class OptimizationProcess {
	
	private static String[] AlgorithmsForDoubleProblemType = new String[]{"NSGAII","SMSEMOA","GDE3","IBEA","MOCell","MOEAD","PAES","RandomSearch"};
	private static String[] AlgorithmsForIntegerProblemType = new String[]{"NSGAII","SMSEMOA","MOCell","PAES","RandomSearch"};
	private static String[] AlgorithmsForBinaryProblemType = new String[]{"NSGAII","SMSEMOA","MOCell","MOCH","PAES","RandomSearch","SPEA2"};	

	/**Executes a problem depending on the data fed from the GUI's table, the chosen algorithm, and whether a jar is used or not.
	 * 
	 * @param data
	 * @param algorithm
	 * @param isJar
	 * @author pvmpa-iscteiulpt
	 */
	public static void runOptimization(Object[][] data, String algorithm, boolean isJar) {
		try {
			boolean integerProblem = false, 
					doubleProblem = false, 
					binaryProblem = false;
			
			for (int i = 0; i < data.length; i++) {
				if (data[i][1].equals("Integer") && (data[i][5].equals(Boolean.TRUE))) {
					integerProblem = true;
				}
				else if (data[i][1].equals("Double") && (data[i][5].equals(Boolean.TRUE))) {
					doubleProblem = true;
				}
				else if (data[i][1].equals("Binary") && (data[i][5].equals(Boolean.TRUE))) {
					binaryProblem = true;
				}
			}
			
			verifyAlgorithmAndTypes(algorithm, integerProblem, doubleProblem, binaryProblem);
			
			launchProblem(data, algorithm, integerProblem, doubleProblem, binaryProblem, isJar);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**Verifies whether a valid algorithm was chosen or not. Also verifies if there are any datatypes mixed together.
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
					break;
				}
				if (i == AlgorithmsForIntegerProblemType.length - 1) {
					throw new IllegalArgumentException("Invalid algorithm for problem type!");
				}
			}
		}
		else if (!integerProblem && doubleProblem && !binaryProblem) {
			for (int i = 0; i < AlgorithmsForDoubleProblemType.length; i++) {
				if (algorithm.equals(AlgorithmsForDoubleProblemType[i])) {
					break;
				}
				if (i == AlgorithmsForDoubleProblemType.length - 1) {
					throw new IllegalArgumentException("Invalid algorithm for problem type!");
				}
			}
		}
		else if (!integerProblem && !doubleProblem && binaryProblem) {
			for (int i = 0; i < AlgorithmsForBinaryProblemType.length; i++) {
				if (algorithm.equals(AlgorithmsForBinaryProblemType[i])) {
					break;
				}
				if (i == AlgorithmsForBinaryProblemType.length - 1) {
					throw new IllegalArgumentException("Invalid algorithm for problem type!");
				}
			}
		}
		else throw new IllegalArgumentException("Multiple data types detected!");
	} 
	
	/**Launches a problem after parsing which datatype it pertains to.
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
		if (integerProblem && !doubleProblem && !binaryProblem) {
			int[][] limits = new int[data.length][2];
			for (int i = 0; i < limits.length; i++) {
				limits[i][0] = Integer.parseInt((String) data[i][2]);
				limits[i][1] = Integer.parseInt((String) data[i][3]);
			}
			ExperimentsInteger.execute(limits, algorithm, isJar);
		}
		else if (doubleProblem && !integerProblem && !binaryProblem) {
			double[][] limits = new double[data.length][2];
			for (int i = 0; i < limits.length; i++) {
				limits[i][0] = Double.parseDouble((String) data[i][2]);
				limits[i][1] = Double.parseDouble((String) data[i][3]);
			}
			ExperimentsDouble.execute(limits, algorithm, isJar);
		}
		else if (binaryProblem && !integerProblem && !doubleProblem) {
			//TODO: WARNING WARNING WARNING THIS IS ASSUMING THAT 8 IS THE NUMBER OF BITS
			ExperimentsBinary.execute(8, algorithm, data.length, isJar);
		}
		else throw new IllegalStateException("How in the world did this happen???");
		
	}
}
