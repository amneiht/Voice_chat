package test.mail;

import javax.mail.*;
import javax.mail.internet.*;

public class Snippet {

	public static void main(String[] args) {
		// Set up the SMTP server.
		java.util.Properties props = new java.util.Properties();
		props.put("mail.smtp.host", "smtp.myisp.com");
		Session session = Session.getDefaultInstance(props, null);

		// Construct the message
		String from = "dccntd2@gmail.com";
		String to = "dccntd@gmail.com";
		String subject = "Hello";
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject);
			msg.setText("Hi,\n\nHow are you?");

			// Send the message.
			System.out.println("s0s");
			Transport.send(msg);
			System.out.println("ss");
		} catch (MessagingException e) {
			// Error.
			System.out.println("s2s");
		}
	}
}
