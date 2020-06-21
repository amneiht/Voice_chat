package amneiht.media.buffer;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import amneiht.media.NetAudioFormat;

public class StackList implements Runnable, Closeable {
	Map<Long, Voice> mp = new HashMap<Long, Voice>();
	Boolean run = true;

	public void close() throws IOException {
		run = false;
		Set<Long> key = mp.keySet();
		for (long id : key) {
			mp.get(id).close();
		}
	}

	public void add(Long id, Pack p) {
		Voice d = mp.get(id);
		if (d != null) {
			d.addList(p);
		} else {
			try {
				// Voice nc = new NoControl(NetAudioFormat.getG729AudioFormat(),160);
				Voice nc = new BufferCotrol(NetAudioFormat.getG729AudioFormat(), 160);
				nc.addList(p);
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
				Thread.sleep(5 * 60 * 1000);// ngu 2 phut roi xoa cac luong khong ton tai
				Iterator<Map.Entry<Long, Voice>> it = mp.entrySet().iterator();
				for (; it.hasNext();) {
					Map.Entry<Long, Voice> x = it.next();
					if (x.getValue().isrun())
						it.remove();
				}
			}
		} catch (Exception e) {
		}

	}

}
