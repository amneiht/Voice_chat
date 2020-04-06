package net.packet.rctp;

import net.packet.io.PWrite;

public class Bye extends RctpSample {
	long id, group;

	public Bye(long user, long gr) {
		id = user;
		group = gr;
		ntp = System.currentTimeMillis();
		type = 1111;
		length = 20 ;
	}

	public byte[] toPacket() {
		// TODO Auto-generated method stub
		byte []res = new byte[20];
		PWrite._16bitToArray(res, type, 0);
		PWrite._16bitToArray(res, length, 2);
		PWrite._64bitToArray(res, ntp, 4);
		PWrite._32bitToArray(res, group, 12);
		PWrite._32bitToArray(res, id, 16);
		return res;
	}

}
