package amneiht.media.buffer;

import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;

import amneiht.media.NetAudioFormat;
import amneiht.media.PlayMedia;

public class NoControl extends Voice {
	public static int siz = 20;
	boolean run = true;
	boolean status = false;
	PlayMedia pm;
	static long lose = 1 * 1000 * 60;// 1 p
	List<Pack> np = new LinkedList<Pack>();
	Long live;

	public NoControl() throws LineUnavailableException {
		pm = new PlayMedia(NetAudioFormat.getG729AudioFormat());
		live = System.currentTimeMillis();
	}

	public List<Pack> getNp() {
		return np;
	}

	@Override
	public void run() {
		while (run) {
			play();
			// System.out.println("pla");
		}

	}

	@Override
	public void addList(Pack con) {
		live = System.currentTimeMillis();
		
		synchronized (np) {

			int k = np.size();
			if (k == 0)
				np.add(con);
			else {
				if (con.sq < np.get(0).sq)
					return;
				if (con.sq > np.get(k - 1).sq) {
					np.add(con);
					if (k > siz)
						status = true;
					return;
				}
				for (int i = k - 2; i > -1; i--) {
					if (con.sq > np.get(i).sq) {
						np.add(i + 1, con);
						if (k > siz)
							status = true;
						return;
					}
				}

			}
		}
	}

	public void play() {
		System.out.println("tt");
		if (status) {
			byte[] dt;
			synchronized (np) {
				dt = np.remove(0).data;
			}
			pm.play(dt);
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
		status = false;
		pm.stop();
	}

	@Override
	public boolean isRun() {
		// TODO Auto-generated method stub
		return run;
	}

}
