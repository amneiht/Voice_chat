package app.dccan.voice;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.GetList;
import net.packet.rctp.Join;

public class RtpSystem {
	static Object lock = new Object();
	private static boolean run = true;
	private static boolean mute = false;

	public static boolean isMute() {
		return mute;
	}

	public static boolean isRun() {
		synchronized (lock) {
			return run;
		}
	}

	public static DatagramSocket rctp = null;
	public static InetAddress inet;
	static int port, rctport = 8890, rtport = 8889;
	static String key;

	public static void end() {
		synchronized (lock) {
			run = false;
			mute = true;
		}

	}

	public static void mute() {
		mute = true;
	}

	public static void talk() {
		mute = false;

	}

	public static void Connect(String group) {
		try {
			NoToken rmi = Client.getRmi();

			String lp = rmi.getUserkey(group);
			if (lp != null) {
				List<String> ls = GetList.listString(lp);
				run = true;
				mute = false;
				rctp = new DatagramSocket((int) (2000 * (Math.random() + 1))); // noi voi 1 cong
				rctp.setReuseAddress(true); // cho phep tai su dung cong
				port = rctp.getLocalPort();
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
