package amneiht.media;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlayMedia {
	SourceDataLine speakers;
	FloatControl sample;
	boolean support;

	public PlayMedia(AudioFormat format) throws LineUnavailableException {
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
		speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		speakers.open(format);
		support = speakers.isControlSupported(FloatControl.Type.SAMPLE_RATE);
		if (support) {
			sample = (FloatControl) speakers.getControl(FloatControl.Type.SAMPLE_RATE);
		}
		speakers.start();

	}

	public void play(byte[] data) {

		speakers.write(data, 0, data.length);
	}

	public void play(byte[] data, int d) {
		speakers.write(data, 0, d);
	}

	public void stop() {
		speakers.drain();
		speakers.close();
	}

	public boolean isSampleSupport() {
		return support;
	}

	public void setSample(float d) {
		if (!support)
			System.out.println("Your system dosen't support this method");
		else {
			sample.setValue(d);
		}

	}

}
