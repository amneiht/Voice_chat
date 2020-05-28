package dccan.test.rmi;

import java.rmi.registry.Registry;

import com.google.gson.Gson;

public class client {

	public static RmiIn connect;
	static Registry registry;
	public static String host = null;

	public static void main(String[] args) throws Exception {
	byte [][] lo = new byte[10][10];
	String ls = new Gson().toJson(lo);
	System.out.println(ls);
	}
}
