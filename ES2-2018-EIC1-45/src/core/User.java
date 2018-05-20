package core;

import java.util.ArrayList;

/**
 * @author Tiago Almeida, Markiyan Pyekh
 *
 */
public class User {
//	private  String name = "default";
//	private  String mail = "group45.dummy.user.1@gmail.com";
	
	private String name;
	private String mail;
	private ArrayList<String> algorithms;
	private String create_var;
	
	/**
	 * The constructor
	 */
	public User() {
	}
	
	/**
	 * The constructor
	 * 
	 * @param name
	 * @param mail
	 */
	public User(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
	
	/**
	 * The constructor
	 * 
	 * @param name
	 * @param mail
	 * @param algorithms
	 * @param create_var
	 */
	public User(String name, String mail, ArrayList<String> algorithms, String create_var) {
		this.name = name;
		this.mail = mail;
		this.algorithms = algorithms;
		this.create_var = create_var;
	}
	
	/**
	 * Returns the user mail
	 * 
	 * @return mail
	 */
	public  String getEmailAddr() {
		return mail;
	}
	
	/**
	 * Set a user mail
	 * 
	 * @param emailAddr
	 */
	public  void setEmailAddr(String emailAddr) {
		this.mail = emailAddr;
	}

	/**
	 * Returns the algorithms list
	 * 
	 * @return algorithms
	 */
	public ArrayList<String> getAlgorithms() {
		return algorithms;
	}

	/**
	 * Set a algorithms list
	 * 
	 * @param algorithms
	 */
	public void setAlgorithms(ArrayList<String> algorithms) {
		this.algorithms = algorithms;
	}

	/**
	 * Returns the user name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set a user name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the user create var
	 * 
	 * @return create_var
	 */
	public String getCreate_var() {
		return create_var;
	}

	/**
	 * Set a user create var
	 * 
	 * @param create_var
	 */
	public void setCreate_var(String create_var) {
		this.create_var = create_var;
	}
	
}
