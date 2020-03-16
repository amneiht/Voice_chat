package voice.dct;

public class BitCodec {
	public static void main(String[] args) {

		byte[] s = {100,99,98,97,96,95,80,90,99,98,97,96,95,80,90,99,98,97,96,95,80,90,99,98,97,96,95,80,90,99,98,97,96,95,80,90,99,98,97,96,95,80,90,99,98,97,96,95,80,90,99,98,97,96,95,80,90,99,98,97,96,95,80,90 };
		BitCodec bc = new BitCodec(20);
		double[] h=bc.encode_1d(s);
		bc.decode_1d(h);
	}

	public BitCodec(int d) {
		dct = d;
		en = make1d(dct);
		dec=makeDecode(dct);
	}

	public int dct = 10;
	public double[][] en;
	public double[][] dec;
	protected static double[][] make1d(int dct) {
		double[][] mg = new double[dct][dct];
		double idct = 1.0 / dct;
		double a = Math.sqrt(2);
		double b = Math.sqrt(idct);
		//System.out.println(a * b);
		for (int i = 1; i < dct; i++) {
			for (int j = 0; j < dct; j++) {
				mg[i][j] = a * b * Math.cos(idct * i * Math.PI / 2 * (2 * j + 1));
			}
		}
		for (int j = 0; j < dct; j++) {
			mg[0][j] = b;
		}

		return mg;
	}
	protected static double[][] makeDecode(int dct) {
		double[][] mg = new double[dct][dct];
		double idct = 1.0 / dct;
		double a = Math.sqrt(2);
		double b = Math.sqrt(idct);
		//System.out.println(a * b);
		for (int i = 1; i < dct; i++) {
			for (int j = 0; j < dct; j++) {
				mg[j][i] = a * b * Math.cos(idct * j * (Math.PI / 2) * (2 * i + 1));
			}
		}
		for (int j = 0; j < dct; j++) {
			mg[j][0] = b;
		}

		return mg;
	}
	public byte[] decode_1d(double[] in) {
		int g = in.length / dct;
		int sd = in.length % dct;
		double[] fl;
		if (sd != 0) {
			fl = new double[(g + 1) * dct];
			for (int i = 0; i < g; i++) {
				int z = i * dct;
				for (int j = 0; j < dct; j++) {
					for (int k = 0; k < dct; k++) {
						fl[z + j] = fl[z + j] + in[z + k] * dec[j][k];
					}
				}
			}
			int z = g * dct;
			for (int j = 0; j < dct; j++) {
				for (int k = 0; k < dct; k++) {
					if (k < sd)
						fl[z + j] = fl[z + j] + in[z + k] * dec[j][k];
					else
						fl[z + j] = fl[z + j] + in[z + sd - 1] * dec[j][k];
				}
			}
		} else {
			fl = new double[g * dct];
			for (int i = 0; i < g; i++) {
				int z = i * dct;
				for (int j = 0; j < dct; j++) {
					for (int k = 0; k < dct; k++) {
						fl[z + j] = fl[z + j] + in[z + k] * dec[j][k];
					}
				}
			}
		}
		
		byte [] out = new byte[fl.length];
		for(int i=1;i<fl.length;i++)
		{
			out[i]=(byte)((fl[i]+fl[i-1])/0.5);
		
		}
		out[0]=(byte) fl[0];
		return out;

	}
	public double[] encode_1d(byte[] in) {
		int g = in.length / dct;
		int sd = in.length % dct;
		double[] fl;
		if (sd != 0) {
			fl = new double[(g + 1) * dct];
			for (int i = 0; i < g; i++) {
				int z = i * dct;
				for (int j = 0; j < dct; j++) {
					for (int k = 0; k < dct; k++) {
						fl[z + j] = fl[z + j] + in[z + k] * en[j][k];
					}
				}
			}
			int z = g * dct;
			for (int j = 0; j < dct; j++) {
				for (int k = 0; k < dct; k++) {
					if (k < sd)
						fl[z + j] = fl[z + j] + in[z + k] * en[j][k];
					else
						fl[z + j] = fl[z + j] + in[z + sd - 1] * en[j][k];
				}
			}
		} else {
			fl = new double[g * dct];
			for (int i = 0; i < g; i++) {
				int z = i * dct;
				for (int j = 0; j < dct; j++) {
					for (int k = 0; k < dct; k++) {
						fl[z + j] = fl[z + j] + in[z + k] * en[j][k];
					}
				}
			}
		}
		 for(int i=0;i<fl.length;i++)
		 {
		 fl[i]=(int)(fl[i]+0.5);
	//	 System.out.print(fl[i]+" ");
		 }
		// System.out.println();
		return fl;

	}
}
