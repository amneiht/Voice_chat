package test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.mobicents.media.server.impl.dsp.audio.g729.Decoder;

import amneiht.media.PlayMedia;
import amneiht.media.buffer.Pack;
import amneiht.media.buffer.StackList;

public class TestDataGramServer {

	public static void main(String[] args) {
		try {
			int set = 50;
			DatagramPacket dp = new DatagramPacket(new byte[set], set);
			DatagramSocket ser = new DatagramSocket(9009);
			byte[][] mg = new byte[10][];

			for (int i = 0; i < 10; i++) {
				ser.receive(dp);
				mg[i] = dp.getData().clone();
				if (set != dp.getLength()) {
					set = dp.getLength();
					dp = new DatagramPacket(new byte[set], set);
				}

			}
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < mg[i].length; j++)
					System.out.print(mg[i][j] + "  ");

				System.out.println("\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
