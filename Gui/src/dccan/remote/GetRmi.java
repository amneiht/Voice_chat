package dccan.remote;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.rmi.ssl.SslRMIClientSocketFactory;

public class GetRmi {
	private static Communication rmi = null;
	static Registry registry;

	public static void init(String host) {
		try {
			setSettings();
			registry = LocateRegistry.getRegistry(host, 8888, new SslRMIClientSocketFactory());
			rmi = (Communication) registry.lookup("Hello");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Communication getRmi() {
		return rmi;
	}

	private static void setSettings() {
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
