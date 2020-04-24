package net.packet.tranfer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.help.Convert;
import net.help.Info;
import net.packet.NotUseRtp;
import net.packet.io.PWrite;

public class RtpClient {
	private int port, sq;
	private DatagramSocket client = null;

	public long getSsrc() {
		return ssrc;
	}

	public void setSsrc(int ssrc) {
		this.ssrc = ssrc;
	}

	private long ssrc;
	private String host;

	/**
	 * 
	 * @param p
	 *            port
	 * @param hs
	 *            serverhost
	 * @param ssr
	 *            nguon dong bo
	 * @param pid
	 *            dinh danh cua nguoi su dung
	 * @throws SocketException
	 */
	public RtpClient(int p, String hs, long ssr) throws SocketException {
		port = p;
		host = hs;
		sq = (int) (Math.random() * 9999);
		ssrc = ssr;
		client = new DatagramSocket();

	}

	public int getPort() {
		return client.getPort();
	}

	public void send(NotUseRtp pack) {
		byte[][] data = creatPacket(pack);
		DatagramPacket send;
		InetAddress inet;
		try {
			inet = InetAddress.getByName(host);
			for (byte[] p : data) {
				send = new DatagramPacket(p, p.length, inet, port);
				client.send(send);

			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.err.println("host :" + host + " khong xac dinh");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("loi gui du lieu");
		}

	}

	public byte[][] creatPacket(NotUseRtp pack) {
		byte[] header = getHeader(pack);
		if (pack.isPadding()) {
			byte[] pad = getPadding(pack.getPad());
			int lheader = header.length;
			int lpad = pad.length;
			int lg = Info.MTU - (lheader + lpad);// kich thuoc payload
			int cp = pack.getSend().length / lg;// so goi tin gui di
			byte[][] mg = new byte[cp + 1][];//
			for (int i = 0; i < cp; i++) {
				mg[i] = new byte[Info.MTU];
				PWrite.copyArray(header, mg[i], 0);
				PWrite.copyArray(pack.getSend(), i * lg, lg, mg[i], lheader);
				PWrite.copyArray(pad, mg[i], lg + lheader);
				addSequenceNumber(mg[i]);
			}

			int d = cp * lg;
			int z = pack.getSend().length - d;
			PWrite.copyArray(header, mg[cp], 0);
			PWrite.copyArray(pack.getSend(), cp * lg, z, mg[cp], lheader);
			PWrite.copyArray(pad, mg[cp], lg + lheader);
			addSequenceNumber(mg[cp]);
			return mg;
		} else {
			int lheader = header.length;
			int lg = Info.MTU - (lheader);// kich thuoc payload
			int cp = pack.getSend().length / lg;// so goi tin gui di
			byte[][] mg = new byte[cp + 1][];//
			for (int i = 0; i < cp; i++) {
				mg[i] = new byte[Info.MTU];
				PWrite.copyArray(header, mg[i], 0);
				PWrite.copyArray(pack.getSend(), i * lg, lg, mg[i], lheader);
				addSequenceNumber(mg[i]);
			}
			int d = cp * lg;
			int z = pack.getSend().length - d;
			PWrite.copyArray(header, mg[cp], 0);
			PWrite.copyArray(pack.getSend(), cp * lg, z, mg[cp], lheader);
			addSequenceNumber(mg[cp]);
			return mg;
		}
	}

	private void addSequenceNumber(byte[] lg) {
		PWrite._16bitToArray(lg, sq, 2);
		sq = (sq + 1) & 0xffff;
	}

	private byte[] getPadding(Object pad) {
		// TODO Auto-generated method stub
		byte[] ez = Convert.objectToByte(pad);
		int g = ez.length;
		byte[] rs = new byte[g + 1];
		for (int i = 0; i < g; i++) {
			rs[i] = ez[i];
		}
		rs[g] = (byte) g;
		return rs;
	}

	private byte[] getHeader(NotUseRtp pack) {
		byte[] ret = null;
		if (pack.isExtend()) {
			byte[] ext = Convert.objectToByte(pack.getEx());
			int size = ext.length;
			// kich thuoc header
			int lg = 12 + 4 + size;// 12 byte co dinh , 4 byte cua tieu de mo rong , con lai la cua phan mo rong
			ret = new byte[lg];
			ret[0] = (byte) 0b1000000; // set version bang 2
			if (pack.isPadding())
				ret[0] = (byte) (ret[0] | 0b100000); // bat bit padding
			ret[0] = (byte) (ret[0] | 0b10000); // bat bit extended
			ret[1] = (byte) (pack.getPayloadtype() & 0x7f); // them playload type
			PWrite._32bitToArray(ret, System.currentTimeMillis(), 4);// them nhan thoi gian
			PWrite._32bitToArray(ret, ssrc, 8);// them ma nguon dong bo
			PWrite._16bitToArray(ret, size, 14);
			PWrite.copyArray(ext, ret, 16);
		} else {
			ret = new byte[12];
			ret[0] = (byte) 0b1000000; // set version bang 2
			if (pack.isPadding())
				ret[0] = (byte) (ret[0] | 0b100000); // bat bit padding
			ret[1] = (byte) (pack.getPayloadtype() & 0x7f); // them playload type
			// them nhan thoi gian
			PWrite._32bitToArray(ret, System.currentTimeMillis(), 4);
			PWrite._32bitToArray(ret, ssrc, 8);// them ma nguon dong bo
		}
		return ret;
	}

	// copy cac mang vao nhau

}
