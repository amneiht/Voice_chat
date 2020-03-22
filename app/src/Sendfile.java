
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Sendfile {
	public static void main(String[] args) {
	
	}

	public static void download(String str, Socket sc) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File(str));

			InputStream in = sc.getInputStream();
			byte[] sock = new byte[8000];
			int read = 0;
			System.out.println("send");
			while (true) {
				read = in.read(sock);
				if (read < 0)
					break;
				out.write(sock, 0, read);

			}
			out.flush();
			out.close();
			in.close();
			sc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void send(String str, Socket sc) {
		try {
			FileInputStream in = new FileInputStream(new File(str));
			OutputStream out = sc.getOutputStream();
			byte[] sock = new byte[8000];
			int read = 0;
			System.out.println("send");
			while (true) {
				read = in.read(sock);
				if (read < 0)
					break;
				out.write(sock, 0, read);

			}
			out.flush();
			out.close();
			in.close();
			sc.close();
			//

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
