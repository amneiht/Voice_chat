package net.help;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Convert {

	public static byte[] objectToByte(Object obj) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(obj);
			out.flush();
			byte[] yourBytes = bos.toByteArray();
			bos.close();
			out.close();
			return yourBytes;
		} catch (Exception e) {
			return null;
		}
	}
	public static int unsignByteToInt( byte k )
	{
		return (256+k)%256;
	}
	public static Object byteToObject(byte[] input) {
		ByteArrayInputStream bis = new ByteArrayInputStream(input);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Object o = in.readObject();
			bis.close();
			in.close();
			return o;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}
}
