package app.test.voice;

import java.rmi.RemoteException;

import app.dccan.voice.RtpSystem;
import dccan.remote.Client;
import dccan.remote.NoToken;

public class TestRecv {
	static String gp = "6oSA7wthxFrYq0wgwwR5YW3WQ8bS9JnS";
	public static void main(String[] args) {
		NoToken rmi = Client.getRmi();
		try {
			rmi.login("test1", "1");
			RtpSystem.Connect(gp);
			RtpSystem.mute();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
