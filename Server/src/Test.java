import java.rmi.RemoteException;
import java.util.LinkedList;

import dccan.server.rmi.RemoteObj;

public class Test {
public static void main(String[] args) {
	RemoteObj rmi = new RemoteObj() ;
	try {
		//rmi.register("can", "1", "admin", "nomail");
		rmi.createGroup("thien ha vo dong", new LinkedList<String>()) ;
		rmi.createGroup("thien ha vo dong", new LinkedList<String>()) ;
		rmi.createGroup("thien ha vo dong", new LinkedList<String>()) ;
		System.out.println("ok");
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
