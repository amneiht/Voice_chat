package net.packet.io;

public class PWrite {
	
	// ghi thong tin tu class rtp thanh chuoi byte
	
	public static void copyArray(byte[] src, int start, int length, byte[] des, int offset) {
		for (int i = 0; i < length; i++) {
			des[offset + i] = src[start + i];
		}
	}

	public static void copyArray(byte[] src, byte[] des, int offset) {
		int start = 0, length = src.length;
		for (int i = 0; i < length; i++) {
			des[offset + i] = src[start + i];
		}
	}

	public static void _16bitToArray(byte[] bt, int add, int start) {
		bt[start] = (byte) ((add >> 8) & 0xff);
		bt[start + 1] = (byte) (add & 0xff);
	}

	public static void addLongToArray(byte[] bt, long add, int start) {
		// co the gap loi tran mang
		bt[start] = (byte) ((add >> 24) & 0xff);
		bt[start + 1] = (byte) ((add >> 16) & 0xff);
		bt[start + 2] = (byte) ((add >> 8) & 0xff);
		bt[start + 3] = (byte) (add & 0xff);
	}

}
