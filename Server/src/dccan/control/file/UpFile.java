package dccan.control.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import dccan.sql.file.SFile;

public class UpFile {
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(8888);
			while (true) {
				Socket e = ss.accept();
				download(e, "6969c0507ae867be42a9ea3c2ffa4534");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private static void download(Socket e, String string) {

		try {
			InputStream in = SFile.downloadFile(string);
			OutputStream out = e.getOutputStream();
			byte[] sock = new byte[8000];
			int read = 0;
			System.out.println("send");
			while (true) {
				read = in.read(sock);
				if (read < 0)
					break;
				out.write(sock, 0, read);

			}
			System.out.println("ok");
			out.flush();
			out.close();
			in.close();
			e.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static void send(Socket e, String s) {
		// TODO Auto-generated method stub
		try {
			InputStream in = e.getInputStream();
			String id = SFile.createFileId(s);
			SFile.insert(id, in, "doan xem");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
