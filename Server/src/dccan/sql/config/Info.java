package dccan.sql.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Info {
	protected static String user = "root";
	protected static String pass = "";
	protected static int port = 3306;
	protected static String db = "chat";
	protected static String host = "localhost";
	public static Connection getCon() {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + host + ":" + port + "/" + db+"?useSSL=false";	
			Connection con = DriverManager.getConnection(url, user, pass);
			return con;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			return convertByteToHex(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String convertByteToHex(byte[] data) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
