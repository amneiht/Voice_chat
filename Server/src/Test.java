import java.util.LinkedList;
import java.util.List;

import dccan.server.rmi.RemoteObj;

public class Test {
	public static void main(String[] args) {
		RemoteObj rmi = new RemoteObj();
		try {
			String token =rmi.login("can2", "1");
			List<String> cn = new LinkedList<String>();
			cn.add("can");
			rmi.createGroup(token,"GTFO", cn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
