package test.rtp;

import app.dccan.voice.RtpSystem;
import dccan.remote.Client;
import dccan.remote.Remote;

public class Ps {
	static String gp = "0SKkCmU3sC8Bsp7FFeLM5yCjsATJ6oMN";

	public static void main(String[] args) {
		RtpSystem.mute();
		Remote rmi = Client.getRmi();
		try {
			rmi.login("can", "1");
			RtpSystem.Connect(gp);

			Thread.sleep(50000);
			RtpSystem.end();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
