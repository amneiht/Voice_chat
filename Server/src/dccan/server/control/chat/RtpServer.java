package dccan.server.control.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import net.packet.io.PRead;

public class RtpServer {

	int port = 8889;
	RoomMap rm;
	int maxlg = 10 * 1024;// 10 kb
	DatagramSocket ds;
	List<Flagment> ls = new LinkedList<Flagment>();
	private boolean run = true;
	DatagramSocket sc = new DatagramSocket();

	public RtpServer(RoomMap r) throws SocketException {
		ds = new DatagramSocket(port);
		rm = r;

	}

	public void run() {
		Thread t = new Thread(new Handle());
		t.start();
		byte[] buf = new byte[maxlg];
		while (true) {
			try {
				DatagramPacket dp = new DatagramPacket(buf, maxlg);
				ds.receive(dp);
				ls.add(new Flagment(dp));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void send(Flagment flg, List<Client> mem) {

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

	private class Handle implements Runnable {

		public void run() {
			while (run) {
				if (!ls.isEmpty()) {
					Flagment fg = ls.remove(0);
					byte data[] = fg.data;
					long gp = PRead.getLong(data, 6, 4);
					Room m = rm.lp.get(gp);
					if (m != null) {
						send(fg, m.mem);
					}
				}
			}
		}
	}

}
