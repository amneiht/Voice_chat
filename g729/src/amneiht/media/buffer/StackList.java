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
	boolean run = true;

	public void close() throws IOException {
		synchronized (mp) {
			run = false;
			Set<Long> key = mp.keySet();
			for (long id : key) {
				mp.get(id).close();
			}
			System.out.println("close statck list");
			Thread.currentThread().interrupt();
		}
	}

	public void add(Long id, Pack p) {
		Voice d = mp.get(id);
		if (d != null) {
			d.addList(p);
		} else {
			try {
				// Voice nc = new NoControl(NetAudioFormat.getG729AudioFormat(),160);
				Voice nc = new NoCotrol(NetAudioFormat.getG729AudioFormat(), 160);
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
			while (true) {
				Thread.sleep(1 * 1000);// ngu 10s phut roi xoa cac luong khong ton tai
				synchronized (mp) {
				
					if (!run)
						break;
					Iterator<Map.Entry<Long, Voice>> it = mp.entrySet().iterator();
					for (; it.hasNext();) {
						Map.Entry<Long, Voice> x = it.next();
						if (!x.getValue().isrun())
							it.remove();
					}
				}
	
			}
		} catch (Exception e) {
		}

	}

}
