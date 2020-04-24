package dccan.server.control.chat;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class Flagment {
	protected byte data[];
	int port;
	InetAddress inet;

	public Flagment(DatagramPacket p) {
		port = p.getPort();
		inet = p.getAddress();
		int d = p.getLength();
		data = new byte[d];
		copyData(data, p.getData(), d);
	}

	private static void copyData(byte[] a, byte[] b, int d) {
		for (int i = 0; i < d; i++) {
			a[i] = b[i];
		}
	}
}
