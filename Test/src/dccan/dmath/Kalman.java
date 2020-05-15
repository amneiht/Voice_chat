package dccan.dmath;

public class Kalman {
	Matrix G, F, Q, P, H, R, X;
	Matrix EI;

	public Kalman(Matrix F, Matrix G, Matrix Q, Matrix H, Matrix P, Matrix R, Matrix X) {
		this.G = G;
		this.F = F;
		this.Q = Q;
		this.H = H;
		this.P = P;
		this.R = R;
		this.X = X;
		init();
	}

	private void init() {
		int row = F.row;
		double[][] rs = new double[row][row];
		for (int i = 0; i < row; i++) {
			rs[i][i] = 1;
		}
		EI = new Matrix(rs);
	}

	public void put(Matrix X1) {
		try {
			// du doan
			// X'(k+1|k) = F * X'(k|k) [
			Matrix X_k1_k = F.mul(X);
			
			// Hiệp phương sai ước lượng dự đoán:
			// P (k+1|k) = F*P(k|k)*Ft + Q k
			Matrix P_k1_k = (F.mul(P).mul(F.T())).add(Q);
		
			// Đo lường dự đoán
			Matrix Z_k1_k = H.mul(X_k1_k);
			
			// Độ lệch đo lường
			Matrix r_k1 = X1.sub(Z_k1_k);

			// Hiệp phương sai độ lệch
			Matrix S_k1 = H.mul(P_k1_k).mul(H.T()).add(R);

			// Độ lời Kalman:
			Matrix K_k1 = P_k1_k.mul(H.T()).mul(S_k1.invertM());

			// Trạng thái ước lượng điều chỉnh:
			Matrix X_k1_k1 = X_k1_k.add(K_k1.mul(r_k1));

			// Hiệp phương sai ước lượng điều chỉnh:
			Matrix P_k1_k1 = (EI.sub(K_k1.mul(H))).mul(P_k1_k);

			// udate du lieu
			X.update(X_k1_k1);
			P.update(P_k1_k1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
