package jMetal.doubleProblems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import email.EMail_Tools;

public class MyProblemDouble extends AbstractDoubleProblem {

	/**
	* 
	*/
	private static final long serialVersionUID = 5661793940199968579L;

	private boolean useJar = false;
	private boolean email25 = false;
	private boolean email50 = false;
	private boolean email75 = false;

	private int testNumber = 0;

	public MyProblemDouble(double[][] limits, boolean isJar) {
		this.useJar = isJar;
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
		
		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void evaluate(DoubleSolution solution) {
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
				Process p = Runtime.getRuntime().exec("java -jar c:\\Kursawe.jar" + " " + solutionString);
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
