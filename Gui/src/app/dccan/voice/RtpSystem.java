package app.dccan.voice;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import net.packet.rctp.Join;

public class RtpSystem {
	public static boolean run = true;
	public static boolean mute = false;
	public static DatagramSocket rctp, voice;
	public static InetAddress inet;
	static int port, rctport = 8890, rtport = 8889;
	static String key;

	public void end() {
		run = false;
	}

	public static void mute() {
		mute = true;
	}

	public static void talk() {
		mute = false;

	}

	public static void Connect(String host, String group, String ke, String user) {
		try {
			run = true;
			rctp = new DatagramSocket();
			voice = new DatagramSocket();
			port = voice.getLocalPort();
			key = ke;
			inet = InetAddress.getByName(host);
			Join jn = new Join(group, port, key, user);
			byte[] dta = jn.toPacket();
			DatagramPacket dp = new DatagramPacket(dta, dta.length, inet, rctport);
			rctp.send(dp);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
