package amneiht.media.buffer;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import amneiht.media.PlayMedia;

public class NoCotrol extends Voice {
	PlayMedia pm;
	int size = 15;
	int length, dem = 0;
	boolean run = true;
	int pack;
	long pre = 0;
	byte[] data;
	long live = 0;

	public NoCotrol(AudioFormat af, int packet) throws LineUnavailableException {
		pm = new PlayMedia(af);
		length = size * packet;
		pack = packet;
		data = new byte[length];
	}

	public static void main(String[] regs) {
		int lp = 15 * 160;
		byte[] ls = new byte[lp];
		long tm = System.currentTimeMillis();
		ls.clone();
		System.out.println(System.currentTimeMillis() - tm);

	}

	public void close() throws IOException {
		pm.stop();
		run = false;
	}

	public void addList(Pack con) {
		live = System.currentTimeMillis();
		if (con.sq > pre) {
			pre = con.sq;
			pm.play(con.data);
		}
	}

	long lose = 1 * 1000 * 60;// 1 p

	public boolean isrun() {
		return System.currentTimeMillis() - live < lose;
	}

}
