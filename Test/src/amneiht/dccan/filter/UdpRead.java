package amneiht.dccan.filter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.mobicents.media.server.impl.dsp.audio.g729.Decoder;
import org.mobicents.media.server.impl.dsp.audio.g729.Encoder;

import amneiht.media.PlayMedia;

public class UdpRead implements Runnable {
	private List<byte[]> lp = new LinkedList<byte[]>();
	
	Hanning noise = new Hanning(80);
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
		 
		String strFilename = filename;
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
			new Thread(this).start();
			int nBytesRead = 0;
			byte[] abData = new byte[BUFFER_SIZE];
			byte[] res;
			Encoder en = new Encoder();
			Decoder dec = new Decoder();
			while (nBytesRead != -1) {
				try {
					nBytesRead = audioStream.read(abData, 0, abData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (nBytesRead >= 0) {
					res = abData.clone();
//					res = en.process(res);
//					res= dec.process(res);
					lp.add(res);
					// Thread.sleep(10);
				}
			}
			audioStream.close();
			// run = false ;
		}
	}

	int mlg = 3;

	private short[] hangming(short[] rm) {
		return noise.process(rm);
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