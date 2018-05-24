package jMetal;

import javax.swing.JOptionPane;

import org.apache.commons.mail.EmailException;

import email.EMail_Tools;
import visual.LeftPanel;

public class ProgressChecker {

	private static final String CONFIRM_TITLE_FINISH = "Optimization finished";
	private static final String CONFIRM_FINISH = "Optimization complete! Check out the Graphics tab for your results.";
	private boolean email25 = false;
	private boolean email50 = false;
	private boolean email75 = false;

	private boolean useJar;

	public ProgressChecker(boolean useJar) {
		this.useJar = useJar;
	}

	/**
	 * checks test progress, if progress is 25,50,75 or 100% an email is sent to
	 * user
	 * 
	 * @param testNumber
	 */
	public void checkProgress(int testNumber) {
		double numberTests;
		if (useJar)
			numberTests = 500;
		else
			numberTests = 2500;
		double progress = ((double) testNumber) / numberTests;
		int act_prog = (int) (progress * 100);
		LeftPanel.setProgress(act_prog);
		if (progress >= 0.25 && !email25) {
			new EmailSender(25).start();
			email25 = true;
		}
		if (progress >= 0.5 && !email50) {
			new EmailSender(50).start();
			email50 = true;
		}
		if (progress >= 0.75 && !email75) {
			new EmailSender(75).start();
			email75 = true;
		}
		if (progress == 1) {
			new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null, CONFIRM_FINISH, CONFIRM_TITLE_FINISH, 1);
				}
			}.start();
			new EmailSender(100).start();
		}

	}

	private class EmailSender extends Thread {
		private final String WARNING_FAILED_MAIL = "Failed to send progress e-mail!";
		private final String WARNING_TITLE_FAILED_MAIL = "E-Mail failure";
		
		public int progress = 0;

		public EmailSender(int prog) {
			this.progress = prog;
		}

		public void run() {
			try {
				EMail_Tools.sendProgressMail(progress);
			} catch (EmailException e) {
				JOptionPane.showMessageDialog(null, WARNING_FAILED_MAIL, WARNING_TITLE_FAILED_MAIL, 0);
				e.printStackTrace();
			}
		}
	}
}
