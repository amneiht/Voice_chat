package app.test.voice;

import java.rmi.RemoteException;

import app.dccan.voice.RtpSystem;
import dccan.remote.Client;
import dccan.remote.NoToken;

public class TestRecv {
	static String gp = "8zsS84wM0yzhj78Kb0xkGAq0dwSq9oUf";
	public static void main(String[] args) {
		Client.init("192.168.1.100");
		NoToken rmi = Client.getRmi();
		
		try {
			rmi.login("can", "1");
			RtpSystem.Connect(gp);
			RtpSystem.mute();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
