import dccan.suport.BCrypt;

public class Test {
	public static void main(String[] args) {
		String password = "testgame"; 
		String lp = BCrypt.hashpw(password, BCrypt.gensalt());
		System.out.println(lp);
		System.out.println(BCrypt.checkpw(password, lp));
	}
}
