package core;

public class Objective {
	
	private String name;
	private String type;
	private String used;
	

	public Objective() {
	}


	public Objective(String string, String string2, String a) {
		this.name = string;
		this.type = string2;
		this.used = a;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String isUsed() {
		if(used == null){
			return new String("");
		}
		return used;
	}


	public void setUsed(String used) {
		if(used.contains("true")){
			this.used = "true";
		}else{
			this.used = "false";
		}
	}

}
