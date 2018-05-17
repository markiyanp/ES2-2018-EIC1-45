package jMetal.doubleProblems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import email.EMail_Tools;
import jMetal.ProgressChecker;

public class MyProblemDouble extends AbstractDoubleProblem {

	/**
	* 
	*/
	private static final long serialVersionUID = 5661793940199968579L;

	private final static long startingTime = System.currentTimeMillis();
	//private final static long timeLimit = Window.getTimeLimit();
	private boolean useJar = false;
	
	private ProgressChecker progC;
	
	private String jarPath;

	private int testNumber = 0;

	public MyProblemDouble(double[][] limits, boolean isJar, String jarPath) {
		this.useJar = isJar;
		this.jarPath = jarPath;
		this.progC = new ProgressChecker(isJar);
		setNumberOfVariables(limits.length);
		System.out.println("Number of variables: " + limits.length);
		setNumberOfObjectives(2);
		setName("MyProblemDouble");

		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(limits[i][0]);
			upperLimit.add(limits[i][1]);
		}
		System.out.println(lowerLimit);
		System.out.println(upperLimit);
		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void evaluate(DoubleSolution solution) {
		if (System.currentTimeMillis() - startingTime <= 10000) {
		if (!useJar) {
			double[] fx = new double[getNumberOfObjectives()];
			double[] x = new double[getNumberOfVariables()];
			for (int i = 0; i < solution.getNumberOfVariables(); i++) {
				x[i] = solution.getVariableValue(i);
			}

			double[] solutionObjectives = Kursawe.kurzaseSolution(fx, x);
			solution.setObjective(0, solutionObjectives[0]);
			solution.setObjective(1, solutionObjectives[1]);
		} else {
			String solutionString = "";
			String evaluationResultString = "";
			for (int i = 0; i < solution.getNumberOfVariables(); i++) {
				solutionString = solutionString + " " + solution.getVariableValue(i);
			}
			try {
				String line;
				Process p = Runtime.getRuntime().exec("java -jar " + jarPath + " " + solutionString);
				BufferedReader brinput = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((line = brinput.readLine()) != null) {
					evaluationResultString += line;
				}
				brinput.close();
				p.waitFor();
			} catch (Exception err) {
				err.printStackTrace();
			}
			String[] individualEvaluationCriteria = evaluationResultString.split("\\s+");
			// It is assumed that all evaluated criteria are returned in the same result
			// string
			for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
				solution.setObjective(i, Double.parseDouble(individualEvaluationCriteria[i]));
			}
		}
		testNumber++;
		progC.checkProgress(testNumber);
		checkTimeLimit();
		}
	}
	
	public void checkTimeLimit() {
		
	}

}
