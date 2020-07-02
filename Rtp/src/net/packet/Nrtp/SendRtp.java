package net.packet.Nrtp;

import net.help.Convert;
import net.packet.io.PWrite;

public class SendRtp {
	byte[] header = new byte[8];
	byte[] key;
	int type = 800;

	// type :0 ; ntp : 2 ; group 6 ,id :10 , data :14
	public SendRtp(long group, long id, String key) {
		try {
			this.key = key.getBytes();
			createHeader(group, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public byte[] send(byte[] dt) {
		byte[] data = Convert.encrypt(dt, key);
		int p = data.length;
		byte[] send = new byte[p + 14];// do dai header
		PWrite._16bitToArray(send, type, 0);
		PWrite._32bitToArray(send, System.currentTimeMillis(), 2);
		PWrite.copyArray(header, send, 6);
		PWrite.copyArray(data, send, 14);
		return send;

	}
	public void createHeader(long group, long id) {
		PWrite._32bitToArray(header, group, 0);
		PWrite._32bitToArray(header, id, 4);
	}
}
