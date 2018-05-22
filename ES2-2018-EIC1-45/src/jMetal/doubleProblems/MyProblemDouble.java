package jMetal.doubleProblems;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import jMetal.JarEvaluator;
import jMetal.ProgressChecker;

public class MyProblemDouble extends AbstractDoubleProblem {

	/**
	* 
	*/
	private static final long serialVersionUID = 5661793940199968579L;

	private final static long startingTime = System.currentTimeMillis();
	//private final static long timeLimit = Window.getTimeLimit();
	private boolean useJar = false;
	private boolean barWarning = false;
	
	private ProgressChecker progC;
	
	private String jarPath;

	private int testNumber = 0;
	
	private long timelimit;

	public MyProblemDouble(double[][] limits, int number_of_objectives, boolean isJar, String jarPath, String problemName, long timelimit) {
		this.useJar = isJar;
		this.jarPath = jarPath;
		this.progC = new ProgressChecker(isJar);
		this.timelimit = timelimit;
		
		setNumberOfVariables(limits.length);
		setNumberOfObjectives(number_of_objectives);
		setName(problemName);

		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(limits[i][0]);
			upperLimit.add(limits[i][1]);
		}
		
		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void evaluate(DoubleSolution solution) {
		if (System.currentTimeMillis() - startingTime <= timelimit) {
			if (!useJar) {
				double[] fx = new double[getNumberOfObjectives()];
				double[] x = new double[getNumberOfVariables()];
				for (int i = 0; i < solution.getNumberOfVariables(); i++) {
					x[i] = solution.getVariableValue(i);
				}
				double[] solutionObjectives = Kursawe.kurzaseSolution(fx, x);
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
