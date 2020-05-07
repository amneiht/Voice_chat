package dccan.remote;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	// private static Communication rmi = null;
	static Registry registry;
	static {
		init("localhost");
		System.out.println("init");
	}

	public static void init(String host) {
		try {
			registry = LocateRegistry.getRegistry(host, 8888);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Communication getRmi() {
		Communication rmi = null;
		try {

			// registry = LocateRegistry.getRegistry(host, 8888);
			rmi = (Communication) registry.lookup("Hello");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
