package jMetal;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.uma.jmetal.solution.Solution;

/**
 * Evaluates a problem through an algorithm encapsulated within a jar.
 * @author pvmpa-iscteiulpt
 *
 */
public class JarEvaluator {
	
	private JarEvaluator() {
		//this class should never be instatiated!
	}
	
	/**
	 * Evaluates any problem of any nature through a jar containing the appropriate algorithm.
	 * @param solution
	 * @param jarPath
	 * @author pvmpa-iscteiulpt
	 * @return Objectives for the solutions in an array of Strings
	 */
	public static String[] jarEvaluate(Solution<?> solution, String jarPath) {
		String solutionString = "";
		String evaluationResultString = "";
		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			solutionString = solutionString + " " + solution.getVariableValue(i);
		}
		try {
			String line;
			Process p = Runtime.getRuntime().exec("java -jar " + jarPath + " " + solutionString.trim());
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
		return individualEvaluationCriteria;
	}
	
}
