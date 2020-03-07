package net.help;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class Time {
	public static void main(String[] args) {
		int k = 0x8f;
		byte l =(byte) k;
		int d =(l+256)%256;
		System.out.println(d);
	}

	public static byte[] getTimeStamp() {
		long ss = System.currentTimeMillis();
		byte[] res = new byte[4];
		res[0] = (byte) ((ss & (0xffL << 40)) >> 40);
		res[0] = (byte) ((ss & (0xffL << 32)) >> 32);
		res[0] = (byte) ((ss & (0xffL << 24)) >> 24);
		res[0] = (byte) ((ss & (0xffL << 16)) >> 16);
		return res;
	}

	@SuppressWarnings("unused")
	private static void getTimestamp1() {
		String TIME_SERVER = "time-a.nist.gov";
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress;

		try {
			inetAddress = InetAddress.getByName(TIME_SERVER);
			TimeInfo timeInfo = timeClient.getTime(inetAddress);
			long returnTime = System.currentTimeMillis() - timeInfo.getMessage().getTransmitTimeStamp().getTime();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		System.out.println();

	}
}
