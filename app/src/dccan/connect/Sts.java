package dccan.connect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

import dccan.sercuty.Des;

/**
 * gui thong tin len server , nhan ve 1 chuoi da ma hoa <br>
 * roi giai ma no
 * 
 * @author dccan
 *
 */
public class Sts {
	private static int send = 1024 * 8;// 8kB

	public static String getString(String host, String gson) throws Exception {
		String enc = Des.encrypt(gson);
		Socket sc = Info.TCPconnect(host);
		DataOutputStream out = new DataOutputStream(sc.getOutputStream());
		out.writeUTF(enc);
		out.flush();
		DataInputStream in = new DataInputStream(sc.getInputStream());
		String get = in.readUTF();

		out.close();
		in.close();
		sc.close();
		return Des.decrypt(get);
	}

	public static String sendFile(String host, String gson, String file) throws Exception {
		String enc = Des.encrypt(gson);
		Socket sc = Info.TCPconnect(host);
		DataOutputStream out = new DataOutputStream(sc.getOutputStream());
		out.writeUTF(enc);
		out.flush();
		FileInputStream fin = new FileInputStream(new File(file));
		byte[] sock = new byte[send];
		int read = 0;
		while (true) {
			read = fin.read(sock);
			if (read < 0)
				break;
			out.write(sock, 0, read);

		}
		out.flush();
		DataInputStream in = new DataInputStream(sc.getInputStream());
		String get = in.readUTF();
		fin.close();
		out.close();
		in.close();
		sc.close();
		return Des.decrypt(get);
	}

	public static String downFile(String host, String gson, String filename, String url) throws Exception {
		String enc = Des.encrypt(gson);
		Socket sc = Info.TCPconnect(host);
		FileOutputStream fout = new FileOutputStream(url+"/"+filename);
		DataOutputStream out = new DataOutputStream(sc.getOutputStream());
		out.writeUTF(enc);
		out.flush();
		
		DataInputStream in = new DataInputStream(sc.getInputStream());
		byte[] sock = new byte[send];
		int read = 0;
		while (true) {
			read = in.read(sock);
			if (read < 0)
				break;
			fout.write(sock, 0, read);

		}
		String get = in.readUTF();
		fout.close();
		out.close();
		in.close();
		sc.close();
		return Des.decrypt(get);
	}

}
