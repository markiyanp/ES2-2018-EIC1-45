package jMetal.binaryProblems;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;

import jMetal.JarEvaluator;
import jMetal.ProgressChecker;

public class MyProblemBinary extends AbstractBinaryProblem {
	/**
	* 
	*/
	private static final long serialVersionUID = 1720856094158596822L;

	private final static long startingTime = System.currentTimeMillis();

	private int bits;
	private boolean useJar = false;
	
	private ProgressChecker progC;
	
	private String jarPath;
	
	private int testNumber = 0;

	private boolean barWarning = false;

	public MyProblemBinary(Integer numberOfBits, int number_of_variables, boolean isJar, String jarPath) {
		this.useJar = isJar;
		this.jarPath = jarPath;
		this.progC = new ProgressChecker(useJar);
		setNumberOfVariables(number_of_variables);
		setNumberOfObjectives(2);
		setName("MyProblemBinary");
		bits = numberOfBits;
	}

	@Override
	protected int getBitsPerVariable(int index) {
		return bits;
	}

	@Override
	public BinarySolution createSolution() {
		return new DefaultBinarySolution(this);
	}

	@Override
	public void evaluate(BinarySolution solution) {
		if (System.currentTimeMillis() - startingTime <= 10000) {
			if (!useJar) {
				double[] solutionObjectives = OneZeroMax.OneZeroMaxSolution(solution);
				// OneZeroMax is a maximization problem: multiply by -1 to minimize
				solution.setObjective(0, solutionObjectives[0]);
				solution.setObjective(1, solutionObjectives[1]);
			} else {
				String[] individualEvaluationCriteria = JarEvaluator.jarEvaluate(solution, jarPath);
				// It is assumed that all evaluated criteria are returned in the same result
				// string
				for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
					solution.setObjective(i, Double.parseDouble(individualEvaluationCriteria[i]));
				}
			}
			testNumber++;
			try {
				progC.checkProgress(testNumber);
			} catch (NullPointerException e) {
				if (!barWarning) {
					System.out.println("WARNING: Progress bar not found. Ignoring error!");
					barWarning = true;
				}
			}
		}
	}


}
