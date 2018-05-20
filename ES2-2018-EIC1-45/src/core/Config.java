package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Markiyan Pyekh, Tiago Almeida
 *
 */
public class Config {
	
	private String admin_name;
	private String admin_mail;
	private ArrayList<User> users;
	private HashMap<String, Path> paths;
	private String time;
	
	/**
	 * Returns the admin name
	 * 
	 * @return admin_name
	 */
	public String getAdmin_name() {
		if(admin_name == null){
			return new String("");
		}
		return admin_name;
	}

	/**
	 * Set a admin name
	 * 
	 * @param admin_name
	 */
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	/**
	 * Returns the admin mail
	 * 
	 * @return admin_mail
	 */
	public String getAdmin_mail() {
		if(admin_mail == null){
			return new String("");
		}
		return admin_mail;
	}

	/**
	 * Set a admin mail
	 * 
	 * @param admin_mail
	 */
	public void setAdmin_mail(String admin_mail) {
		this.admin_mail = admin_mail;
	}

	/**
	 * Returns the users list
	 * 
	 * @return users
	 */
	public ArrayList<User> getUsers() {
		if(users == null){
			this.users = new ArrayList<User>();
		}
		return users;
	}

	/**
	 * Set a users list
	 * 
	 * @param users
	 */
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	/**
	 * Returns the path
	 * 
	 * @return paths
	 */
	public HashMap<String, Path> getPaths() {
		if(paths == null){
			this.paths =  new  HashMap<String, Path>();
		}
		return paths;
	}

	/**
	 * Set a path
	 * 
	 * @param paths
	 */
	public void setPaths( HashMap<String, Path> paths) {
		this.paths = paths;
	}

	/**
	 * Returns the admin
	 * 
	 * @return user
	 */
	public User getAdmin() {
		return new User(admin_name, admin_mail);
	}
	
	/**
	 * Returns the limit time
	 * 
	 * @return limitTime
	 */
	public LimitTime getLimitTime() {
		return new LimitTime(time);
	}
	
	/**
	 * Returns the time
	 * 
	 * @return time
	 */
	public String getTime() {
		if(time == null){
			return new String("");
		}
		return time;
	}
	
	/**
	 * Set a time
	 * 
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
