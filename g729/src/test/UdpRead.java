package test;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.mobicents.media.server.impl.dsp.audio.g729.Encoder;

import amneiht.media.Afomart;
import amneiht.media.PlayMedia;

public class UdpRead {
	public static void main(String[] args) {
		try {
			new UdpRead().playSound("/home/dccan/Music/rc.wav");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final int BUFFER_SIZE = 128000;
	private File soundFile;
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceLine;

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

		try {
			audioStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		DatagramPacket dp;
		DatagramSocket client = new DatagramSocket();
		audioFormat = audioStream.getFormat();
		InetAddress inet = InetAddress.getByName("localhost");
		int port = 9981;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		PlayMedia pm = new PlayMedia(audioFormat);
		sourceLine.start();
		Encoder en  = new Encoder();
		int nBytesRead = 0;
		byte[] abData = new byte[160];
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				// @SuppressWarnings("unused")
				// int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
				byte [] eb = en.process(abData);
				dp = new DatagramPacket(eb, eb.length, inet, port);
				client.send(dp);
				Thread.sleep(13);
//				pm.play(abData);
			}
		}
		pm.stop();
		client.close();
		sourceLine.drain();
		sourceLine.close();
	}
}
