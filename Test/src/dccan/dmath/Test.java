package dccan.dmath;

public class Test {
	static Kalman km;
	static double dem = 0, dt = 0;

	public static void main(String[] args) {
		double dt = 0.1d;
		// position measurement noise (meter)
		double measurementNoise = 10d;
		// acceleration noise (meter/sec^2)
		double accelNoise = 1d;
		init(dt, measurementNoise, accelNoise);
		
		double [] p = { 1 ,15,69,100,45,34,34,33,60,5,-19};
		int h =0;
		for(double z:p)
		{
			h++;
			km.put(new Matrix(new double[] { z, h }).T());
			km.X.print();
			System.out.println();
		}

	}

	public static void init(double ds, double measurementNoise, double accelNoise) {
		dt = ds;
		Matrix F = new Matrix(new double[][] { { 1, dt, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, dt }, { 0, 0, 0, 1 } });
		Matrix G = null;

		Matrix H = new Matrix(new double[][] { { 1, 0, 0, 0 }, { 0, 0, 1, 0 } });

		Matrix Q = new Matrix(new double[][] { { Math.pow(dt, 4) / 4.0, Math.pow(dt, 3) / 2.0, 0, 0 },
				{ Math.pow(dt, 3) / 2.0, Math.pow(dt, 2), 0, 0 },
				{ 0, 0, Math.pow(dt, 4) / 4.0, Math.pow(dt, 3) / 2.0, },
				{ 0, 0, Math.pow(dt, 3) / 2.0, Math.pow(dt, 2) } });
		Q.print();
		Matrix R = new Matrix(
				new double[][] { { Math.pow(measurementNoise, 2), 0 }, { 0, Math.pow(measurementNoise, 2) } });
		Matrix P = new Matrix(new double[][] { { 1, 1, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } });
		Matrix X = new Matrix(new double[] { 1, 0, 1, 0 }).T();

		km = new Kalman(F, G, Q, H, P, R, X);
	}

}
