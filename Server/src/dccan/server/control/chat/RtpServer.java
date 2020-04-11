package dccan.server.control.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RtpServer {

	int port = 8889;
	RoomMap rm;
	int maxlg = 10 * 1024;// 10 kb
	DatagramSocket ds;

	ExecutorService exe = Executors.newFixedThreadPool(20);

	public RtpServer(RoomMap r) throws SocketException {
		ds = new DatagramSocket(port);
		rm = r;

	}

	public void run() {
		byte[] buf = new byte[maxlg];
		while (true) {
			try {
				DatagramPacket dp = new DatagramPacket(buf, maxlg);
				ds.receive(dp);
				exe.execute(new Handle(dp));
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
			// lay thong tin truyen cho client
		}
	}
}
