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

	private String key;

	LinkedList<byte[]> lp = new LinkedList<byte[]>();

	public Sound(String key) {
		this.key = key;
	}

	class Loa implements Runnable {
		@Override
		public void run() {

			try {
				Decoder dec = new Decoder();
				StackList sl = new StackList();
				//PlayMedia pm = new PlayMedia(NetAudioFormat.getG729AudioFormat());
				new Thread(sl).start();
				while (RtpSystem.run) {
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
						//pm.play(sound);
					}
				}
				sl.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void run() {
		DatagramSocket ds = RtpSystem.voice;
		new Thread(new Loa()).start();
		try {
			DatagramPacket dp = new DatagramPacket(new byte[30], 30);
			while (RtpSystem.run) {
				ds.receive(dp);
				synchronized (lp) {
					lp.add(dp.getData().clone());
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
