package dccan.server.control.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dccan.server.suport.rtp.ReadPacket;

public class RctpServer {
	int port = 8889;
	RoomMap rm;
	int maxlg = 10 * 1024;// 10 kb
	DatagramSocket ds;
	DatagramSocket dg;
	ExecutorService exe = Executors.newFixedThreadPool(20);

	public RctpServer(RoomMap mp) throws SocketException {
		ds = new DatagramSocket(port);
		dg = new DatagramSocket();
		rm = mp;
	}

	public void run() {
		byte[] buf = new byte[maxlg];
		while (true) {
			try {
				DatagramPacket dp = new DatagramPacket(buf, maxlg);
				ds.receive(dp);
				exe.execute(new Handle(dp));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class Handle implements Runnable {
		int port;
		InetAddress inet;
		byte[] res;

		public Handle(DatagramPacket p) {
			port = p.getPort();
			res = p.getData();
			inet = p.getAddress();
		}

		@Override
		public void run() {
			int type = ReadPacket.getType(res);
			switch (type) {
			case 1000: // goi tin create of join
				int length = (int) ReadPacket.getLong(res, 2, 2);
				int pos = (int) ReadPacket.getLong(res, 12, 4);
				int z = length - 16;
				String group = ReadPacket.getString(res, 16, z);
				long id = (long) Math.random() * 0xffffffff;
				Client s = new Client(pos, inet, id);
				long gid = rm.getRoomId(group);
				rm.addClient(gid, s);
				sendid(id);
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

		private void sendid(long id) {
			// TODO Auto-generated method stub
			int h = 10;
		}
	}
}
