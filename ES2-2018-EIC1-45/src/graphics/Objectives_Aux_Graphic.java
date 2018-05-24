package graphics;

import java.util.ArrayList;

public class Objectives_Aux_Graphic {

	private String path;
	private ArrayList<String> objectives_name;
	
	public Objectives_Aux_Graphic(String path, ArrayList<String> objectives_name) {
		this.path = path;
		this.objectives_name = objectives_name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<String> getObjectives_name() {
		return objectives_name;
	}

	public void setObjectives_name(ArrayList<String> objectives_name) {
		this.objectives_name = objectives_name;
	}
	
}
