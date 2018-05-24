package graphics;

import java.util.ArrayList;

public class Variables_Aux_Graphic {

	private String path;
	private ArrayList<String> variables_name;
	
	public Variables_Aux_Graphic(String path, ArrayList<String> variables_name) {
		this.path = path;
		this.variables_name = variables_name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<String> getVariables_name() {
		return variables_name;
	}

	public void setVariables_name(ArrayList<String> variables_name) {
		this.variables_name = variables_name;
	}
}
