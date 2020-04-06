package net.packet.rctp;

public abstract class RctpSample {
	int type =0;// loai goi tin
	long ntp = 0;// nhan thoi gian
	int length =0;// do dai goi tin tinh theo 4 byte
//	byte extend;// phan mo rong de nang cap len - dat  o cuoi

	public int getType() {
		return type;
	}

	public long getNtp() {
		return ntp;
	}

	public int getLength() {
		return length;
	}

	public abstract byte[] toPacket();
}