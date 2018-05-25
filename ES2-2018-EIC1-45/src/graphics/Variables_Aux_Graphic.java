package graphics;

import java.util.ArrayList;

/**
 * @author Tiago Almeida
 *
 */
public class Variables_Aux_Graphic {

	private String path;
	private ArrayList<String> variables_name;
	
	/**
	 * The construtor
	 * @param path
	 * @param variables_name
	 */
	public Variables_Aux_Graphic(String path, ArrayList<String> variables_name) {
		this.path = path;
		this.variables_name = variables_name;
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
	 * Returns the variables name
	 * @return variables_name
	 */
	public ArrayList<String> getVariables_name() {
		return variables_name;
	}

	/**
	 * Set a variabbles name
	 * @param variables_name
	 */
	public void setVariables_name(ArrayList<String> variables_name) {
		this.variables_name = variables_name;
	}
}
