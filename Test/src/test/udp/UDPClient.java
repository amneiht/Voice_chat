package test.udp;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

public class UDPClient {

	public static void main(String args[]) {

		try {
			DatagramSocket socket = new DatagramSocket();
			byte[] inData = new byte[1024];
			byte[] outData = new byte[1024];
			InetAddress IP = InetAddress.getByName("192.168.1.100");
			
			String data = "hello kaka";
			outData = data.getBytes();			
			long tm = System.currentTimeMillis() ;
			for(int i=0;i<1000;i++)
			{
			DatagramPacket sendPkt = new DatagramPacket(outData, outData.length, IP, 8000);
			socket.send(sendPkt);
			}
			System.out.println(System.currentTimeMillis()-tm);
		} catch (Exception e) {

			System.out.println("error connect udp server");

		}

	}

}