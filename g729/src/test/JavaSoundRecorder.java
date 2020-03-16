package test;
import javax.sound.sampled.*;

import amneiht.media.Afomart;
import amneiht.media.PlayMedia;
import amneiht.media.Recorder;

import java.io.*;
 
/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class JavaSoundRecorder {
   public static void main(String[] args) {
	AudioFormat af = Afomart.getG729AudioFormat();
	try {
		System.out.println(af.getFrameRate());
		Recorder rp = new Recorder(af,1);
		PlayMedia pl = new PlayMedia(af);
		byte [][] res = new byte[20][];
		for(int i=0;i<res.length;i++)
		{
			res[i]=rp.getSound();
			System.out.println(res[i].length);
		}
		for(int i=0;i<res.length;i++)
		{
			pl.play(res[i]);
		}
		rp.stop();
		pl.stop();
	} catch (LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}