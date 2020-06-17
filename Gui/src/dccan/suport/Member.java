package dccan.suport;

import java.net.InetAddress;

public class Member {
	InetAddress ina; // dia chi innet
	int port; // cong
	long id;
	long live;
	String user;

	public Member(int p, InetAddress ina, long pid, String user) {
		port = p;
		this.ina = ina;
		id = pid;
		live = System.currentTimeMillis();
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public InetAddress getIna() {
		return ina;
	}

	public int getPort() {
		return port;
	}

	public long getId() {
		return id;
	}

	public long getLive() {
		return live;
	}

}
