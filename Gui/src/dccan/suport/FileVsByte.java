package dccan.suport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileVsByte {
	public static final int max=16*1000*1000;
	public static byte[] toByte(File file ) {
		try {
			byte[] bytesArray = new byte[(int) file.length()];

			FileInputStream fis;
			fis = new FileInputStream(file);
			fis.read(bytesArray); // read file into bytes[]
			fis.close();

			return bytesArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static byte[] toByte(String input) {
		try {
			File file = new File(input);
			// init array with file length
			byte[] bytesArray = new byte[(int) file.length()];

			FileInputStream fis;
			fis = new FileInputStream(file);
			fis.read(bytesArray); // read file into bytes[]
			fis.close();

			return bytesArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void toFile(byte[] bytes, String path) {

		try {
			File file = new File(path);

			OutputStream os;
			os = new FileOutputStream(file);
			os.write(bytes);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
