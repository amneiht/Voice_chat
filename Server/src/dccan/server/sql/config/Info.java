package dccan.server.sql.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class Info {

	// com.mysql.jdbc.MysqlDataSource es ;
	private static BasicDataSource ds = new BasicDataSource();
	public static final String HOST_NAME = "localhost";
	public static final String DB_NAME = "chat";
	public static final String DB_PORT = "3306";
	public static final String USER_NAME = "root";
	public static final String PASSWORD = "";
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final int DB_MIN_CONNECTIONS = 5;
	public static final int DB_MAX_CONNECTIONS = 10;
	// jdbc:mysql://hostname:port/dbname
	public static final String CONNECTION_URL = "jdbc:mysql://" + HOST_NAME + ":" + DB_PORT + "/" + DB_NAME
			+ "?useSSL=false";
	static {
		ds.setDriverClassName(DB_DRIVER);
		ds.setUrl(CONNECTION_URL);
		ds.setUsername(USER_NAME);
		ds.setPassword(PASSWORD);
		ds.setMinIdle(DB_MIN_CONNECTIONS); // minimum number of idle connections in the pool
		ds.setInitialSize(DB_MIN_CONNECTIONS);
		ds.setMaxIdle(DB_MAX_CONNECTIONS); // maximum number of idle connections in the pool
		ds.setMaxOpenPreparedStatements(100);
	}

	// dung trong thuc te
	public static Connection getCon1() throws SQLException {
		return ds.getConnection();
	}

	// test hoat dong
	public static Connection getCon() {
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + HOST_NAME + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false";
			Connection con = DriverManager.getConnection(url, USER_NAME, PASSWORD);
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
