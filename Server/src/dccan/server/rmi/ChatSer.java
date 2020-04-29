package dccan.server.rmi;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;

import dccan.server.control.chat.RctpServer;

public class ChatSer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String host = "localhost";
		if (args.length > 0) {
			host = args[0];
		}
		try {
			setSettings();
			new Thread(new RctpServer()).start();
			System.setProperty("java.rmi.server.hostname", host);
			RemoteObj call = new RemoteObj();
			LocateRegistry.createRegistry(8888, new SslRMIClientSocketFactory(),
					new SslRMIServerSocketFactory(null, null, true));
			Registry registry = LocateRegistry.getRegistry(host, 8888, new SslRMIClientSocketFactory());
			UnicastRemoteObject.exportObject(call, 0);
			registry.bind("Hello", call);
			System.out.println("run server");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private static void setSettings() {
		String path = new File("").getAbsolutePath();
		System.out.println(path);
		String pass = "dangcongcan"; // ko duoc tu tien thay doi
		System.setProperty("javax.net.ssl.debug", "all");
		System.out.println( path + "/ssl/server/KeyStore.jks");
		System.setProperty("javax.net.ssl.keyStore", path + "/ssl/server/KeyStore.jks");
		System.setProperty("javax.net.ssl.trustStore", path + "/ssl/server/truststore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", pass);
		System.setProperty("javax.net.ssl.trustStorePassword", pass);
	}
}
