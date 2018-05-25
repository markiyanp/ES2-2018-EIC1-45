package jMetal.binaryProblems;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;

import jMetal.JarEvaluator;
import jMetal.ProgressChecker;

/**
 * Handles Binary problems.
 * 
 * @author vbasto-iscte and pvmpa-iscteiulpt
 *
 */
public class MyProblemBinary extends AbstractBinaryProblem {
	/**
	* 
	*/
	private static final long serialVersionUID = 1720856094158596822L;

	private final long startingTime = System.currentTimeMillis();

	private int bits;
	private boolean useJar = false;

	private ProgressChecker progC;

	private String jarPath;
	private int testNumber = 0;
	private boolean barWarning = false;

	private long timelimit;

	/**
	 * Constructs a Binary problem.
	 * 
	 * @param numberOfBits
	 * @param number_of_objectives
	 * @param number_of_variables
	 * @param isJar
	 * @param jarPath
	 * @param problemName
	 * @param timelimit
	 */
	public MyProblemBinary(Integer numberOfBits, int number_of_objectives, int number_of_variables, boolean isJar,
			String jarPath, String problemName, long timelimit) {
		this.useJar = isJar;
		this.jarPath = jarPath;
		this.progC = new ProgressChecker(useJar);
		this.timelimit = timelimit;

		setNumberOfVariables(number_of_variables);
		setNumberOfObjectives(number_of_objectives);
		setName(problemName);
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
		if (System.currentTimeMillis() - startingTime <= timelimit) {
			if (!useJar) {
				double[] solutionObjectives = OneZeroMax.OneZeroMaxSolution(solution);
				for (int i = 0; i < solutionObjectives.length; i++) {
					solution.setObjective(i, solutionObjectives[i]);
				}
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
