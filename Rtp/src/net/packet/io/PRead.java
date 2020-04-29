package net.packet.io;

public class PRead {
	public static int getLeng(byte[] ls) {
		int d = unsignByteToInt(ls[0]) << 8;
		d = d + unsignByteToInt(ls[1]);
		return d;
	}

	public static int getType(byte[] ls) {
		int d = unsignByteToInt(ls[2]) << 8;
		d = d + unsignByteToInt(ls[3]);
		return d;
	}

	public static long getLong(byte[] ls, int offset, int length) {
		long time = 0;
		for (int i = 0; i < length; i++) {
			time = time << 8;
			time = time + unsignByteToInt(ls[offset + i]);
		}
		return time;
	}

	public static String getString(byte[] ls, int offset, int length) {
		byte[] clgt = new byte[length];
		for (int i = 0; i < length; i++) {
			clgt[i] = ls[i + offset];
		}
		return new String(clgt);
	}

	private static int unsignByteToInt(byte k) {
		return (256 + k) % 256;
	}

	public static byte[] getByte(byte[] ls, int offset, int length) {
		byte[] clgt = new byte[length];
		for (int i = 0; i < length; i++) {
			clgt[i] = ls[i + offset];
		}
		return clgt;
	}
}
