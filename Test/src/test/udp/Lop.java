package test.udp;

import java.io.File;

public class Lop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final File f = new File(Lop.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		System.out.println(f.getParent());
	}

}
