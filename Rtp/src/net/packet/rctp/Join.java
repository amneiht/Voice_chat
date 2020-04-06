package net.packet.rctp;

import net.packet.io.PWrite;

public class Join extends RctpSample{
	byte [] ps;
	int port  ;
	public Join(String g , int pt)
	{
		port = pt ;
		ps =g.getBytes();
		ntp = System.currentTimeMillis();
		type = 1000;
		length = 16+ps.length;
	}
	@Override
	public byte[] toPacket() {
		// TODO Auto-generated method stub
		
		byte[] res = new byte[length];
		PWrite._16bitToArray(res, type, 0);
		PWrite._16bitToArray(res, length, 2);
		PWrite._64bitToArray(res, ntp, 4);
		PWrite._32bitToArray(res, port, 12);
		PWrite.copyArray(res, ps, 16);
		return null;
	}
}
