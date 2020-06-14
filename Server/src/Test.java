import dccan.server.control.user.ListUser2;
import dccan.server.rmi.RemoteObj;

public class Test {
	public static void main(String[] args) {
		RemoteObj rmi = new RemoteObj();
		try {
			String token = rmi.login("can", "2");
			String token2 = rmi.login("can", "2");
			String token3 = rmi.login("can2", "1");
			
			System.out.println(token);
			System.out.println(token2);
			System.out.println(rmi.getInfo(token));
			System.out.println(rmi.getInfo(token2));
		
			synchronized (ListUser2.op) {
				ListUser2.op.remove(token);
				ListUser2.op.remove(token2);
				ListUser2.op.remove(token3);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
