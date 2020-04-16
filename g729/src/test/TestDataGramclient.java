package test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestDataGramclient {
	public static void main(String[] args) {

		try {
			DatagramPacket dp;
			DatagramSocket client = new DatagramSocket();
			InetAddress inet = InetAddress.getByName("127.0.0.1");
			int port = 9009;
			byte[] dk = new byte[10];
			while (true) {

				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 10; j++) {
						dk[j] = (byte) i;
					}
					dp = new DatagramPacket(dk, dk.length, inet, port);
					client.send(dp);
				}
				Thread.sleep(100);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int port = 9981;
	}
}
