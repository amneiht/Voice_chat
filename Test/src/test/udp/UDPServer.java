package test.udp;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

public class UDPServer {

	public static void main(String args[]) {
		try {
			DatagramSocket socket = new DatagramSocket(8000);
			DatagramSocket socket2 = new DatagramSocket();
			System.out.println("server is running");

			// tạo chuỗi byte

			byte[] inServer = new byte[1024];

			byte[] outServer = new byte[1024];

			// tạo packet nhận dữ liệu

			DatagramPacket rcvPkt = new DatagramPacket(inServer, inServer.length);

			while (true) {

				// chờ nhận dữ liệu từ client

				socket.receive(rcvPkt);

				System.out.println("Packet Received!");

				System.out.println("ip Address!" + rcvPkt.getAddress());

				System.out.println("port!" + rcvPkt.getPort());

				System.out.println("message Received!" + new String(rcvPkt.getData()));

				InetAddress IP = rcvPkt.getAddress();

				int port = rcvPkt.getPort();

				// lấy dữ liệu nhận và gửi dữ liệu lại cho client

				String temp = new String(rcvPkt.getData());
				temp = "server :" + temp.toUpperCase();
				outServer = temp.getBytes();
				// gửi dữ liệu lại cho client

				DatagramPacket sndPkt = new DatagramPacket(outServer, outServer.length, IP, port);
				//socket.send(sndPkt);
				socket2.send(sndPkt);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}