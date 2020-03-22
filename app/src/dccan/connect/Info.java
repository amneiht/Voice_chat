package dccan.connect;

import java.net.Socket;

public class Info {
	private static final int tcp = 8888;
	protected static final int udp = 8889;
	
	public static final String key= "can 1234";
	public static Socket TCPconnect(String a) {
		try {
			Socket s = new Socket(a, tcp);
			return s;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
