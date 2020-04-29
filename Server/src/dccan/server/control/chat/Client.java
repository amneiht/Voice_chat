package dccan.server.control.chat;

import java.net.InetAddress;

public class Client {
	InetAddress ina; // dia chi innet
	int port; // cong
	long id;
	long live;
	String user ;
	public Client(int p, InetAddress ina, long pid , String user) {
		port = p;
		this.ina = ina;
		id = pid;
		live = System.currentTimeMillis();
		this.user = user ;
	}
	public String getUser()
	{
		return user ;
	}
}
