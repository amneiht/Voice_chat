package app.dccan.voice;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import net.help.Convert;
import net.packet.io.PRead;
import net.packet.rctp.Bye;
import net.packet.rctp.Live;

public class KeepLive implements Runnable {

	@Override
	public void run() {
		DatagramSocket cls = RtpSystem.rctp;
		DatagramPacket dp = new DatagramPacket(new byte[20], 20);
		try {
			cls.receive(dp);

			long type = PRead.getLong(dp.getData(), 0, 2);
			if (type == 2001)
				return;
			else if (type == 2000) {
				byte[] data = new byte[dp.getLength()];
				copyData(data, dp.getData(), dp.getLength());
				data = Convert.encrypt(data, RtpSystem.key);
				long group = PRead.getLong(data, 6, 4);
				long id = PRead.getLong(data, 10, 4);
				Live lv = new Live(id, group);
				byte[] res = lv.toPacket();
				dp = new DatagramPacket(res, res.length, RtpSystem.inet, RtpSystem.rctport);
				new Thread(new Record(id, group)).start();
				new Thread(new Sound(RtpSystem.key)).start();
				while (RtpSystem.run) {
					cls.send(dp);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Bye bt = new Bye(id, group);
				byte[] bby = bt.toPacket();
				dp = new DatagramPacket(bby, bby.length, RtpSystem.inet, RtpSystem.rctport);
				cls.send(dp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("rctp no connect");
			e.printStackTrace();
		}
	}

	private static void copyData(byte[] a, byte[] b, int d) {
		for (int i = 0; i < d; i++) {
			a[i] = b[i];
		}
	}
}
