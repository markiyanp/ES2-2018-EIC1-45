package core;

/**
 * @author Markiyan Pyekh
 *
 */
public class Objective {
	
	private String name;
	private String type;
	private String used;
	

	/**
	 * The constructor
	 */
	public Objective() {
	}


	/**
	 * The constructor
	 * 
	 * @param string
	 * @param string2
	 * @param a
	 */
	public Objective(String string, String string2, String a) {
		this.name = string;
		this.type = string2;
		this.used = a;
	}


	/**
	 * Returns the objective name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Set a objective name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Returns the objective type
	 * 
	 * @return type
	 */
	public String getType() {
		return type;
	}


	/**
	 * Set a objective type
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * Returns the objective used
	 * 
	 * @return used
	 */
	public String isUsed() {
		if(used == null){
			return new String("");
		}
		return used;
	}


	/**
	 * Set a objective used
	 * 
	 * @param used
	 */
	public void setUsed(String used) {
		if(used.contains("true")){
			this.used = "true";
		}else{
			this.used = "false";
		}
	}

}
