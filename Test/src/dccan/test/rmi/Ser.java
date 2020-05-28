package dccan.test.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Ser {
	public static void main(String[] args) throws RemoteException {
		String host = "localhost";
		if (args.length > 0) {
			host = args[0];
		}
		try {
			// setSettings();
			System.setProperty("java.rmi.server.hostname", host);
			TestInput call = new TestInput();
			LocateRegistry.createRegistry(8888);
			Registry registry = LocateRegistry.getRegistry(host, 8888);
			UnicastRemoteObject.exportObject(call, 0);
			registry.bind("Hello", call);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
