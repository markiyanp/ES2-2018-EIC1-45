package jMetal.integerProblems;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;

import jMetal.JarEvaluator;
import jMetal.ProgressChecker;

import java.util.ArrayList;
import java.util.List;

public class MyProblemInteger extends AbstractIntegerProblem {
	private static final long serialVersionUID = 5053442100790906706L;

	private final static long startingTime = System.currentTimeMillis();

	private boolean useJar = false;
	private String jarPath;
	private ProgressChecker progC;
	private int testNumber = 0;
	private boolean barWarning = false;

	public MyProblemInteger(int[][] limits, boolean isJar, String jarPath) throws JMetalException {
		this.useJar = isJar;
		this.jarPath = jarPath;
		this.progC = new ProgressChecker(isJar);
		setNumberOfVariables(limits.length);
		setNumberOfObjectives(2);
		setName("MyProblemInteger");

		List<Integer> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Integer> upperLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(limits[i][0]);
			upperLimit.add(limits[i][1]);
		}

		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void evaluate(IntegerSolution solution) {
		if (System.currentTimeMillis() - startingTime <= 10000) {
			if (!useJar) {
				double[] fx = new double[getNumberOfObjectives()];
				int[] x = new int[getNumberOfVariables()];
				for (int i = 0; i < solution.getNumberOfVariables(); i++) {
					x[i] = solution.getVariableValue(i);
				}
				double[] solutionObjectives = NMMin.NMMinSolution(fx, x);
				solution.setObjective(0, solutionObjectives[0]);
				solution.setObjective(1, solutionObjectives[1]);
			} else {
				String[] individualEvaluationCriteria = JarEvaluator.jarEvaluate(solution, jarPath);
				// It is assumed that all evaluated criteria are returned in the same result
				// string
				for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
					solution.setObjective(i, Integer.parseInt(individualEvaluationCriteria[i]));
				}
			}
			testNumber++;
			try {
				progC.checkProgress(testNumber);
			} catch (NullPointerException e) {
				if (!barWarning) {
					System.out.println("WARNING: Progress bar not found. Ignoring error!");
					barWarning  = true;
				}
			}
		}
	}

}
