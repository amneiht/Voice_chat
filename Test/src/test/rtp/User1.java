package test.rtp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

import dccan.remote.Client;
import dccan.remote.Remote;
import dccan.suport.GetList;
import net.help.Convert;
import net.packet.Nrtp.RtpClient;
import net.packet.io.PRead;
import net.packet.rctp.Join;

public class User1 implements Runnable {
	String user, pass;
	static String gp = "0SKkCmU3sC8Bsp7FFeLM5yCjsATJ6oMN";
	static boolean run = true;

	public static void main(String[] args) {
		new Thread(new User1("can2", "1")).start();
		//new Thread(new User1("can", "1")).start();
		
	}

	public User1(String up, String pa) {
		// TODO Auto-generated constructor stub
		user = up;
		pass = pa;
	}

	
	@Override
	public void run() {
		Remote rmi = Client.getRmi();
		try {
			rmi.login(user, pass);
			String ls = rmi.getUserkey(gp);
			List<String> ld = GetList.listString(ls);
			for (String up : ld) {
				System.out.println(user + " " + up);
			}
			InetAddress ip = InetAddress.getByName("localhost");
			DatagramSocket client = new DatagramSocket();
			DatagramSocket get = new DatagramSocket();
			Join clm = new Join(gp, client.getLocalPort(), ld.get(0), ld.get(1));
			byte[] jn = clm.toPacket();
			DatagramPacket dp = new DatagramPacket(jn, jn.length, ip, 8890);
			get.send(dp);
			DatagramPacket ddp = new DatagramPacket(new byte[30], 30);
			get.receive(ddp);
			jn = ddp.getData(); 
			jn =  Convert.encrypt(jn, ld.get(0));
			long group = PRead.getLong(jn, 10, 4);
			long id = PRead.getLong(jn, 6, 4);
			System.out.println(user+" : id "+id+" gp "+group);
			if (id == 0) {
				System.out.println(user + " no connect ");
				client.close();
				get.close();
				return ;
			}
			System.out.println(user + " start");
			new Thread(new TestRun(client)).start();
			byte[] data = new byte[10];
			RtpClient np = new RtpClient(ip, group, id, ld.get(0), 8889);
			while (run) {
				np.send(data);
				Thread.sleep(100);
			}
			np.close();
			get.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class TestRun implements Runnable {
		DatagramSocket get;

		public TestRun(DatagramSocket ge) {
			get = ge;
		}

		@Override
		public void run() {
			DatagramPacket dp = new DatagramPacket(new byte[28], 28);
			while (run) {
				try {
					get.receive(dp);
					System.out.println(user + " get data");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			get.close();
		}

	}
}
