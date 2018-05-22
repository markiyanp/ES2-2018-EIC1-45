package jMetal;

public abstract class AbstractExperiment {

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

	public String getJarPath() {
		return jarPath;
	}

	public String getProblemName() {
		return problemName;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public double[][] getLimits_Double() {
		return limits_Double;
	}

	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setLimits_Double(double[][] limits) {
		this.limits_Double = limits;
	}

	public boolean isJar() {
		return isJar;
	}

	public void setJar(boolean isJar) {
		this.isJar = isJar;
	}

	public int[][] getLimits_Int() {
		return limits_Int;
	}

	public void setLimits_Int(int[][] limits_Int) {
		this.limits_Int = limits_Int;
	}

	public int getLimits_Binary() {
		return limits_Binary;
	}

	public void setLimits_Binary(int limits_Binary) {
		this.limits_Binary = limits_Binary;
	}

	public int getNumber_of_variables() {
		return number_of_variables;
	}

	public void setNumber_of_variables(int number_of_variables) {
		this.number_of_variables = number_of_variables;
	}

	public int getNumber_of_objectives() {
		return number_of_objectives;
	}

	public void setNumber_of_objectives(int number_of_objectives) {
		this.number_of_objectives = number_of_objectives;
	}

	public long getTimelimit() {
		return timelimit;
	}

	public void setTimelimit(long timelimit) {
		this.timelimit = timelimit;
	}

}
