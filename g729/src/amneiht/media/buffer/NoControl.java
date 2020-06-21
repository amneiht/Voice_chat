package amneiht.media.buffer;

import java.io.Closeable;
import java.util.LinkedList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import amneiht.media.NetAudioFormat;
import amneiht.media.PlayMedia;

public class NoControl extends Voice implements Closeable {
	public static int siz = 15;
	boolean status = false;
	PlayMedia pm;
	long last = 0, pre = 0;
	long lose = 1 * 1000 * 60;// 1 p
	LinkedList<Pack> np = new LinkedList<Pack>();
	Long live;
	int packetsize;

	public NoControl(AudioFormat af, int packetsize) throws LineUnavailableException {
		pm = new PlayMedia(af);
		this.packetsize = packetsize;
		int time = (int) ((packetsize * 8000) / (af.getSampleRate() * af.getSampleSizeInBits()));
		disconnect = siz * time;
		System.out.println(lose);
	}

	public static void main(String[] args) {
		try {
			new NoControl(NetAudioFormat.getG729AudioFormat(), 160);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int dem = 0;
	int spl = 500;
	long disconnect;

	public void addList(Pack con) {
		live = System.currentTimeMillis();

		if (dem == 0)
			last = live;
		dem++;
		if (con.sq < pre)
			return;
		if (con.sq - pre > disconnect) {

			status = false;
			np.add(con);
			dem = np.size();
			last = live;
			pre = con.sq;
			return;
		}
		pre = con.sq;
		if (!status) {
			if (dem > siz) {
				status = true;
				while (!np.isEmpty()) {
					pm.play(np.removeFirst().data);
				}
				pm.play(con.data);
			} else {
				np.add(con);
			}
		} else {
			pm.play(con.data);
			if ((pm.isSampleSupport()) && dem > spl) {
				long gm = live - last;
				double k = (dem - 1) * 1000;// doi don vi voi do tinh la ms
				k = k / gm;// Time on one packet
				float sr = (float) (k * packetsize);
				pm.setSample(sr); // thay doi toc do phat
				dem = 0;
			}
		}
		System.out.println(System.currentTimeMillis() - live);
	}

	public void close() {
		status = false;
		pm.stop();
	}

	@Override
	public boolean isrun() {
		// TODO Auto-generated method stub
		return System.currentTimeMillis() - live < lose;
	}

}
