package jMetal.integerProblems;

import org.apache.commons.mail.EmailException;
import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;

import email.EMail_Tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyProblemInteger extends AbstractIntegerProblem {

	/**
	* 
	*/
	private static final long serialVersionUID = 5053442100790906706L;

	private boolean useJar = false;
	private boolean email25 = false;
	private boolean email50 = false;
	private boolean email75 = false;
	
	private int testNumber = 0;
	
	public MyProblemInteger(int[][] limits, boolean isJar) throws JMetalException {
		this.useJar = isJar;
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
		if (!useJar) {
			double[] fx = new double[getNumberOfObjectives()];
			int[] x = new int[getNumberOfVariables()];
			for (int i = 0; i < solution.getNumberOfVariables(); i++) {
				x[i] = solution.getVariableValue(i);
			}
			double[] solutionObjectives = NMMin.NMMinSolution(fx, x);
			solution.setObjective(0, solutionObjectives[0]);
			solution.setObjective(1, solutionObjectives[1]);
		}
		else {
			String solutionString = "";
			String evaluationResultString = "";
			for (int i = 0; i < solution.getNumberOfVariables(); i++) {
				solutionString = solutionString + " " + solution.getVariableValue(i);
			}
			try {
				String line;
				Process p = Runtime.getRuntime().exec("java -jar c:\\NMMin.jar" + " " + solutionString);
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
				solution.setObjective(i, Integer.parseInt(individualEvaluationCriteria[i]));
			}
		}
		testNumber++;
		checkProgress(testNumber);
		
	}

	/**
	 * checks test progress, if progress is 25,50,75 or 100% an email is sent to user
	 * @param testNumber
	 */
	public void checkProgress(int testNumber) {
		double numberTests;
		if (useJar)
			numberTests = 500;
		else
			numberTests = 2500;
		double progress = testNumber/numberTests;
		try {
			if (progress >= 0.25 && !email25) {
				EMail_Tools.sendProgressMail(25);
				email25 = true;
			}
			if (progress >= 0.5 && !email50) {
				EMail_Tools.sendProgressMail(50);
				email50 = true;
			}
			if (progress >= 0.75 && !email75) {
				EMail_Tools.sendProgressMail(75);
				email75 = true;
			}
			if (progress == 1) {
				EMail_Tools.sendProgressMail(100);
			} 
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
