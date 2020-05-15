package dccan.remote;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	private static Remote rmi = null;
	public static Communication connect;
	static Registry registry;
	static {
		Client.init("localhost");
	}

	public static void init(String host) {
		try {
			// setSettings();
			// registry = LocateRegistry.getRegistry(host, 8888, new
			// SslRMIClientSocketFactory());

			registry = LocateRegistry.getRegistry(host, 8888);
			connect = (Communication) registry.lookup("Hello");
			rmi = new Remote(connect);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Remote getRmi() {
		return rmi;
	}

	protected static void setSettings() {
		String path = new File("").getAbsolutePath();
		String pass = "dangcongcan"; // ko duoc tu tien thay doi
		System.setProperty("javax.net.ssl.debug", "all");
		System.out.println(path + "/ssl/server/KeyStore.jks");
		System.setProperty("javax.net.ssl.keyStore", path + "/ssl/client/KeyStore.jks");
		System.setProperty("javax.net.ssl.trustStore", path + "/ssl/client/truststore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", pass);
		System.setProperty("javax.net.ssl.trustStorePassword", pass);
	}

}
