import dccan.server.sql.Requests;

public class Test {
	public static void main(String[] args) {
		//RemoteObj rmi = new RemoteObj();
		try {
//			String token = rmi.login("can2","1");
//			System.out.println(Groups.getPriMember("35u3WNgPoCaGmgRAWUgjRMbcgwfUZKKx", 1)+token);
			
			Requests.addRequest("can", "can2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
