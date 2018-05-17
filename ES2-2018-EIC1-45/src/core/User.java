package core;

import java.util.ArrayList;

public class User {
//	private  String name = "default";
//	private  String mail = "group45.dummy.user.1@gmail.com";
	
	private String name;
	private String mail;
	private ArrayList<String> algorithms;
	private String create_var;
	private String upload_jars;
	private String max_var;
	private String max_obj;
	
	public User() {
	}
	
	public User(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
	
	public User(String name, String mail, ArrayList<String> algorithms, String create_var, String upload_jars, String max_var, String max_obj) {
		this.name = name;
		this.mail = mail;
		this.algorithms = algorithms;
		this.create_var = create_var;
		this.upload_jars = upload_jars;
		this.max_var = max_var;
		this.max_obj = max_obj;
	}
	
	public  String getEmailAddr() {
		return mail;
	}
	
	public  void setEmailAddr(String emailAddr) {
		this.mail = emailAddr;
	}

	public ArrayList<String> getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(ArrayList<String> algorithms) {
		this.algorithms = algorithms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCreate_var() {
		return create_var;
	}

	public void setCreate_var(String create_var) {
		this.create_var = create_var;
	}

	public String getUpload_jars() {
		return upload_jars;
	}

	public void setUpload_jars(String upload_jars) {
		this.upload_jars = upload_jars;
	}

	public String getMax_var() {
		return max_var;
	}

	public void setMax_var(String max_var) {
		this.max_var = max_var;
	}

	public String getMax_obj() {
		return max_obj;
	}

	public void setMax_obj(String max_obj) {
		this.max_obj = max_obj;
	}
}
