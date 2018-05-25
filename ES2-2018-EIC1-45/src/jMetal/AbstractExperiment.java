package jMetal;

import java.io.IOException;

import javax.swing.JOptionPane;

public abstract class AbstractExperiment {

	private static final String WARNING_TITLE_404 = "Program not found!";
	private static final String WARNING_R_404 = "WARNING: RScript not detected. Do you have R installed? "
			+ "Has your Administrator correctly set up its path?";
	private static final String WARNING_MIKTEX_404 = "WARNING: miktex not detected. Do you have miktex installed? "
			+ "Has your Administrator correctly set up its path?";
	private String jarPath;
	private String problemName;
	private String algorithm;
	private double[][] limits_Double;
	private int[][] limits_Int;
	private int limits_Binary;
	private int number_of_variables; //for Binary only
	private int number_of_objectives;
	private boolean isJar;
	private long timelimit;
	
	public static final String DEFAULT_R_PATH = "C:\\Program Files\\R\\R-3.4.3\\bin\\RScript.exe"; 
	public static final String DEFAULT_LATEX_PATH = "C:\\Program Files\\MiKTeX-2.9\\miktex\\bin\\x64\\pdflatex.exe";

	/**
	 * Gets the current used jar path.
	 * @return jar path
	 */
	public String getJarPath() {
		return jarPath;
	}

	/**
	 * Gets the problem's name.
	 * @return problem's name.
	 */
	public String getProblemName() {
		return problemName;
	}

	/**
	 * Gets the used algorithm.
	 * @return the algorithm
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * USED ONLY FOR PROBLEMS USING DOUBLE VARIABLES.
	 * Gets the min/max values of the variable data.
	 * 
	 * @return min/max values
	 */
	public double[][] getLimits_Double() {
		return limits_Double;
	}

	/**
	 * Sets the jar path.
	 * @param jarPath
	 */
	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}

	/**
	 * Sets the problem's name.
	 * @param problemName
	 */
	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	/**
	 * Sets the problem's current algorithm.
	 * @param algorithm
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * USED ONLY FOR PROBLEMS USING DOUBLE VARIABLES.
	 * Sets the min/max values of the variable data.
	 * @param limits
	 */
	public void setLimits_Double(double[][] limits) {
		this.limits_Double = limits;
	}

	/**
	 * Gets whether a jar is used or not.
	 * @return using/not using a jar
	 */
	public boolean isJar() {
		return isJar;
	}

	/**
	 * Sets whether a jar is used or not.
	 * @param isJar
	 */
	public void setJar(boolean isJar) {
		this.isJar = isJar;
	}

	/**
	 * USED ONLY FOR PROBLEMS USING INTEGER VARIABLES.
	 * Gets the min/max values of the variable data.
	 * @return min/max values
	 */
	public int[][] getLimits_Int() {
		return limits_Int;
	}

	/**
	 * USED ONLY FOR PROBLEMS USING INTEGER VARIABLES.
	 * Sets the min/max values of the variable data.
	 * @param limits_Int
	 */
	public void setLimits_Int(int[][] limits_Int) {
		this.limits_Int = limits_Int;
	}

	/**
	 * USED ONLY FOR PROBLEMS USING DOUBLE LIMITS.
	 * Gets the min/max values of the variable data.
	 * @return min/max values
	 */
	public int getLimits_Binary() {
		return limits_Binary;
	}
	
	/**
	 * USED ONLY FOR BINARY PROBLEMS.
	 * Sets the number of used variables.
	 * @param limits_Binary
	 */
	public void setLimits_Binary(int limits_Binary) {
		this.limits_Binary = limits_Binary;
	}

	/**
	 * USED ONLY FOR BINARY PROBLEMS.
	 * Gets the number of used variables.
	 * @return the number of variables
	 */
	public int getNumber_of_variables() {
		return number_of_variables;
	}

	/**
	 * USED ONLY FOR BINARY PROBLEMS.
	 * Sets the number of used variables.
	 * @param number_of_variables
	 */
	public void setNumber_of_variables(int number_of_variables) {
		this.number_of_variables = number_of_variables;
	}

	/**
	 * Gets the number of objectives.
	 * @return number of objectives
	 */
	public int getNumber_of_objectives() {
		return number_of_objectives;
	}

	/**
	 * Sets the number of objectives.
	 * @param number_of_objectives
	 */
	public void setNumber_of_objectives(int number_of_objectives) {
		this.number_of_objectives = number_of_objectives;
	}

	/**
	 * Gets the problem's time limit.
	 * @return timelimit
	 */
	public long getTimelimit() {
		return timelimit;
	}

	/**
	 * Sets the problem's time limit.
	 * @param timelimit
	 */
	public void setTimelimit(long timelimit) {
		this.timelimit = timelimit;
	}
	
	/**
	 * Invokes the Script Generator in order to generate the R and PDF documents.
	 * @param r_path
	 * @param latex_path
	 * @param e
	 */
	public void generateDocuments(String r_path, String latex_path, AbstractExperiment e) {
		try {
			ScriptGenerator.generateR(r_path, e.getClass().getSimpleName());
			System.out.println("Generated R...");
		} catch (IOException e1) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null, WARNING_R_404, WARNING_TITLE_404,
							2);
				}
			}.start();
		}
		try {
			ScriptGenerator.generatorLatex(latex_path, e.getClass().getSimpleName());
			System.out.println("Generated LaTeX...");
		} catch (IOException e1) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null, WARNING_MIKTEX_404, WARNING_TITLE_404,
							2);
				}
			}.start();
		}
		
	}

}
