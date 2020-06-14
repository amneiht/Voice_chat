package test.mail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Test {
	public static void main(String[] args) {
		String email = "dccntd3@gmailsm";
		InternetAddress internetAddress;
		try {
			internetAddress = new InternetAddress(email);
			internetAddress.validate();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
