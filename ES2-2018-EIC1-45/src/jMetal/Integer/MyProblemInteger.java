package jMetal.Integer;

import org.uma.jmetal.problem.impl.AbstractIntegerProblem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.JMetalException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MyProblemInteger extends AbstractIntegerProblem {

	/**
	* 
	*/
	private static final long serialVersionUID = 5053442100790906706L;

	public MyProblemInteger(int[][] limits) throws JMetalException {
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
		double[] fx = new double[getNumberOfObjectives()];
		int[] x = new int[getNumberOfVariables()];
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			x[i] = solution.getVariableValue(i);
		}

		double[] solutionObjectives = NMMin.NMMinSolution(fx, x);
		solution.setObjective(0, solutionObjectives[0]);
		solution.setObjective(1, solutionObjectives[1]);
	}

	public void evaluateViaJar(IntegerSolution solution) {
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

}
