package app.dccan.voice;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import dccan.remote.Client;
import dccan.remote.Remote;
import dccan.suport.GetList;
import net.packet.rctp.Join;

public class RtpSystem {
	public static boolean run = true;
	public static boolean mute = false;
	public static DatagramSocket rctp, voice;
	public static InetAddress inet;
	static int port, rctport = 8890, rtport = 8889;
	static String key;

	public static void end() {
		run = false;
		mute = true;
	}

	public static void mute() {
		mute = true;
	}

	public static void talk() {
		mute = false;

	}

	public static void Connect(String group) {
		try {
			Remote rmi = Client.getRmi();

			String lp = rmi.getUserkey(group);
			if (lp != null) {
				List<String> ls = GetList.listString(lp);
				run = true;
				mute = false;
				rctp = new DatagramSocket();
				voice = new DatagramSocket();
				port = voice.getLocalPort();
				String host = Client.host;

				key = ls.get(0);
				String user = ls.get(1);
				inet = InetAddress.getByName(host);
				Join jn = new Join(group, port, key, user);
				byte[] dta = jn.toPacket();
				DatagramPacket dp = new DatagramPacket(dta, dta.length, inet, rctport);
				rctp.send(dp);
				new Thread(new KeepLive()).start();
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
