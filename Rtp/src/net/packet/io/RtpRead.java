package net.packet.io;

import net.help.Convert;

public class RtpRead {
	public static long getTimeStamp(byte[] data) {
		return getValue(data, 4, 4);
	}

	public boolean isPadding(byte[] data) {
		return (data[0] & 0b100000) != 0;
	}

	public boolean isExtend(byte[] data) {
		return (data[0] & 0b10000) != 0;
	}

	public static int getPayLoad(byte[] dta) {
		int res = dta[1] & 0x7f;
		return res;
	}

	public static Object getPaddingObject(byte[] data) {
		int d = data.length - 1;
		int lg = (int) getValue(data, d, 1);
		return getObject(data, d - lg, lg);
	}

	public static Object getExtendObject(byte[] data) {
		int lg = (int) getValue(data, 14, 2);
		return getObject(data, 16, lg);
	}

	private static Object getObject(byte[] tn, int start, int length) {
		byte[] p = new byte[length];
		for (int i = 0; i < length; i++) {
			p[i] = tn[start + i];
		}
		return Convert.byteToObject(p);
	}

	private static long getValue(byte[] tn, int start, int length) {
		long res = 0;
		for (int i = 0; i < length; i++) {
			res = res | Convert.unsignByteToInt(tn[start + i]);
			res = res << 8;
		}
		return res;
	}
}
