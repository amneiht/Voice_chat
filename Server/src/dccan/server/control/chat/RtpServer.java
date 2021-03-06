package dccan.server.control.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import net.packet.io.PRead;

public class RtpServer implements Runnable {

	int port = 8889;
	RoomMap rm;
	int maxlg = 10 * 1024;// 10 kb
	DatagramSocket ds;
	List<Flagment> ls = new LinkedList<Flagment>();
	private boolean run = true;
	DatagramSocket sc = new DatagramSocket();

	public RtpServer() throws SocketException {
		ds = new DatagramSocket(port);
		rm = StaticMap.getRm();
		System.out.println("create rtp server on port:8889\n");
	}

	public void run() {
		new Thread(new Handle()).start();
		byte[] buf = new byte[maxlg];
		DatagramPacket dp = new DatagramPacket(buf, maxlg);
		while (true) {
			try {
				ds.receive(dp);
				synchronized (ls) {
					ls.add(new Flagment(dp));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void send(Flagment flg, List<Client> mem) {

		try {
			byte[] send = flg.data;
			long tid = PRead.getLong(send, 10, 4);
			for (Client c : mem) {

				if (c.id != tid) {
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
				Flagment fg = null;
				synchronized (ls) {
					if (!ls.isEmpty())
						fg = ls.remove(0);
				}
				if (fg != null) {
					long tm = System.currentTimeMillis();
					byte data[] = fg.data;
					long gp = PRead.getLong(data, 6, 4);

					Room m = rm.lp.get(gp);
					if (m != null) {
						send(fg, m.getMem());
					}
					System.out.println(System.currentTimeMillis() - tm);
				}
			}
		}
	}

}
