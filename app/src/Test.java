import dccan.connect.Login;
import dccan.connect.Task;

public class Test {
	public static void main(String[] args) {
		Login ln = new Login("localhost");
		String token = ln.log("dccan", "1");
		Task ts = new Task("localhost", token);
	}
}
