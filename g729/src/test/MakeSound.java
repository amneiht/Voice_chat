package test;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import amneiht.media.NetAudioFormat;

public class MakeSound implements Runnable {

	private final int BUFFER_SIZE = 128000;
	private File soundFile;
	private AudioInputStream audioStream;
	// private AudioFormat audioFormat;
	private SourceDataLine sourceLine;
	String filename;

	public static void main(String[] args) {
		new Thread(new MakeSound("/home/dccan/Music/t1.wav")).start();
		new Thread(new MakeSound("/home/dccan/Music/t2.wav")).start();

		// new Thread(new MakeSound("/home/dccan/Music/rc.wav")).start();
	}

	public MakeSound(String d) {
		filename = d;
	}

	/**
	 * @param filename
	 *            the name of the file that is going to be played
	 */
	public void run() {

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

		AudioFormat audioFormat = audioStream.getFormat();
		AudioFormat format = audioFormat;// NetAudioFormat.getG729AudioFormat();

		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(format);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		sourceLine.start();

		int nBytesRead = 0;
		byte[] abData = new byte[BUFFER_SIZE];
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				@SuppressWarnings("unused")
				int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
			}
		}

		sourceLine.drain();
		sourceLine.close();
	}
}