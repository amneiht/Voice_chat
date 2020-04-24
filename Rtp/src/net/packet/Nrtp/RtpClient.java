package net.packet.Nrtp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import net.help.Convert;
import net.packet.io.PWrite;

public class RtpClient {
	byte[] header = new byte[8];
	static int port = 9981;
	DatagramSocket client;
	InetAddress inet;
	int sq = 1;
	String key ;
	// sq :0 ; ntp : 2 ; group 6 ,id :10 , data :14
	public int getClientPort() {
		return client.getLocalPort();
	}

	public RtpClient(String host, long group, long id , String key) {
		try {
			inet = InetAddress.getByName(host);
			client = new DatagramSocket();
			this.key = key ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(byte[] dt) {

		try {
			byte [] data = Convert.encrypt(dt, key);
			int p = data.length;
			byte[] send = new byte[p + 14];// do dai header
			PWrite._16bitToArray(send, sq, 0);
			PWrite._32bitToArray(data, System.currentTimeMillis(), 2);
			PWrite.copyArray(header, send, 6);
			PWrite.copyArray(data, send, 14);
			DatagramPacket dp = new DatagramPacket(send, p + 14, inet, port);
			client.send(dp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sq++;
	}

	public void createHeader(long group, long id) {
		PWrite._32bitToArray(header, group, 0);
		PWrite._32bitToArray(header, id, 4);
	}
}
