package core;

public class User {
//	private  String name = "default";
//	private  String mail = "group45.dummy.user.1@gmail.com";
	
	private  String name;
	private  String mail;
	
	public User() {
	}
	
	public User(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
	
	public  String getUsername() {
		return name;
	}
	
	public  void setUsername(String username) {
		this.name = username;
	}
	
	public  String getEmailAddr() {
		return mail;
	}
	
	public  void setEmailAddr(String emailAddr) {
		this.mail = emailAddr;
	}
}
