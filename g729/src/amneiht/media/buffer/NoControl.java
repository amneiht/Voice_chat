package amneiht.media.buffer;

import javax.sound.sampled.LineUnavailableException;

import amneiht.media.NetAudioFormat;
import amneiht.media.PlayMedia;

public class NoControl extends Voice {

	boolean run = true;
	PlayMedia pm;
	PlayMedia spm; // media format with speed is x1.2

	public NoControl() throws LineUnavailableException {
		pm = new PlayMedia(NetAudioFormat.getG729AudioFormat());
		spm = new PlayMedia(NetAudioFormat.getG729AudioFormat(1.2F));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addList(byte[] con, long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

}
