package graphics;

import java.util.ArrayList;

/**
 * @author Tiago Almeida
 *
 */
public class Objectives_Aux_Graphic {

	private String path;
	private ArrayList<String> objectives_name;
	
	/**
	 * The construtor
	 * @param path
	 * @param objectives_name
	 */
	public Objectives_Aux_Graphic(String path, ArrayList<String> objectives_name) {
		this.path = path;
		this.objectives_name = objectives_name;
	}

	/**
	 * Returns the path
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Set a path
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Returns the objectives name
	 * @return objectives_name
	 */
	public ArrayList<String> getObjectives_name() {
		return objectives_name;
	}

	/**
	 * Set a objectives name
	 * @param objectives_name
	 */
	public void setObjectives_name(ArrayList<String> objectives_name) {
		this.objectives_name = objectives_name;
	}
	
}
