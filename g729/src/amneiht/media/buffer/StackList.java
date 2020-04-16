package amneiht.media.buffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StackList implements Runnable {
	Map<Long, Voice> mp = new HashMap<Long, Voice>();
	Boolean run = true;

	public void end() {
		run = false;
	}

	public void add(Long id, Pack p) {
		Voice d = mp.get(id);
		if (d != null) {
			d.addList(p);
		} else {
			try {
				
				NoControl nc = new NoControl();
				nc.addList(p);
				new Thread(nc).start();
				mp.put(id, nc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void run() {
		try {
			while (run) {
				Thread.sleep(2 * 60 * 1000);// ngu 2 phut roi xoa cac luong khong ton tai
				Set<Long> key = mp.keySet();
				for (long id : key) {
					if (!mp.get(id).isRun()) {
						mp.remove(id);
					}
				}
			}
		} catch (Exception e) {
		}

	}
}