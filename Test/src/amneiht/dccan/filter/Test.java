package amneiht.dccan.filter;

public class Test {
	public static void main(String[] args) {
		Complex[] cp = new Complex[32];
		for (int i = 0; i < cp.length; i++) {
			cp[i] = new Complex(i+1, 0);
		}
		cp = FFT.fft(cp);
		cp=FFT.ifft(cp);
		for (int i = 0; i < cp.length; i++) {
			System.out.println(cp[i].toString());
		}
		
	}
}
