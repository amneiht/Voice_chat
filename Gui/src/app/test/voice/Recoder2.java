package app.test.voice;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.mobicents.media.server.impl.dsp.audio.g729.Encoder;

import amneiht.media.NetAudioFormat;
import amneiht.media.PlayMedia;
import app.dccan.voice.RtpSystem;
import dccan.remote.Client;
import dccan.suport.GetList;
import dccan.suport.Member;
import net.packet.Nrtp.SendRtp;

public class Recoder2 implements Runnable {
	static final byte[] cmp = new Encoder().process(new byte[160]);// byte to compare
	private long id, gp;
	LinkedList<byte[]> lp = new LinkedList<byte[]>();
	String group;
	static List<Member> clp = new LinkedList<Member>();

	public Recoder2(long id, long gp) {
		this.id = id;
		this.gp = gp;
	}

	class Send implements Runnable {

		@Override
		public void run() {
			Encoder en = new Encoder();
			DatagramSocket socket;
			try {
				socket = new DatagramSocket();
				SendRtp pack = new SendRtp(gp, id, GetVoice.key);
				long tm2, tm = System.currentTimeMillis() ;
				while (RtpSystem.run) {
					byte[] dat = null;
					synchronized (lp) {
						if (!lp.isEmpty())
							dat = lp.remove(0);
					}
					if (dat != null) {

						byte[] data = en.process(dat);
						data = pack.send(data);
						synchronized (clp) {

							for (Member mp : clp) {

								if (mp.getId() != id) {
									
									DatagramPacket dp = new DatagramPacket(data, data.length, mp.getIna(),
											mp.getPort());
									socket.send(dp);

								}
							}
						}
						tm2 = System.currentTimeMillis() ;
						System.out.println(tm2 -tm);
						tm = tm2 ;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	static boolean cmp(byte[] res) {
		int lg = res.length;
		for (int i = 0; i < lg; i++) {
			if (res[i] != cmp[i])
				return true;
		}
		return false;
	}

	public static List<Member> getMemList(String group) {
		try {
			String lop = Client.getRmi().chatMember(group);
			List<Member> lp = GetList.chatMember(lop);
			if (lp != null) {
				synchronized (clp) {
					clp.clear();
					clp.addAll(lp);
				}
			}
			return lp;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	private final int BUFFER_SIZE = 80 * 2;
	private File soundFile;
	private AudioInputStream audioStream;
	AudioFormat af;

	@Override
	public void run() {
		try {
			// Recorder rd = new Recorder(NetAudioFormat.getG729AudioFormat());
			new Thread(new Send()).start();
			System.out.println("start send data");
			String strFilename = "/home/dccan/Music/p2.wav";
			try {
				soundFile = new File(strFilename);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			PlayMedia pm = new PlayMedia(NetAudioFormat.getG729AudioFormat());
			while (true) {
				audioStream = AudioSystem.getAudioInputStream(soundFile);
				af = audioStream.getFormat();
				int nBytesRead = 0;
				byte[] abData = new byte[BUFFER_SIZE];
				while (nBytesRead != -1) {
					try {
						// long tm = System.currentTimeMillis();
						nBytesRead = audioStream.read(abData, 0, abData.length);
						// System.out.println(System.currentTimeMillis() - tm);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (nBytesRead >= 0) {
						byte[] res = abData.clone();
						synchronized (lp) {
							lp.add(res);
						}
						Thread.sleep(4);
					}
				}
				audioStream.close();
				// run = false ;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
