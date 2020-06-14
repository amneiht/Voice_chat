package app.test.voice;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.mobicents.media.server.impl.dsp.audio.g729.Encoder;

import amneiht.media.NetAudioFormat;
import amneiht.media.Recorder;
import app.dccan.voice.RtpSystem;
import net.packet.Nrtp.RtpClient;

public class Recoder2 implements Runnable {
	static final byte[] cmp = new Encoder().process(new byte[160]);// byte to compare
	private long id, gp;
	List<byte[]> lp = new LinkedList<byte[]>();

	public Recoder2(long id, long gp) {
		this.id = id;
		this.gp = gp;
	}

	class Send implements Runnable {

		@Override
		public void run() {
			Encoder en = new Encoder();
			try {
				RtpClient rtp = new RtpClient(GetVoice.inet, gp, id, GetVoice.key, GetVoice.rtport);
				while (RtpSystem.run) {
					byte[] dat = null;
					 synchronized (lp)
					{
						if (!lp.isEmpty())
							dat = lp.remove(0);
					}
					if (dat != null) {
						byte[] send = en.process(dat);
						rtp.send(send);

					}
				}

				rtp.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	static boolean cmp(byte[] res) {
		int lg = res.length;
		for (int i = 0; i < lg; i++) {
			if (res[i] != cmp[i])
				return true;
		}
		return false;
	}

	private final int BUFFER_SIZE = 80 * 2;
	private File soundFile;
	private AudioInputStream audioStream;
	AudioFormat af;

	public void run() {
		try {
			Recorder rd = new Recorder(NetAudioFormat.getG729AudioFormat());
			new Thread(new Send()).start();

			String strFilename = "/home/dccan/Music/p2.wav";
			try {
				soundFile = new File(strFilename);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			// while (true)
			{
				try {
					audioStream = AudioSystem.getAudioInputStream(soundFile);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
				af = audioStream.getFormat();
				System.out.println(af.isBigEndian() + "   " + af.getChannels());
				int nBytesRead = 0;
				byte[] abData = new byte[BUFFER_SIZE];
				while (nBytesRead != -1) {
					try {
						nBytesRead = audioStream.read(abData, 0, abData.length);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (nBytesRead >= 0) {
						synchronized (lp) {
							lp.add(abData.clone());
						}
						Thread.sleep(10);
					}
				}
				audioStream.close();
				// run = false ;
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
