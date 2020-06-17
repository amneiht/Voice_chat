package dccan.server.control.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;

import net.help.Convert;
import net.packet.io.PRead;
import net.packet.io.PWrite;

public class RctpServer implements Runnable {
	int port = 8890;
	RoomMap rm;
	int maxlg = 10 * 1024;// 10 kb
	private DatagramSocket ds;
	private LinkedList<Flagment> ls = new LinkedList<Flagment>();
	private boolean run = true;
	DatagramSocket clients = new DatagramSocket();

	public RctpServer() throws SocketException {
		ds = new DatagramSocket(port);
		rm = StaticMap.getRm();
		System.out.println("create Rctp server on port 8890\n");
	}

	public static void main(String[] args) {
		try {
			new Thread(new RctpServer()).start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {

		System.out.println("Rctp server run");
		new Thread(new Handle()).start();

		byte[] buf = new byte[maxlg];
		DatagramPacket dp = new DatagramPacket(buf, maxlg);
		while (run) {
			try {
				ds.receive(dp);
				Flagment fg = new Flagment(dp);
				synchronized (ls) {
					ls.add(fg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class Handle implements Runnable {
		public void run() {
			// System.out.println("Rctp handle server run : ");
			while (run) {
				try {
					Flagment fg = null;
					synchronized (ls) {
						if (!ls.isEmpty())
							fg = ls.removeFirst();
					}
					if (fg != null) {
						byte[] res = fg.data;
						int type = (int) PRead.getLong(res, 0, 2);
						switch (type) {
						case 1000: // goi tin create of join
							int length = (int) PRead.getLong(res, 2, 2);
							//int pos = (int) PRead.getLong(res, 12, 2);
							int z = length - 16;
							String group = PRead.getString(res, 16, z);
							long gid = rm.getRoomId(group);
							System.out.println("ma group " + gid);
							if (gid == -1) {
								sendid(fg);
								break;
							}
							byte[] en = Convert.encrypt(PRead.getByte(res, length, 8), rm.getGroupKey(group));

							String ck = rm.checkUserKey(gid, en);
							System.out.println("user ket noi" + ck);
							System.out.println();
							if (ck == null) {
								sendid(fg);
								break; // kiem tra key false la cho bay luon
							}
							long id = (long) (Math.random() * 0xffffffffL);
							// System.out.println("id "+id);
							InetAddress inet = fg.inet;
							Client s = new Client(fg.port, inet, id, ck);
							rm.addClient(gid, s);
							sendid(id, gid, fg, rm.getGroupKey(group));
							break;
						case 1001:
							long gid1 = PRead.getLong(res, 12, 4);
							long live = PRead.getLong(res, 16, 4);
							rm.live(gid1, live ,fg);
							break;
						case 1111:
							long byegroup = PRead.getLong(res, 12, 4);
							long byeid = PRead.getLong(res, 16, 4);
							rm.bye(byegroup, byeid);
							break;

						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void sendid(Flagment fg) {
			byte res[] = new byte[14];
			PWrite._16bitToArray(res, 2001, 0);
			DatagramPacket dp = new DatagramPacket(res, res.length, fg.inet, fg.port);
			try {
				clients.send(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void sendid(long id, long gp, Flagment fg, String key) {
			byte res[] = new byte[14];
			PWrite._32bitToArray(res, System.currentTimeMillis(), 2);
			PWrite._32bitToArray(res, gp, 6);
			PWrite._32bitToArray(res, id, 10);
			byte[] dta = Convert.encrypt(res, key);
			PWrite._16bitToArray(dta, 2000, 0);
			DatagramPacket dp = new DatagramPacket(dta, dta.length, fg.inet, fg.port);
			try {
				clients.send(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
