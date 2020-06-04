
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
public class DemoDES {
  public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    String SECRET_KEY = "1234567889";
    SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "DES");
    
    String original = "pikaloplop";
    System.out.println(original.length());
    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    long tm = System.currentTimeMillis();
    byte[] byteEncrypted = cipher.doFinal(original.getBytes());
    String encrypted =  Base64.getEncoder().encodeToString(byteEncrypted);
    System.out.println(byteEncrypted.length);
    
    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
    byte[] byteDecrypted = cipher.doFinal(byteEncrypted);
    String decrypted = new String(byteDecrypted);
    
    System.out.println("original  text: " + original);
    System.out.println("encrypted text: " + encrypted);
    System.out.println("decrypted text: " + decrypted);
    
  }
}