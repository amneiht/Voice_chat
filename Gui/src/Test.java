import java.rmi.RemoteException;

import dccan.remote.Communication;
import dccan.remote.Client;

public class Test {
	public static void main(String[] args) {
		Client.init("localhost");
		Communication rmi = Client.getRmi();
		try {
			System.out.println("Run");
			rmi.login("ma", "1");
			rmi.requestGroup("VKv3wAKsJszEar201ohkjfCqSEQZUFBJ");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
