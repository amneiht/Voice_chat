package test.noise;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.mobicents.media.server.impl.dsp.audio.g729.Decoder;
import org.mobicents.media.server.impl.dsp.audio.g729.Encoder;
import org.mobicents.media.server.impl.dsp.audio.g729.Util;

import amneiht.media.PlayMedia;
import dev.adaptive.Adapt08;

public class UdpRead implements Runnable {
	private List<byte[]> lp = new LinkedList<byte[]>();
	static int N = 160;
	int dem = 0;
	
	public static void main(String[] args) {
		try {
			UdpRead up = new UdpRead();
			up.playSound("/home/dccan/Music/p2.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final int BUFFER_SIZE = 80 * 2;
	private File soundFile;
	private AudioInputStream audioStream;
	AudioFormat af;

	/**
	 * @param filename
	 *            the name of the file that is going to be played
	 * @throws Exception
	 */

	public void playSound(String filename) throws Exception {

		@SuppressWarnings("resource")

		String strFilename = filename;
		try {
			soundFile = new File(strFilename);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		Decoder dc = new Decoder();
		Encoder ec = new Encoder();
		// PlayMedia pm = new PlayMedia(NetAudioFormat.getG729AudioFormat());
		// while (true)
		{

			try {
				audioStream = AudioSystem.getAudioInputStream(soundFile);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			af = audioStream.getFormat();
			new Thread(this).start();
			int nBytesRead = 0;
			byte[] abData = new byte[BUFFER_SIZE];
			byte[] res;
			while (nBytesRead != -1) {
				try {
					nBytesRead = audioStream.read(abData, 0, abData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (nBytesRead >= 0) {
					short[] rm = Util.byteArrayToShortArray(abData);
					rm = hangming(rm);
					res = Util.shortArrayToByteArray(rm);
					// res = abData.clone();
					lp.add(res);
					// Thread.sleep(10);
				}
			}
			audioStream.close();
			// run = false ;
		}
	}

	int mlg = 3;

	private short[] hangming2(short[] rm) {
		short[] ps = rm.clone();
		int z = mlg / 2;
		for (int i = z; i < rm.length - z; i++) {
			int p = 0;
			for (int j = -z; j <= z; j++) {
				p = rm[i+z] + p;
			}
			p = p / 5;
			ps[i] = (short) p;
		}
		return ps;
	}
	private short[] hangming(short[] rm) {
		short[] ps = rm.clone();
		int z = mlg / 2;
		short []ss=new short[5];
		for (int i = z; i < rm.length - z; i++) {
			
			for (int j = -z; j <= z; j++) {
				ss[j+z] = rm[i+z] ;
			}
			Arrays.sort(ss);
			ps[i] = ss[z];
		}
		return ps;
	}
	boolean run = true;

	@Override
	public void run() {
		System.out.println("pl");
		try {
			PlayMedia pm = new PlayMedia(af);
			while (run) {
				if (!lp.isEmpty()) {
					byte[] res = lp.remove(0);
					pm.play(res);
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}