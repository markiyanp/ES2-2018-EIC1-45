package jUnitTests;

import static org.junit.Assert.*;

import org.apache.commons.mail.EmailException;
import org.junit.Test;

import email.EMail_Tools;

public class JUnitEmail_Tools {
	
	/**
	 * Class specified to test Email_Tools class using JUnit
	 * @author afgos-iscteiulpt
	 */
	
	/**
	 * Check's if AdminEmail is the right one
	 */
	@Test
	public void checkAdminEmail() {
		assertEquals(EMail_Tools.getAdminEmail(),"AGrupo45@gmail.com");
	}
	
	/**
	 * Check's for an email when sending an email with invalid data 
	 * @throws EmailException 
	 */
	@Test(expected = EmailException.class)
	public void sendEmail() throws EmailException {
		EMail_Tools.sendMail("aaa@gmail.com", "Potato", "bbb@gmail.com", 
				null, "TesteUnitário 1", "isto é um mail do teste unitario 1, não devias estar a receber isto", "");
	}

}
