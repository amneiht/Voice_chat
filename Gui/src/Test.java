import java.rmi.RemoteException;

import dccan.remote.Client;
import dccan.remote.Remote;

public class Test {
	public static void main(String[] args) {
		Remote rm = Client.getRmi();
		try {
			rm.login("can", "1");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
