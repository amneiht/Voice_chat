import dccan.server.rmi.RemoteObj;

public class Test {
	public static void main(String[] args) {
		RemoteObj rmi = new RemoteObj();
		try {
			String token =rmi.login("can2", "1");
			rmi.getFriendList(token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
