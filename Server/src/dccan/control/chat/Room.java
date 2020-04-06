package dccan.control.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Room implements Runnable {
	public static long timeout = 5 * 1000;// 5 s

	public Room(String d) throws SocketException {
		sc = new DatagramSocket();
		group = d;
	}

	DatagramSocket sc;
	protected String group;
//	long rtp; // ma tham so rtp
	List<DatagramPacket> list = new LinkedList<DatagramPacket>(); // khoi tao de dang , phu hop thay doi
	ArrayList<Client> mem = new ArrayList<Client>();// tru nhap de dang
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

	public void run() {
		boolean run = false;
		while (!end) {
			run = !list.isEmpty();
			if (run) {
				DatagramPacket dp;
				synchronized (list) {
					dp = list.remove(0);
				}
				send(dp);// hoac tao rieng 1 luong de gui
				run = false;
			}

		}

	}

	private void send(DatagramPacket dp) {

		try {
			byte[] send = dp.getData();
			InetAddress ip = dp.getAddress();
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
