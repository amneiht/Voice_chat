package dccan.server.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import dccan.server.control.chat.RctpServer;
import dccan.server.control.chat.RtpServer;

public class ChatSer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String host = "localhost";
		if (args.length > 0) {
			host = args[0];
		}
		try {
			// setSettings();
			new Thread(new RctpServer()).start();
			new Thread(new RtpServer()).start();
			System.setProperty("java.rmi.server.hostname", host);
			RemoteObj call = new RemoteObj();
			// LocateRegistry.createRegistry(8888, new SslRMIClientSocketFactory(),
			// new SslRMIServerSocketFactory(null, null, true));
			// Registry registry = LocateRegistry.getRegistry(host, 8888, new
			// SslRMIClientSocketFactory());

			LocateRegistry.createRegistry(8888);
			Registry registry = LocateRegistry.getRegistry(host, 8888);
			UnicastRemoteObject.exportObject(call, 0);
			registry.bind("Hello", call);
			System.out.println("Rmi server run on port: " + 8888);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	protected static void setSettings() {
		String path = System.getenv("CHAT");
		System.out.println("chat:" + path);
		String pass = "dangcongcan"; // ko duoc tu tien thay doi
		System.setProperty("javax.net.ssl.debug", "all");
		System.out.println(path + "/ssl/server/KeyStore.jks");
		System.setProperty("javax.net.ssl.keyStore", path + "/ssl/server/KeyStore.jks");
		System.setProperty("javax.net.ssl.trustStore", path + "/ssl/server/truststore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", pass);
		System.setProperty("javax.net.ssl.trustStorePassword", pass);
	}
}
