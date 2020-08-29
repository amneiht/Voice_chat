package app.dccan.voice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;

import org.mobicents.media.server.impl.dsp.audio.g729.Decoder;

import amneiht.media.buffer.Pack;
import amneiht.media.buffer.StackList;
import net.help.Convert;
import net.packet.io.PRead;

public class Sound implements Runnable {

	private byte[] key;

	LinkedList<byte[]> lp = new LinkedList<byte[]>();

	public Sound(String key) {
		this.key = key.getBytes();
	}

	class Loa implements Runnable {
		@Override
		public void run() {

			try {
				Decoder dec = new Decoder();
				StackList sl = new StackList();
				Thread lol =new Thread(sl);
				lol.start();

				while (RtpSystem.isRun()) {
					byte data[] = null;
					synchronized (lp) {
						if (!lp.isEmpty())
							data = lp.removeFirst();
					}
					if (data != null) {
						long id = PRead.getLong(data, 10, 4);
						long time = PRead.getLong(data, 2, 4);
						byte[] sound = PRead.getByte(data, 14, 10);
						sound = Convert.encrypt(sound, key);
						sound = dec.process(sound);
						sl.add(id, new Pack(time, sound));
					} else {
						Thread.sleep(9);
					}
				}
				sl.close();
				System.out.println("end sound");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void run() {
		DatagramSocket ds = RtpSystem.rctp;
		// new Thread(new Loa()).start();
		try {
			DatagramPacket dp = new DatagramPacket(new byte[30], 30);
			ds.setSoTimeout(1000);

			Decoder dec = new Decoder();
			StackList sl = new StackList();
			new Thread(sl).start();

			while (RtpSystem.isRun()) {
				byte[] data;
				try {
					ds.receive(dp);
					data = dp.getData().clone();
					// synchronized (lp) {
					// lp.addLast(data);
					// }
					if (true) {
						long id = PRead.getLong(data, 10, 4);
						long time = PRead.getLong(data, 2, 4);
						byte[] sound = PRead.getByte(data, 14, 10);
						sound = Convert.encrypt(sound, key);
						sound = dec.process(sound);
						sl.add(id, new Pack(time, sound));
//						System.out.println(id);
					}
				} catch (Exception e) {
					//e.printStackTrace();
					//System.out.println("timeout");
				}
			}
			sl.close();
			System.out.println("end sound recive");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
