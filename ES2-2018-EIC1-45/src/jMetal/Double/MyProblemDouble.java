package jMetal.Double;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class MyProblemDouble extends AbstractDoubleProblem {

	/**
	* 
	*/
	private static final long serialVersionUID = 5661793940199968579L;

	private boolean useJar = false;

	public MyProblemDouble(double[][] limits, boolean isJar) {
		this.useJar = isJar;
		setNumberOfVariables(limits.length);
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
	}

}
