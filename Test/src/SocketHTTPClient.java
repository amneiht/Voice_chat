import java.io.*;
import java.lang.reflect.Type;
import java.net.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SocketHTTPClient {
	public static void main(String[] args) {

		String hostName = "www.martinbroadhurst.com";
		int portNumber = 80;

		try {
			InetAddress inet = InetAddress.getByName(hostName);
			Gson gp = new Gson();
			String lp = new Gson().toJson(inet);
			Type ts = new TypeToken<InetAddress>() {
			}.getType();
			InetAddress is = gp.fromJson(lp, ts);
			Socket socket = new Socket(is , portNumber);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.println("GET / HTTP/1.1\nHost: www.martinbroadhurst.com\n\n");
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			socket.close();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}
}