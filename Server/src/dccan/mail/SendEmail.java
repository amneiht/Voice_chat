package dccan.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	static String user = "dccntd@gmail.com";
	static String pass = "Dccan1591997";
	static Session session;

	static {
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		session = Session.getInstance(properties, new Gmail(user, pass));
		session.setDebug(false);
	}
	public static void sendPassWordToken(String email, String id) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject("Demo Đồ án : khôi phục mật khâu");
			message.setText("Mã token khôi phục của đồng chí là : " + id);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void sendEmailToken(String email, String id) {
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject("Demo Đồ án : Thay Đổi email");
			message.setText("Mã token thay đổi của đồng chí là : " + id);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
