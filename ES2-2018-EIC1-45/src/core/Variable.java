package core;

public class Variable {
	
	private String variable_name;
	private String variable_type;
	private String variable_min_val;
	private String variable_max_val;
	private String variable_restricted;
	private String variable_used;

	
	public Variable() {
	}
	

	public String getVariable_name() {
		if(variable_name == null){
			return new String("");
		}
		return variable_name;
	}

	public void setVariable_name(String variable_name) {
		this.variable_name = variable_name;
	}

	public String getVariable_type() {
		if(variable_type == null){
			return new String("");
		}
		return variable_type;
	}

	public void setVariable_type(String variable_type) {
		this.variable_type = variable_type;
	}

	public String getVariable_min_val() {
		if(variable_min_val == null){
			return new String("");
		}
		return variable_min_val;
	}

	public void setVariable_min_val(String variable_min_val) {
		this.variable_min_val = variable_min_val;
	}

	public String getVariable_max_val() {
		if(variable_max_val == null){
			return new String("");
		}
		return variable_max_val;
	}

	public void setVariable_max_val(String variable_max_val) {
		this.variable_max_val = variable_max_val;
	}

	public String getVariable_restricted() {
		if(variable_restricted == null){
			return new String("");
		}
		return variable_restricted;
	}

	public void setVariable_restricted(String variable_restricted) {
		this.variable_restricted = variable_restricted;
	}

	public String isVariable_used() {
		if(variable_used == null){
			return new String("");
		}
		return variable_used;
	}

	public void setVariable_used(String variable_used) {
		if(variable_used.contains("true")){
			this.variable_used = "true";
		}else{
			this.variable_used = "false";
		}
	}

}
