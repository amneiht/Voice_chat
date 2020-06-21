package app.dccan.voice;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import org.mobicents.media.server.impl.dsp.audio.g729.Encoder;

import amneiht.media.NetAudioFormat;
import amneiht.media.Recorder;
import dccan.remote.Client;
import dccan.suport.GetList;
import dccan.suport.Member;
import net.packet.Nrtp.SendRtp;

public class Record implements Runnable {
	static final byte[] cmp = new Encoder().process(new byte[160]);// byte to compare
	private long id, gp;
	LinkedList<byte[]> lp = new LinkedList<byte[]>();
	String group;
	static List<Member> clp = new LinkedList<Member>();

	public Record(long id, long gp) {
		this.id = id;
		this.gp = gp;
	}

	public static List<Member> getMemList(String group) {
		try {
			String lop = Client.getRmi().chatMember(group);
			List<Member> lp = GetList.chatMember(lop);
			synchronized (clp) {
				clp.clear();
				clp.addAll(lp);
			}
			return lp;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	class Send implements Runnable {

		@Override
		public void run() {

			DatagramSocket socket;
			try {
				socket = new DatagramSocket();

				Encoder en = new Encoder();

				SendRtp pack = new SendRtp(gp, id, RtpSystem.key);

				while (RtpSystem.run) {
					byte[] dat = null;
					synchronized (lp) {
						if (!lp.isEmpty())
							dat = lp.removeFirst();
					}
					if (dat != null) {
						byte[] data = en.process(dat);
						data = pack.send(data);
						synchronized (clp) {
							for (Member mp : clp) {
								DatagramPacket dp = new DatagramPacket(data, data.length, mp.getIna(), mp.getPort());
								socket.send(dp);
							}
						}

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

	public void run() {
		try {
			Recorder rd = new Recorder(NetAudioFormat.getG729AudioFormat());
			new Thread(new Send()).start();
			while (RtpSystem.run) {
				if (!RtpSystem.mute) {
					byte[] rc = rd.getSound(160);
					synchronized (lp) {
						lp.addLast(rc);
					}

				}
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
