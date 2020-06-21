package app.test.voice;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import app.dccan.voice.RtpSystem;
import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.GetList;
import net.help.Convert;
import net.packet.io.PRead;
import net.packet.rctp.Join;
import net.packet.rctp.Live;

public class GetVoice {

	static String gp = "6oSA7wthxFrYq0wgwwR5YW3WQ8bS9JnS";
	public static boolean run = true;
	public static boolean mute = false;
	public static DatagramSocket rctp, voice;
	public static InetAddress inet;
	static int port, rctport = 8890, rtport = 8889;
	static String key;

	public static void main(String[] args) {

		try {
			NoToken rmi = Client.getRmi();
			rmi.login("can", "1");
			//Connect(gp);
			RtpSystem.Connect(gp);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void Connect(String group) {
		try {
			NoToken rmi = Client.getRmi();

			String lp = rmi.getUserkey(group);
			if (lp != null) {
				List<String> ls = GetList.listString(lp);
				run = true;
				mute = false;
				rctp = new DatagramSocket();
				port = rctp.getLocalPort();
				String host = Client.host;
				key = ls.get(0);
				String user = ls.get(1);
				inet = InetAddress.getByName(host);
				Join jn = new Join(group, port, key, user);
				byte[] dta = jn.toPacket();
				DatagramPacket dp = new DatagramPacket(dta, dta.length, inet, rctport);
				rctp.send(dp);
				DatagramSocket cls = rctp;
				dp = new DatagramPacket(new byte[20], 20);
				cls.receive(dp);
				long type = PRead.getLong(dp.getData(), 0, 2);

				if (type == 2001) {
					System.out.println("do type");
					return;
				} else if (type == 2000) {
					byte[] data = new byte[dp.getLength()];
					copyData(data, dp.getData(), dp.getLength());
					data = Convert.encrypt(data, key);
					long id = PRead.getLong(data, 10, 4);
					long gps = PRead.getLong(data, 6, 4);
					System.out.println(cls.getLocalPort());
					Live lv = new Live(id, gps);
					byte[] res = lv.toPacket();
					dp = new DatagramPacket(res, res.length, inet, rctport);
					cls.send(dp);

					new Thread(new Recoder2(id, gps)).start();
					while (run) {
						Thread.sleep(2000);
						cls.send(dp);
						Recoder2.getMemList(group);
					}
				}
			} else {
				System.out.println("no group");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void sendData() {

	}

	private static void copyData(byte[] a, byte[] b, int d) {
		for (int i = 0; i < d; i++) {
			a[i] = b[i];
		}
	}
}
