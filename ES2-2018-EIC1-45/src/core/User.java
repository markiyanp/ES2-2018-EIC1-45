package core;

import java.util.ArrayList;

public class User {
//	private  String name = "default";
//	private  String mail = "group45.dummy.user.1@gmail.com";
	
	private String name;
	private String mail;
	private ArrayList<String> algorithms;
	private String create_var;
	
	public User() {
	}
	
	public User(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
	
	public User(String name, String mail, ArrayList<String> algorithms, String create_var) {
		this.name = name;
		this.mail = mail;
		this.algorithms = algorithms;
		this.create_var = create_var;
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

	public String getCreate_var() {
		return create_var;
	}

	public void setCreate_var(String create_var) {
		this.create_var = create_var;
	}
	
}
