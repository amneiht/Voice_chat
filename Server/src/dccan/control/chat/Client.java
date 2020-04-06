package dccan.control.chat;

import java.net.InetAddress;

public class Client {
	InetAddress ina; // dia chi innet
	int port; // cong
	long id;
	long live;

	public Client(int p, InetAddress ina, long pid) {
		port = p;
		this.ina = ina;
		id = pid;
		live = System.currentTimeMillis();
	}
}
