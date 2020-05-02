package dccan.server.sql.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Info {
	static DbPool dp = new DbPool();
	public static final String HOST_NAME = "localhost";
	public static final String DB_NAME = "chat";
	public static final String DB_PORT = "3306";
	public static final String USER_NAME = "root";
	public static final String PASSWORD = "";
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final int DB_MIN_CONNECTIONS = 5;
	public static final int DB_MAX_CONNECTIONS = 10;
	public static final String CONNECTION_URL = "jdbc:mysql://" + HOST_NAME + ":" + DB_PORT + "/" + DB_NAME
			+ "?useSSL=false&characterEncoding=UTF-8";
	static {
		System.out.println("Khoi tao database pool ");
		System.out.println();
		dp.setMaxConnection(DB_MAX_CONNECTIONS);
		dp.setMinConnection(DB_MIN_CONNECTIONS);
		dp.setUser(USER_NAME);
		dp.setPass(PASSWORD);
		dp.setUrl(CONNECTION_URL);
		dp.init();
	}

	// dung trong thuc te
	public static Connection getCon(){
		return dp.getConnection();
	}
	public static void give(Connection con)
	{
		dp.give(con);
	}
	// test hoat dong
	public static Connection getCon1() {
		try {

			String url = "jdbc:mysql://" + HOST_NAME + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false";
			Connection con = DriverManager.getConnection(url, USER_NAME, PASSWORD);
			return con;
		} catch (Exception e) {
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
