import amneiht.media.NetAudioFormat;
import amneiht.media.PlayMedia;
import amneiht.media.Recorder;
 
public class Xm {
public static void main(String[] args) throws Exception {
	int lg = 100*160*9 ;
	PlayMedia pm = new PlayMedia(NetAudioFormat.getG729AudioFormat());
	Recorder rd = new Recorder(NetAudioFormat.getG729AudioFormat()); 
	byte [] ls = rd.getSound(lg);
	pm.play(ls);
	pm.stop();
	rd.close();
}
}
