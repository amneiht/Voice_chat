package dccan.server.control.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

public class Room  {
	public static long timeout = 5 * 1000;// 5 s
	private String key;
	public List<String> user = new LinkedList<String>();

	public Room(String d) throws SocketException {
		sc = new DatagramSocket();
		group = d;
		key = RandomStringUtils.random(24);
	}

	public String createUserKey() {
		String res = RandomStringUtils.random(8);
		user.add(res);
		return res;
	}

	public boolean checkUserKey(byte[] in) {
		String test = new String(in);
		synchronized (user) {
			int lg = user.size();
			for (int i = 0; i < lg; i++) {
				if (user.get(i).equals(test)) {
					user.remove(i);
					return true;
				}
			}
		}
		return false ;
	}
	public boolean checkUserKey(String test) {
		synchronized (user) {
			int lg = user.size();
			for (int i = 0; i < lg; i++) {
				if (user.get(i).equals(test)) {
					user.remove(i);
					return true;
				}
			}
		}
		return false ;
	}
	public String getKey() {
		return key;
	}

	DatagramSocket sc;
	protected String group;
	// long rtp; // ma tham so rtp
	List<DatagramPacket> list = new LinkedList<DatagramPacket>(); // khoi tao de dang , phu hop thay doi
	protected List<Client> mem = new LinkedList<Client>();// tru nhap de dang
	boolean end = false;

	public void live(long p) {
		for (Client c : mem) {
			if (c.id == p) {
				c.live = System.currentTimeMillis();
				return;
			}
		}
	}

	public void bye(long id) {
		for (int i = 0; i < mem.size(); i++) {
			if (mem.get(i).id == id) {
				mem.remove(i);
				return;
			}
		}
	}

	public void clear() {
		long now = System.currentTimeMillis();
		for (int i = mem.size() - 1; i > 0; i--) {
			if (now - mem.get(i).live > timeout) {
				mem.remove(i);
			}
		}
		if (mem.size() == 0)
			end = true;
	}

	public void addClient(Client s) {
		mem.add(s);
	}

	public void addPacket(DatagramPacket dp) {
		list.add(dp);
	}
	public void send(Flagment flg) {

		try {
			byte[] send = flg.data;
			InetAddress ip = flg.inet;
			for (Client c : mem) {
				if (!c.ina.equals(ip)) {
					DatagramPacket sd = new DatagramPacket(send, send.length, c.ina, c.port);
					sc.send(sd);
				} else {
					c.live = System.currentTimeMillis();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
