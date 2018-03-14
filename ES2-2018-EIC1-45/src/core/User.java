package core;

/**
 * This class simulates an User for the time being.<p>
 * It should be updated when real users are going to be implemented!
 * 
 * @author pvmpa-iscteiulpt
 *
 */
public class User {
	private static String username = "default";
	private static String emailAddr = "group45.dummy.user.1@gmail.com";
	
	public static String getUsername() {
		return username;
	}
	
	public static void setUsername(String username) {
		User.username = username;
	}
	
	public static String getEmailAddr() {
		return emailAddr;
	}
	
	public static void setEmailAddr(String emailAddr) {
		User.emailAddr = emailAddr;
	}
	
	
	
}
