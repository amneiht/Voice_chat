package dccan.remote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	private static NoToken rmi = null;
	public static Communication connect;
	static Registry registry;
	public static String host = null;
	public static String path;
	static {
//		path = new File(Client.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
		path = "/home/dccan/app/software/Chat";
	}

	public static void init(String hosts) {
		try {
			host = hosts;
			setSettings();
			// normal
			registry = LocateRegistry.getRegistry(host, 8888);
			// ssl
			// registry = LocateRegistry.getRegistry(host, 8888, new
			// SslRMIClientSocketFactory());
			connect = (Communication) registry.lookup("Hello");
			rmi = new NoToken(connect);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static NoToken getRmi() {
		return rmi;
	}

	public static void main(String[] args) {
		System.out.println(Client.class.getResource("").getPath());
	}

	protected static void setSettings() {
		String pass = "dangcongcan"; // ko duoc tu tien thay doi
		System.setProperty("javax.net.ssl.debug", "all");
		System.setProperty("javax.net.ssl.keyStore", path + "/ssl/client/KeyStore.jks");
		System.setProperty("javax.net.ssl.trustStore", path + "/ssl/client/truststore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", pass);
		System.setProperty("javax.net.ssl.trustStorePassword", pass);
	}

}
