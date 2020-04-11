package amneiht.media.buffer;

import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;

import amneiht.media.NetAudioFormat;
import amneiht.media.PlayMedia;

public class NoControl extends Voice {
	public static int siz = 13;
	boolean run = true;
	boolean status = false;
	PlayMedia pm;
	static long lose = 1 * 1000 * 60;// 1 p
	List<Pack> np = new LinkedList<Pack>();
	Long live = System.currentTimeMillis();

	public NoControl() throws LineUnavailableException {
		pm = new PlayMedia(NetAudioFormat.getG729AudioFormat());
	}

	@Override
	public void run() {
		while (run) {
			play();

		}

	}

	@Override
	public void addList(Pack con) {

		int k = np.size();
		if (k == 0)
			np.add(con);
		else {
			if (con.sq < np.get(0).sq)
				return;
			for (int i = k - 1; i > -1; i--) {
				if (con.sq > np.get(i).sq) {
					np.set(i + 1, con);
					if (k > siz)
						status = true;
					return;
				}
			}

		}
	}

	public void play() {
		if (status) {
			byte[] dt = np.remove(0).data;
			pm.play(dt);
			live = System.currentTimeMillis();
			if (np.size() == 0)
				status = false;
		} else {
			if (System.currentTimeMillis() - live > lose)
				end();
		}

	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void end() {
		// TODO Auto-generated method stub
		run = false;
		pm.stop();
	}

	@Override
	public boolean isRun() {
		// TODO Auto-generated method stub
		return run;
	}

}
