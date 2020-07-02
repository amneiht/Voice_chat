package dccan.server.sercu;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	private static final String key = "Amneiht 15/09/97";
	private static Cipher en, dec;
	static {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
			en = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			dec = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			en.init(Cipher.ENCRYPT_MODE, skeySpec);
			dec.init(Cipher.DECRYPT_MODE, skeySpec);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String enCrypt(String original) {
		byte[] byteEncrypted;
		try {
			byteEncrypted = en.doFinal(original.getBytes());
			return Base64.getEncoder().encodeToString(byteEncrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String deCrypt(String original) {
		byte[] byteEncrypted;
		try {
			byteEncrypted = dec.doFinal(Base64.getDecoder().decode(original));
			return new String(byteEncrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
