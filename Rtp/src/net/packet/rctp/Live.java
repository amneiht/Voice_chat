package net.packet.rctp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.RandomStringUtils;

import net.packet.io.PWrite;

public class Live extends RctpSample {
	long id, group;
	static String publicIp;
	byte[] info;
	static {
		URL url_name;
		try {
			url_name = new URL("http://bot.whatismyipaddress.com");
			BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
			publicIp = sc.readLine().trim();
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				publicIp = InetAddress.getLocalHost().getAddress().toString().trim();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		}

	}
	public static void main(String[] args) {
		Live lp = new Live(11002, 202002, "sssdwxsbhj");
		System.out.println(lp.info.length);
	}
	public Live(long user, long gr, String key) {
		id = user;
		group = gr;
		ntp = System.currentTimeMillis();
		type = 1001;
		DESencrypt(key);
		length = 20 + info.length;
	}

	private void DESencrypt(String key) {
		String token = RandomStringUtils.randomAlphanumeric(5) + ":" + publicIp;// salt + ip
		String subkey = key.substring(0, 8);
	//	System.out.println("token : "+token);
		SecretKeySpec skeySpec = new SecretKeySpec(subkey.getBytes(), "DES");
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			info = cipher.doFinal(token.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public byte[] toPacket() {
		// TODO Auto-generated method stub
		byte[] res = new byte[length];
		PWrite._16bitToArray(res, type, 0);
		PWrite._16bitToArray(res, length, 2);
		PWrite._64bitToArray(res, ntp, 4);
		PWrite._32bitToArray(res, group, 12);
		PWrite._32bitToArray(res, id, 16);
		PWrite.copyArray(info, res, 20);
		return res;
	}
}
