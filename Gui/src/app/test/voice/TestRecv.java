package app.test.voice;

import java.rmi.RemoteException;

import app.dccan.voice.RtpSystem;
import dccan.remote.Client;
import dccan.remote.Remote;

public class TestRecv {
	static String gp = "Adw5nIIKxO7wyLSk57nCdE1TS1vlSdKa";
	public static void main(String[] args) {
		Remote rmi = Client.getRmi();
		try {
			rmi.login("pikapika", "12345678");
			RtpSystem.Connect(gp);
			RtpSystem.mute();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
