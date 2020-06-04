package test.buffered;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;

import org.mobicents.media.server.impl.dsp.audio.g729.Decoder;

import amneiht.media.NetAudioFormat;
import amneiht.media.PlayMedia;
import amneiht.media.buffer.Pack;
import amneiht.media.buffer.StackList;

public class UdpServer implements Runnable {

	static List<byte[]> lp = new LinkedList<byte[]>();
	static boolean run = true;

	// @SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) {

		try {

			int set = 50;
			DatagramPacket dp = new DatagramPacket(new byte[set], set);
			DatagramSocket ser = new DatagramSocket(9981);
			Thread p = new Thread(new UdpServer());
			p.start();
			while (run) {

				ser.receive(dp);
				if (dp.getLength() > 9) {
					lp.add(dp.getData().clone());
				}
				if (set != dp.getLength() + 4) {
					set = dp.getLength() + 4;
					dp = new DatagramPacket(new byte[set], set);
					System.out.println(set);
				}
			}
			ser.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		try {
			AudioFormat af = NetAudioFormat.getG729AudioFormat();
			// AudioFormat cf = new AudioFormat(af.getSampleRate(),
			// af.getSampleSizeInBits(), af.getChannels(), true,!af.isBigEndian());
			PlayMedia pm = new PlayMedia(af);
			Decoder dec = new Decoder();
			StackList sl = new StackList();
			long sq = 1;
			while (run) {
				byte[] dt = null;
				synchronized (lp) {
					if (!lp.isEmpty())
						dt = lp.remove(0);
				}
				if (dt != null) {
					dt = dec.process(dt);
					sl.add(1L, new Pack(sq, dt));
					sq++;
					// pm.play(dt);
				}
			}
			sl.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}