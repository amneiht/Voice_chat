package dccan.server.control.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import dccan.server.suport.rtp.ReadPacket;
import net.packet.io.PRead;
import net.packet.io.PWrite;

public class RctpServer {
	int port = 8889;
	RoomMap rm;
	int maxlg = 10 * 1024;// 10 kb
	DatagramSocket ds;
	DatagramSocket dg;
	List<Flagment> ls = new LinkedList<Flagment>();
	private boolean run = true;
	DatagramSocket clients = new DatagramSocket();

	public RctpServer(RoomMap mp) throws SocketException {
		ds = new DatagramSocket(port);
		dg = new DatagramSocket();
		rm = mp;
	}

	public void run() {
		Thread t = new Thread(new Handle());
		t.start();
		byte[] buf = new byte[maxlg];
		while (run) {
			try {
				DatagramPacket dp = new DatagramPacket(buf, maxlg);
				ds.receive(dp);
				ls.add(new Flagment(dp));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class Handle implements Runnable {

		@Override
		public void run() {
			while (run) {
				try {
					if (!ls.isEmpty()) {
						Flagment fg = ls.remove(0);
						byte[] res = fg.data;

						int type = ReadPacket.getType(res);
						switch (type) {
						case 1000: // goi tin create of join
							int length = (int) ReadPacket.getLong(res, 2, 2);
							int pos = (int) ReadPacket.getLong(res, 12, 4);
							int z = length - 16;
							String group = ReadPacket.getString(res, 16, z);
							String user = PRead.getString(res, length, 8);
							long gid = rm.getRoomId(group);
							if (gid == -1)
								break;
							if (!rm.checkUserKey(gid, user))
								break; // kiem tra key false la cho bay luon
							long id = (long) Math.random() * 0xffffffff;
							InetAddress inet = fg.inet;
							Client s = new Client(pos, inet, id);

							rm.addClient(gid, s);
							sendid(id, fg);
							break;
						case 1001:
							long gid1 = ReadPacket.getLong(res, 12, 4);
							long live = ReadPacket.getLong(res, 16, 4);
							rm.live(gid1, live);
							break;
						case 1111:
							long byegroup = ReadPacket.getLong(res, 12, 4);
							long byeid = ReadPacket.getLong(res, 16, 4);
							rm.bye(byegroup, byeid);
							break;

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void sendid(long id, Flagment fg) {
			byte res[] = new byte[10];
			PWrite._16bitToArray(res, 2000, 0);
			PWrite._32bitToArray(res, System.currentTimeMillis(), 2);
			PWrite._32bitToArray(res, id, 6);
			DatagramPacket dp = new DatagramPacket(res, res.length, fg.inet, fg.port);
			try {
				clients.send(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
