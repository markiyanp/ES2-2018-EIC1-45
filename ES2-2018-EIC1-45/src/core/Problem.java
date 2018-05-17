package core;

import java.util.ArrayList;

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
	ArrayList<Variable> variables; 
	//********VARIABLES*************
	
	//********VARIABLES*************
	ArrayList<Objective> objectives;
	//********VARIABLES*************

	public Problem() {
	}
	
	public ArrayList<Objective> getObjectives() {
		if(objectives == null){
			this.objectives = new ArrayList<>();
		}
		return objectives;
	}

	public void setObjectives(ArrayList<Objective> objectives) {
		this.objectives = objectives;
	}

	public String getProblem_name() {
		if(problem_name == null){
			return new String("");
		}
		return problem_name;
	}

	public void setProblem_name(String problem_name) {
		this.problem_name = problem_name;
	}

	public String getProblem_description() {
		if(problem_description == null){
			return new String("");
		}
		return problem_description;
	}

	public void setProblem_description(String problem_description) {
		this.problem_description = problem_description;
	}

	public String getAlgorithm() {
		if(algorithm == null){
			return new String("");
		}
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getUser_name() {
		if(user_name == null){
			return new String("");
		}
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		if(user_email == null){
			return new String("");
		}
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public ArrayList<Variable> getVariables() {
		if(variables == null){
			this.variables = new ArrayList<>();
		}
		return variables;
	}

	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
}