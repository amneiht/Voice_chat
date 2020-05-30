import dccan.server.rmi.RemoteObj;

public class Test {
	public static void main(String[] args) {
		RemoteObj rmi = new RemoteObj();
		try {
			String token = rmi.login("can","1");
			System.out.println(rmi.isAdmin(token, "0SKkCmU3sC8Bsp7FFeLM5yCjsATJ6oMN"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
