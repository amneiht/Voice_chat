package dccan.sercuty;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Des {
	private static boolean init = false;
	private static Cipher encryptCipher = null;
	private static Cipher decryptCipher = null;

	/**
	 * khoi tao bo giai ma
	 * 
	 * @param input
	 * @throws Exception
	 */
	public static void init(String input) throws Exception {
		SecretKey key = getKey(input);
		encryptCipher = Cipher.getInstance("DES");
		decryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
		init = true ;
	}

	private static SecretKey getKey(String in) {
		try {
			DESKeySpec key = new DESKeySpec(in.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			return keyFactory.generateSecret(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static String encrypt(String unencryptedString) throws Exception {
		// Encode the string into bytes using utf-8
		byte[] unencryptedByteArray = unencryptedString.getBytes("UTF8");

		// Encrypt
		byte[] encryptedBytes = encryptCipher.doFinal(unencryptedByteArray);

		// Encode bytes to base64 to get a string

		byte[] encodedBytes = Base64.getEncoder().encode(encryptedBytes);

		return new String(encodedBytes);
	}

	/**
	 * Decrypt a base64 encoded, DES encrypted string and return the unencrypted
	 * string.
	 * 
	 * @param encryptedString
	 *            The base64 encoded string to decrypt.
	 * @return String The decrypted string.
	 * @throws Exception
	 *             If an error occurs.
	 */
	public static String decrypt(String encryptedString) throws Exception {
		// Encode bytes to base64 to get a string
		byte[] decodedBytes = Base64.getDecoder().decode(encryptedString.getBytes());

		// Decrypt
		byte[] unencryptedByteArray = decryptCipher.doFinal(decodedBytes);

		// Decode using utf-8
		return new String(unencryptedByteArray, "UTF8");
	}

	public static boolean isInit() {
		return init;
	}
}
