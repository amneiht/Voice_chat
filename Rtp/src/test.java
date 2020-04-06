public class test {
	public static void main(String[] args) throws InterruptedException {
	long kds = System.currentTimeMillis();
	System.out.println(kds);
	Thread.sleep(1000);
//	kds = System.currentTimeMillis();
	System.out.println( System.currentTimeMillis()-kds);
	}
}
