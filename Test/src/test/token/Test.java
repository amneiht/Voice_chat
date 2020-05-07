package test.token;

public class Test {
	public static void main(String[] args) {
		long time = System.currentTimeMillis() ;
		String lg = null;
		for (int i = 0; i < 100000; i++) {
			if (i == 500)
				lg = ListUser2.addNew("" + i);
			else
				ListUser2.addNew("" + i);
		}
		System.out.println(System.currentTimeMillis()-time);
		System.out.println(ListUser2.getUserByToken(lg));
		System.out.println(System.currentTimeMillis()-time);
	}
}
