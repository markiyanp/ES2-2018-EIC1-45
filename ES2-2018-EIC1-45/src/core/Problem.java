package core;

import java.util.ArrayList;

/**
 * @author Markiyan Pyekh
 *
 */
public class Problem {


	//***********ABOUT***************
	private String problem_name;
	private String problem_description;
	private String algorithm;
	//***********ABOUT***************

	//***********USER***************
	private String user_name;
	private String user_email;
	//***********USER***************

	//********VARIABLES*************
	private ArrayList<Variable> variables; 
	//********VARIABLES*************
	
	//********VARIABLES*************
	private ArrayList<Objective> objectives;
	//********VARIABLES*************

	/**
	 * The constructor
	 */
	public Problem() {
	}
	
	/**
	 * Returns the objectives list
	 * 
	 * @return objectives
	 */
	public ArrayList<Objective> getObjectives() {
		if(objectives == null){
			this.objectives = new ArrayList<>();
		}
		return objectives;
	}

	/**
	 * Set a objectives list
	 * 
	 * @param objectives
	 */
	public void setObjectives(ArrayList<Objective> objectives) {
		this.objectives = objectives;
	}

	/**
	 * Returns the problem name
	 * 
	 * @return problem_name
	 */
	public String getProblem_name() {
		if(problem_name == null){
			return new String("");
		}
		return problem_name;
	}

	/**
	 * Set a problem name
	 * 
	 * @param problem_name
	 */
	public void setProblem_name(String problem_name) {
		this.problem_name = problem_name;
	}

	/**
	 * Returns the problem description
	 * 
	 * @return problem_description
	 */
	public String getProblem_description() {
		if(problem_description == null){
			return new String("");
		}
		return problem_description;
	}

	/**
	 * Set a problem description
	 * 
	 * @param problem_description
	 */
	public void setProblem_description(String problem_description) {
		this.problem_description = problem_description;
	}

	/**
	 * Returns the algorithm
	 * 
	 * @return algorithm
	 */
	public String getAlgorithm() {
		if(algorithm == null){
			return new String("");
		}
		return algorithm;
	}

	/**
	 * Set a algorithm
	 * 
	 * @param algorithm
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * Returns the user name
	 * 
	 * @return user_name
	 */
	public String getUser_name() {
		if(user_name == null){
			return new String("");
		}
		return user_name;
	}

	/**
	 * Set a user name
	 * 
	 * @param user_name
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	/**
	 * Returns the user mail
	 * 
	 * @return user_email
	 */
	public String getUser_email() {
		if(user_email == null){
			return new String("");
		}
		return user_email;
	}

	/**
	 * Set a user mail
	 * 
	 * @param user_email
	 */
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	/**
	 * Returns the variables list
	 * 
	 * @return variable
	 */
	public ArrayList<Variable> getVariables() {
		if(variables == null){
			this.variables = new ArrayList<>();
		}
		return variables;
	}

	/**
	 * Set a variables list
	 * 
	 * @param variables
	 */
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
}