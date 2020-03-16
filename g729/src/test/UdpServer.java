package test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.sound.sampled.AudioFormat;

import org.mobicents.media.server.impl.dsp.audio.g729.Decoder;

import amneiht.media.Afomart;
import amneiht.media.PlayMedia;

public class UdpServer {
public static void main(String[] args) {
	AudioFormat af = Afomart.getG729AudioFormat();
	try {
		PlayMedia pm = new PlayMedia(af);
		byte [] in =new byte[7000];
		DatagramPacket dp =new DatagramPacket(in, in.length) ;
		DatagramSocket ser = new DatagramSocket(9981);
		Decoder dec = new Decoder();
		while(true)
		{
			ser.receive(dp);
			
			pm.play(dec.process(dp.getData()));
			if(1==2)break ;
		}
		ser.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
