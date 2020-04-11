import dccan.server.sql.User;

public class test {
	public static void main(String[] args) {
		try {
			User.register("test2", "1", "pongvan", "haiho");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
