package dccan.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator {

	PasswordAuthentication pc;

	public Gmail(String user, String pass) {
		pc = new PasswordAuthentication(user, pass);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return pc;
	}
}
