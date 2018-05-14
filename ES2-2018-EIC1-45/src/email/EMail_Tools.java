package email;

import java.io.File;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * Harbors several functions that are useful for the e-mail functionality of this program.
 * @author pvmpa-iscteiulpt
 *
 */
public class EMail_Tools {
	
	//temporary!!! The admin_email will be imported from a XML file...
	private static final String ADMIN_EMAIL = "AGrupo45@gmail.com";
	//This class should never be instatiated!
	private EMail_Tools(){
		
	}
	
	protected static Object[] mailProviderToSMTP(String email){
		int i;
		for (i = 0; i < email.length(); i++){
			if (email.charAt(i) == '@'){
				email = email.substring(i+1);
			}
		}
		Object[] smtp_and_port = new Object[3];
		switch (email) {
		case "gmail.com":
			smtp_and_port[0] = "smtp.gmail.com";
			smtp_and_port[1] = "465";
			smtp_and_port[2] = Boolean.TRUE;
			return smtp_and_port;
		case "mail.com":
			smtp_and_port[0] = "smtp.mail.com";
			smtp_and_port[1] = "587";
			smtp_and_port[2] = Boolean.FALSE;
			return smtp_and_port;
		}
		
		throw new IllegalArgumentException("WARNING: Invalid e-mail detected! Failed to parse provider!");
	}
	
	
	public static boolean checkAuth(String userAddr, String userPw, String reason) {
		
		if (reason.isEmpty()) {
			throw new IllegalArgumentException("You must provide a reason to check authentication.");
		}
		
		try {
			System.out.println("Attempting to send warning e-mail");
			sendMail(userAddr, userPw, userAddr, null, "[OPTIMIZATION PROGRAM] User activity detected", reason, null);
			return true;
		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**Sends an e-mail.
	 * 
	 * @param userAddr
	 * @param userPw
	 * @param destinationAddr
	 * @param cc
	 * @param subject
	 * @param body
	 * @param attachmentPath
	 * @author pvmpa-iscteiulpt
	 * @throws EmailException
	 */
	public static void sendMail(
			String userAddr, 
			String userPw, 
			String destinationAddr,
			String[] cc,
			String subject, 
			String body, 
			String attachmentPath) throws EmailException{
		
		//Get the appropriate SMTP server. If this fails, the entire sequence is aborted.
		Object[] smtp_and_port = mailProviderToSMTP(userAddr);
		
		boolean validAttachment = false;
		boolean validCc = false;
		
		if (attachmentPath != null) {
			File file = new File(attachmentPath);
			if ((!file.isDirectory() && file.exists())){
				validAttachment = true;
				System.out.println("Attachment detected!");
			}
		}
		
		if (cc != null){ 
			//chained if required or else we might get a null pointer exception
			if (cc.length > 0) {
				validCc = true;
			}
		}
		
		if (validAttachment){
			
			File file = new File(attachmentPath);
			System.out.println(file.getName());
			
			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(attachmentPath);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("ES2-2018-EIC1-45");
			attachment.setName(file.getName());
			
			MultiPartEmail email = new MultiPartEmail();
			email.setHostName((String) smtp_and_port[0]);
			email.setSmtpPort(Integer.parseInt((String) smtp_and_port[1]));
			email.setSslSmtpPort((String) smtp_and_port[1]);
			
			email.setAuthenticator(new DefaultAuthenticator(userAddr, userPw));
			
			email.setSSLOnConnect((Boolean)smtp_and_port[2]);
			
			email.setFrom(userAddr);
			email.addTo(destinationAddr);
			email.setSubject(subject);
			email.setMsg(body);
			
			email.attach(attachment);
			
			if (validCc){
				for (int i = 0; i < cc.length; i++){
					email.addCc(cc[i]);
				}
			}
			
			System.out.println("Sending e-mail (with attachment)");
			email.send();
			
		}
		else{
			Email email = new SimpleEmail();
			
			email.setHostName((String) smtp_and_port[0]);
			email.setSmtpPort(Integer.parseInt((String) smtp_and_port[1]));
			email.setSslSmtpPort((String) smtp_and_port[1]);
			
			email.setAuthenticator(new DefaultAuthenticator(userAddr, userPw));
			email.setSSLOnConnect((Boolean)smtp_and_port[2]);
			
			email.setFrom(userAddr);
			email.addTo(destinationAddr);
			email.setSubject(subject);
			email.setMsg(body);
			
			if (validCc){
				for (int i = 0; i < cc.length; i++){
					email.addCc(cc[i]);
				}
			}
			
			System.out.println("Sending e-mail (no attachment)");
			email.send();
		}
		
	}
	
	public static void sendProgressMail(int progress) throws EmailException {
//		if (progress != 100)
//			sendMail("group45.optimization.bot@gmail.com", "", "group45.dummy.user.1@gmail.com", null, "Your Optimization is currently at " + progress, progressEmailBody + progress, null);
//		else
//			sendMail("group45.optimization.bot@gmail.com", "", "group45.dummy.user.1@gmail.com", null, "Your Optimization is completed!", completedProgressEmailBody, null);
	}
	
	public static String getAdminEmail() {
		return ADMIN_EMAIL;
	}
}
