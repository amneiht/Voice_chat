package test.noise;

import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.DefaultProcessModel;
import org.apache.commons.math3.filter.KalmanFilter;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.filter.ProcessModel;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Kalman {
	// 2d version
	KalmanFilter kf;
	double dem = 0, dt;
	public static void main(String[] args) {
		double dt = 0.01d;
		// position measurement noise (meter)
		double measurementNoise = 5d;
		// acceleration noise (meter/sec^2)
		double accelNoise = 0.1d;
		Kalman km = new Kalman(dt, measurementNoise, accelNoise);
		short [] cs = {10,10,10,10,10,0,10,10,10};
		cs = km.filter(cs);
		for(int i=0;i<cs.length;i++)
		{
			System.out.println(cs[i]);
		}
		
		RealVector x = new ArrayRealVector(new double[] {0,0});
		System.out.println(x.getDimension());
	}
	public Kalman(double dt, double measurementNoise, double accelNoise)
	{
		init(dt, measurementNoise, accelNoise);
	}
	public void init(double dt, double measurementNoise, double accelNoise) {
		this.dt = dt;
		RealMatrix A = new Array2DRowRealMatrix(
				new double[][] { { 1, dt, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, dt }, { 0, 0, 0, 1 } });
		RealMatrix B = null;

		RealMatrix H = new Array2DRowRealMatrix(new double[][] { { 1, 0, 0, 0 }, { 0, 0, 1, 0 } });

		RealMatrix Q = new Array2DRowRealMatrix(new double[][] { { Math.pow(dt, 4) / 4.0, Math.pow(dt, 3) / 2.0, 0, 0 },
				{ Math.pow(dt, 3) / 2.0, Math.pow(dt, 2), 0, 0 },
				{ 0, 0, Math.pow(dt, 4) / 4.0, Math.pow(dt, 3) / 2.0, },
				{ 0, 0, Math.pow(dt, 3) / 2.0, Math.pow(dt, 2) } });

		RealMatrix R = new Array2DRowRealMatrix(
				new double[][] { { Math.pow(measurementNoise, 2), 0 }, { 0, Math.pow(measurementNoise, 2) } });
		RealMatrix P = new Array2DRowRealMatrix(
				new double[][] { { 1, 1, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } });
		RealVector x = new ArrayRealVector(new double[] {1,0,1,0});
		ProcessModel pm = new DefaultProcessModel(A, B, Q, x, P);
		MeasurementModel mm = new DefaultMeasurementModel(H, R);
		kf = new KalmanFilter(pm, mm);
	}

	public short[] filter(short[] pm) {
		short[] res = new short[pm.length];
		for (int i = 0; i < pm.length; i++) {
			kf.predict();
			kf.correct(new double[] { pm[i], 0.1 });
			dem = dem + dt;
			res[i] = (short) kf.getStateEstimation()[0];
		}
		return res;
	}
}
