package amneiht.media;

import java.io.Closeable;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Recorder implements Closeable {
	int time = 100;// 1/100 s = 10ms
	int count;
	TargetDataLine microphone;

	public Recorder(AudioFormat af) throws LineUnavailableException {

		count = (int) (af.getFrameRate() / time);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
		microphone = (TargetDataLine) AudioSystem.getLine(info);
		microphone.open(af);
		microphone.start();
		System.out.println(microphone.getBufferSize());

	}
	/**
	 * ghi d giay thoi gian
	 * 
	 * @param af
	 * @param d
	 * @throws LineUnavailableException
	 */
	public Recorder(AudioFormat af, double d) throws LineUnavailableException {

		count = (int) (af.getFrameRate() * d);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, af, 160);
		microphone = (TargetDataLine) AudioSystem.getLine(info);
		microphone.open(af);
		microphone.start();
	}

	public byte[] getSound() {
		byte[] data = new byte[160];
		microphone.read(data, 0, count);
		return data;
	}

	public byte[] getSound(int d) {
		byte[] data = new byte[d];
		microphone.read(data, 0, d);
		return data;
	}

	@Override
	public void close() throws IOException {
		microphone.close();

	}
}
