package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Config {
	
	private String admin_name;
	private String admin_mail;
	private ArrayList<User> users;
	private HashMap<String, Path> paths;
	private String time;
	
	
	public String getAdmin_name() {
		if(admin_name == null){
			return new String("");
		}
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public String getAdmin_mail() {
		if(admin_mail == null){
			return new String("");
		}
		return admin_mail;
	}

	public void setAdmin_mail(String admin_mail) {
		this.admin_mail = admin_mail;
	}

	public ArrayList<User> getUsers() {
		if(users == null){
			this.users = new ArrayList<User>();
		}
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public HashMap<String, Path> getPaths() {
		if(paths == null){
			this.paths =  new  HashMap<String, Path>();
		}
		return paths;
	}

	public void setPaths( HashMap<String, Path> paths) {
		this.paths = paths;
	}

	public User getAdmin() {
		return new User(admin_name, admin_mail);
	}
	
	public LimitTime getLimitTime() {
		return new LimitTime(time);
	}
	
	public String getTime() {
		if(time == null){
			return new String("");
		}
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
