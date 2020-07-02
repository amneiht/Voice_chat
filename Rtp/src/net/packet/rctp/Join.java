package net.packet.rctp;

import net.help.Convert;
import net.packet.io.PWrite;

public class Join extends RctpSample {
	byte[] ps;
	int port;
	byte[] key ;
	String user;

	public Join(String g, int pt, String key, String user) {
		port = pt;
		ps = g.getBytes();
		ntp = System.currentTimeMillis();
		type = 1000;
		length = 16 + ps.length;
		this.key = key.getBytes();
		this.user = user;
	}

	@Override
	public byte[] toPacket() {
		// TODO Auto-generated method stub
		try {
			byte[] re = Convert.encrypt(user.getBytes(), key);
			byte[] res = new byte[length + 8];
			PWrite._16bitToArray(res, type, 0);
			PWrite._16bitToArray(res, length, 2);
			PWrite._64bitToArray(res, ntp, 4);
			PWrite._16bitToArray(res, port, 12);
			PWrite.copyArray(ps, res, 16);
			PWrite.copyArray(re, res, length);
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
