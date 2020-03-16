package amneiht.media;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlayMedia {
	SourceDataLine speakers;

	public PlayMedia(AudioFormat format) throws LineUnavailableException {
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
		speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		speakers.open(format);
		speakers.start();
	}

	public void play(byte[] data) {

		speakers.write(data, 0, data.length);
		
	}
	public void play(byte[] data,int d) {

		speakers.write(data, 0, d);
		
	}
	public void stop() {
		speakers.drain();
		speakers.close();
	}

}
