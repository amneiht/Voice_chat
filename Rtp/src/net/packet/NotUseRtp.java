package net.packet;

public class NotUseRtp {
	public final int ver = 2;
	protected byte[] send;
	protected boolean extend = false;
	protected Object ex,pad = null;
	protected boolean padding = false;
	protected int payloadtype =0 ;
	
	public Object getPad() {
		return pad;
	}
	//protected int ssrc ;// nguon dong bo
	//protected int Timestamp; // nhan thoi gian 32 bit
	public int getVer() {
		return ver;
	}
	public byte[] getSend() {
		return send;
	}
	public boolean isExtend() {
		return extend;
	}
	public Object getEx() {
		return ex;
	}
	public boolean isPadding() {
		return padding;
	}
	public int getPayloadtype() {
		return payloadtype;
	}
}
