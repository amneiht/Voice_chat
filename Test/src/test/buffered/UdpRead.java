package test.buffered;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.mobicents.media.server.impl.dsp.audio.g729.Encoder;

public class UdpRead implements Runnable {
	private LinkedList<byte[]> lp = new LinkedList<byte[]>();

	public static void main(String[] args) {
		try {
			UdpRead up = new UdpRead();
			new Thread(up).start();
			up.playSound("/home/dccan/Music/p2.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final int BUFFER_SIZE = 80 * 2;
	private File soundFile;
	private AudioInputStream audioStream;

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

		while (true) {

			try {
				audioStream = AudioSystem.getAudioInputStream(soundFile);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

			int nBytesRead = 0;
			byte[] abData = new byte[BUFFER_SIZE];
			while (nBytesRead != -1) {
				try {
					nBytesRead = audioStream.read(abData, 0, abData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (nBytesRead >= 0) {
					byte[] res = abData.clone();
					synchronized (lp) {
						lp.addLast(res);
					}
					Thread.sleep(8);
				
				}
				//System.out.println(System.currentTimeMillis() - tm);
			}
			audioStream.close();

		}

	}

	boolean run = true;

	@Override
	public void run() {
		try {
			DatagramSocket client = new DatagramSocket();
			InetAddress inet = InetAddress.getByName("127.0.0.1");
			int port = 9981;
			Encoder en = new Encoder();
			DatagramPacket dp;
			while (run) {
				byte[] res = null;
				synchronized (lp) {
					if (!lp.isEmpty()) {
						res = lp.removeFirst();
					}
				}
				if (res != null) {
					res = en.process(res);
					dp = new DatagramPacket(res, res.length, inet, port);
					client.send(dp);
				}

			}
			client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}