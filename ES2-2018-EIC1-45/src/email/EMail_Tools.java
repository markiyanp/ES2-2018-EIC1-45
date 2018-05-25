package email;

import java.io.File;

import javax.swing.JOptionPane;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 * Harbors several functions that are useful for the e-mail functionality of
 * this program.
 * 
 * @author pvmpa-iscteiulpt
 *
 */
public class EMail_Tools {

	public static final String OPTIMIZATION_BOT_ADDRESS = "group45.optimization.bot@gmail.com";
	public static final String OPTIMIZATION_BOT_PASSWORD = "";
	
	private static final String WARNING_TITLE_INVALID_EMAIL = "Address failure";
	private static final String WARNING_TITLE_AUTH_FAILED = "Auth failure";

	private static final String WARNING_INVALID_EMAIL = "WARNING: Invalid e-mail address detected! Failed to parse provider!"
			+ "\nTry again with an e-mail from the following providers:\nGMail\nmail.com\nyandex.com";
	private static final String WARNING_AUTH_FAILED = "Authentication has failed during '";
	
	private static String currentUserEmail;

	// this class should never be instatiated!
	private EMail_Tools() {

	}

	/**
	 * Finds out the correct SMTP server and port depending on the e-mail address
	 * specified.
	 * </p>
	 * Supports GMail and mail.com only.
	 * 
	 * @param email
	 * @return array containing the smtp address, the port and whether SSL is used
	 *         or not
	 */
	private static Object[] mailProviderToSMTP(String email) {
		int i;
		for (i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '@') {
				email = email.substring(i + 1);
			}
		}
		System.out.println(email);
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
		case "yandex.com":
			smtp_and_port[0] = "smtp.yandex.com";
			smtp_and_port[1] = "465";
			smtp_and_port[2] = Boolean.TRUE;
			return smtp_and_port;
		}
		// something messed up..
		new Thread() {
			public void run() {
				JOptionPane.showMessageDialog(null, WARNING_INVALID_EMAIL, WARNING_TITLE_INVALID_EMAIL, 0);
			}
		}.start();
		throw new IllegalArgumentException(WARNING_INVALID_EMAIL);
	}

	/**
	 * Checks whether the e-mail/password combination is valid by sending an email
	 * to the same address.
	 * </p>
	 * A reason must be provided to do this.
	 * 
	 * @param userAddr
	 * @param userPw
	 * @param reason
	 * @return successful/unsuccessful auth
	 */
	public static boolean checkAuth(String userAddr, String userPw, String reason) {

		if (reason.isEmpty()) {
			throw new IllegalArgumentException("You must provide a reason to check authentication.");
		}

		try {
			System.out.println("Attempting to send warning e-mail");
			sendMail(userAddr, userPw, userAddr, null, "[OPTIMIZATION PROGRAM] User activity detected", reason, null);
			return true;
		} catch (EmailException e) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null,
							WARNING_AUTH_FAILED + reason + "'" + "\nCheck your anti-virus program's SMTP features.",
							WARNING_TITLE_AUTH_FAILED, 0);
				}
			}.start();
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Sends an e-mail.
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
	public static void sendMail(String userAddr, String userPw, String destinationAddr, String[] cc, String subject,
			String body, String attachmentPath) throws EmailException {

		// Get the appropriate SMTP server. If this fails, the entire sequence is
		// aborted.
		Object[] smtp_and_port = mailProviderToSMTP(userAddr);

		boolean validAttachment = false;
		boolean validCc = false;

		if (attachmentPath != null) {
			File file = new File(attachmentPath);
			if ((!file.isDirectory() && file.exists())) {
				validAttachment = true;
				System.out.println("Attachment detected!");
			}
		}

		if (cc != null) {
			// chained if required or else we might get a null pointer exception
			if (cc.length > 0) {
				validCc = true;
			}
		}

		if (validAttachment) {

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

			email.setSSLOnConnect((Boolean) smtp_and_port[2]);

			email.setFrom(userAddr);
			email.addTo(destinationAddr);
			email.setSubject(subject);
			email.setMsg(body);

			email.attach(attachment);

			if (validCc) {
				for (int i = 0; i < cc.length; i++) {
					email.addCc(cc[i]);
				}
			}

			System.out.println("Sending e-mail (with attachment)");
			email.send();

		} else {
			Email email = new SimpleEmail();

			email.setHostName((String) smtp_and_port[0]);
			email.setSmtpPort(Integer.parseInt((String) smtp_and_port[1]));
			email.setSslSmtpPort((String) smtp_and_port[1]);

			email.setAuthenticator(new DefaultAuthenticator(userAddr, userPw));
			email.setSSLOnConnect((Boolean) smtp_and_port[2]);

			email.setFrom(userAddr);
			email.addTo(destinationAddr);
			email.setSubject(subject);
			email.setMsg(body);

			if (validCc) {
				for (int i = 0; i < cc.length; i++) {
					email.addCc(cc[i]);
				}
			}

			System.out.println("Sending e-mail (no attachment)");
			email.send();
		}
	}

	/**
	 * Periodically used to send an e-mail containing information about the current
	 * optimization process to the current user.
	 * 
	 * @param progress
	 * @throws EmailException
	 */
	public static void sendProgressMail(int progress) throws EmailException {
		if (progress != 100)
			sendMail(OPTIMIZATION_BOT_ADDRESS, OPTIMIZATION_BOT_PASSWORD, currentUserEmail, null,
					"Your Optimization is currently at " + progress, "Please wait...", null);
		else
			sendMail(OPTIMIZATION_BOT_ADDRESS, OPTIMIZATION_BOT_PASSWORD, currentUserEmail, null,
					"Your Optimization is completed!", "Check out the results as soon as possible.", null);
	}

	/**
	 * Gets the logged user's e-mail.
	 * @return E-Mail
	 */
	public static String getCurrentUserEmail() {
		return currentUserEmail;
	}

	/**
	 * Sets the logged user's e-mail.
	 * @param currentUserEmail
	 */
	public static void setCurrentUserEmail(String currentUserEmail) {
		EMail_Tools.currentUserEmail = currentUserEmail;
	}

}