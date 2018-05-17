package jMetal.binaryProblems;

import org.apache.commons.mail.EmailException;
import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.solution.impl.DefaultBinarySolution;
import org.uma.jmetal.util.JMetalException;

import email.EMail_Tools;
import jMetal.ProgressChecker;
import visual.LeftPanel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.BitSet;

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
		if (index != 0) {
			throw new JMetalException("Problem MyBinaryProblem has only a variable. Index = " + index);
		}
		return bits;
	}

	@Override
	public BinarySolution createSolution() {
		return new DefaultBinarySolution(this);
	}

	@Override
	public void evaluate(BinarySolution solution) {
		if (System.currentTimeMillis() - startingTime <= 2000) {
			if (!useJar) {
				double[] solutionObjectives = OneZeroMax.OneZeroMaxSolution(solution);
				// OneZeroMax is a maximization problem: multiply by -1 to minimize
				solution.setObjective(0, solutionObjectives[0]);
				solution.setObjective(1, solutionObjectives[1]);
			} else {
				String solutionString = "";
				String evaluationResultString = "";
				BitSet bitset = solution.getVariableValue(0);
				solutionString = bitset.toString();
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
		}
	}


}
