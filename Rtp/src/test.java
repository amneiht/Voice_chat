public class test {
	public static void main(String[] args) {
		String opm = "con ga an dam";
		byte [] rp = opm.getBytes();
		for(int i =0 ;i<rp.length;i++)
		{
			System.out.println(rp[i]+"   "+(int)opm.charAt(i));
		}
		
	}
}
