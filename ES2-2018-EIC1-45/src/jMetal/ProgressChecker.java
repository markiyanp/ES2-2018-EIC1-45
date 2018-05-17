package jMetal;

import org.apache.commons.mail.EmailException;

import email.EMail_Tools;
import visual.LeftPanel;

public class ProgressChecker {
	
	private boolean email25 = false;
	private boolean email50 = false;
	private boolean email75 = false;
	
	private boolean useJar;
	
	public ProgressChecker(boolean useJar){
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
		double progress =  ((double)testNumber) / numberTests;
		try {
			int act_prog = (int) (progress*100);
			LeftPanel.setProgress(act_prog);
			if (progress >= 0.25 && !email25) {
				EMail_Tools.sendProgressMail(25);
				email25 = true;
			}
			if (progress >= 0.5 && !email50) {
				EMail_Tools.sendProgressMail(50);
				email50 = true;
			}
			if (progress >= 0.75 && !email75) {
				EMail_Tools.sendProgressMail(75);
				email75 = true;
			}
			if (progress == 1) {
				EMail_Tools.sendProgressMail(100);
			}
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
