package jMetal;

import java.io.IOException;
import java.lang.Boolean;

import jMetal.Double.ExperimentsDouble;
import jMetal.Double.ExperimentsDoubleExternalViaJAR;

public class OptimizationProcess {
	
/* O conjunto de algoritmos adequados a cada tipo de problema estão indicados aqui */
	private static String[] AlgorithmsForDoubleProblemType = new String[]{"NSGAII","SMSEMOA","GDE3","IBEA","MOCell","MOEAD","PAES","RandomSearch"};
	private static String[] AlgorithmsForIntegerProblemType = new String[]{"NSGAII","SMSEMOA","MOCell","PAES","RandomSearch"};
	private static String[] AlgorithmsForBinaryProblemType = new String[]{"NSGAII","SMSEMOA","MOCell","MOCH","PAES","RandomSearch","SPEA2"};	

	public static void runOptimization(Object[][] data, String algorithm) {
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
			
			verifyAlgorithm(algorithm, integerProblem, doubleProblem, binaryProblem);
			
			
/* Deverão ser comentadas ou retiradas de comentário as linhas 
   correspondentes às simulações que se pretendem executar */
//			ExperimentsDouble.main(null);
//			ExperimentsInteger.main(null);
//			ExperimentsBinary.main(null);

/* As simulações com ExternalViaJAR no nome tem as funções de avaliação 
   implementadas em .JAR externos que são invocados no método evaluate() 
   As simulações que executam .jar externos são muito mais demoradas, 
   maxEvaluations e INDEPENDENT_RUNS tem por isso valores mais baixos */
//			ExperimentsDoubleExternalViaJAR.main(null);
//			ExperimentsIntegeExternalViaJAR.main(null);
//			ExperimentsBinaryExternalViaJAR.main(null);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void verifyAlgorithm(String algorithm, boolean integerProblem, boolean doubleProblem,
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
}
