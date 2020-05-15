package amneiht.dccan.filter;

public class Hanning {
	int size;
	int lfft;
	double[] funtion;

	public static void main(String[] args) {
		Hanning op = new Hanning(80);
		System.out.println(op.lfft);
		for(double sp:op.funtion)
		{
			System.out.println(sp);
		}
	}

	public Hanning(int size) {
		this.size = size;
		int z = (int) (Math.log(size) / Math.log(2)) + 1;
		lfft = (int) Math.pow(2, z);
		funtion = new double[size];
		for (int i = 0; i < size; i++) {
			double ps = 0.5 - 0.5 * Math.cos(2 * Math.PI * (i+size) / (2*size));
			funtion[i] = ps;
		}
//		funtion[1]=1;
//		funtion[2]=1;
//		funtion[3]=1;
	}

	public short[] process(short[] rm) {
		//if(true) return rm ;
		Complex[] cp = new Complex[lfft];
		for (int i = 0; i < size; i++) {
			double ps = rm[i]/ 32768.0;
			cp[i] = new Complex(ps, 0);
		}
		for (int i = size; i < lfft; i++) {
			cp[i] = new Complex(0, 0);
		}
		Complex[] cv = FFT.fft(cp);
		for (int i = 0; i < size; i++) {
			cv[i] = cv[i].scale(funtion[i]);
		}
		for (int i = size; i < lfft; i++) {
			cv[i] = cv[i].scale(0);
		}
		cv = FFT.ifft(cv);
		short[] res = new short[size];
		for (int i = 0; i < size; i++) {
			res[i] = (short) (cv[i].re()*32768.0);
		}
		return res;
	}
}
