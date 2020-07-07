import java.io.File;

public class Xm {
	public static void main(String[] args) throws Exception {
		// String path = Xm.class.get
		ClassLoader loader = Xm.class.getClassLoader();
		System.out.println(loader.getResource(""));
		final File f = new File(Xm.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		System.out.println(f.getPath());
		String lts = Xm.class.getResource("").getPath();
		System.out.println(lts);
	}
}
