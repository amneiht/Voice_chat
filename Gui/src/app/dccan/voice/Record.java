package app.dccan.voice;

import java.util.LinkedList;
import java.util.List;

import org.mobicents.media.server.impl.dsp.audio.g729.Encoder;

import amneiht.media.NetAudioFormat;
import amneiht.media.Recorder;
import net.help.Convert;
import net.packet.Nrtp.RtpClient;

public class Record implements Runnable {
	static final byte[] cmp = new Encoder().process(new byte[160]);// byte to compare
	private long id, gp;
	List<byte[]> lp = new LinkedList<byte[]>();

	public Record(long id, long gp) {
		this.id = id;
		this.gp = gp;
	}

	class Send implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Encoder en = new Encoder();
			try {
				RtpClient rtp = new RtpClient(RtpSystem.inet, gp, id, RtpSystem.key, RtpSystem.rtport);
				while (RtpSystem.run) {
					byte[] dat = null;
					synchronized (lp) {
						if (!lp.isEmpty())
							dat = lp.remove(0);
					}
					if (dat != null) {
						byte[] send = en.process(dat);
						send = Convert.encrypt(send, RtpSystem.key);
						if (cmp(send))
							rtp.send(send);
					}
				}

				rtp.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
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

	public void run() {
		try {
			Recorder rd = new Recorder(NetAudioFormat.getG729AudioFormat());
			new Thread(new Send()).start();
			while (RtpSystem.run) {
				if (!RtpSystem.mute) {
					byte[] rc = rd.getSound(160);
					lp.add(rc);
				}
			}
			rd.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
