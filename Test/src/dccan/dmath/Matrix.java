package dccan.dmath;

public class Matrix {
	private double[][] mg;
	int row, col;

	public static void main(String[] args) {

		double[][] ls= {{1,2,3},{8,4,3},{12,17,19}};
		Matrix z = new Matrix(ls);
		try {
			Matrix d = z.invertM();
			Matrix h = d.mul(z);
			h.print();
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void print() {
		for (double[] ps : mg) {
			for (double d : ps)
				System.out.print(String.format("%.3f", d) + "\t");
			System.out.println();
		}
	}

	public Matrix(double[][] set) {
		mg = set;
		row = mg.length;
		col = mg[0].length;
	}

	public Matrix(double[] set) {
		mg = new double[1][];
		mg[0] = set;
		row = mg.length;
		col = mg[0].length;
	}

	public Matrix invertM() throws MatrixException {
		double[][] res = invert(cloneF(mg));
		if (res[0][0] == Double.NaN) {
			throw new MatrixException("ma tran nay ko the nghich dao");
		}
		return new Matrix(res);
	}

	public Matrix T() {
		double[][] res = new double[col][row];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++)
				res[j][i] = mg[i][j];
		}
		return new Matrix(res);
	}

	public Matrix mul(Matrix b) throws MatrixException {
		if (col != b.row) {
			throw new MatrixException("Khong dung kich thuoc: 2 ma tran nhan voi nhau co gia tri khac hang: "+col+"va cot :"+b.row);
		}
		double[][] res = new double[row][b.col];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < b.col; j++) {
				double p = 0;
				for (int k = 0; k < col; k++) {
					p = p + mg[i][k] * b.mg[k][j];
				}
				res[i][j] = p;
			}
		return new Matrix(res);
	}

	public Matrix mul(double[][] bmg) throws MatrixException {
		if (col != bmg.length) {
			throw new MatrixException("Khong dung kich thuoc: 2 ma tran nhan voi nhau co gia tri khac hang va cot");
		}
		int bcol = bmg[0].length;
		double[][] res = new double[row][bcol];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < bcol; j++) {
				double p = 0;
				for (int k = 0; k < col; k++) {
					p = p + mg[i][k] * bmg[k][j];
				}
				res[i][j] = p;
			}
		return new Matrix(res);
	}

	private static double[][] cloneF(double[][] mg) {
		int row = mg.length;
		int col = mg[0].length;
		double[][] res = new double[row][col];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				res[i][j] = mg[i][j];
			}
		return res;

	}

	public Matrix mul(double b) {

		double[][] res = new double[row][col];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++) {
				res[i][j] = mg[i][j] * b;
			}
		return new Matrix(res);
	}

	public Matrix add(Matrix b) throws MatrixException {
		if (row != b.row || col != b.col)
			throw new MatrixException("Hai ma tran khong cung kich thuoc");
		double[][] res = new double[row][col];
		double[][] bmg = b.mg;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++)
				res[i][j] = mg[i][j] + bmg[i][j];
		}
		return new Matrix(res);
	}

	public Matrix add(double[][] bmg) throws MatrixException {
		if (row != bmg.length || col != bmg[0].length)
			throw new MatrixException("Hai ma tran khong cung kich thuoc");
		double[][] res = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++)
				res[i][j] = mg[i][j] + bmg[i][j];
		}
		return new Matrix(res);
	}

	private static double[][] invert(double a[][]) {
		int n = a.length;
		double x[][] = new double[n][n];
		double b[][] = new double[n][n];
		int index[] = new int[n];
		for (int i = 0; i < n; ++i)
			b[i][i] = 1;

		// Transform the matrix into an upper triangle
		gaussian(a, index);

		// Update the matrix b[i][j] with the ratios stored
		for (int i = 0; i < n - 1; ++i)
			for (int j = i + 1; j < n; ++j)
				for (int k = 0; k < n; ++k)
					b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];

		// Perform backward substitutions
		for (int i = 0; i < n; ++i) {
			x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
			for (int j = n - 2; j >= 0; --j) {
				x[j][i] = b[index[j]][i];
				for (int k = j + 1; k < n; ++k) {
					x[j][i] -= a[index[j]][k] * x[k][i];
				}
				x[j][i] /= a[index[j]][j];
			}
		}
		return x;
	}

	// Method to carry out the partial-pivoting Gaussian
	// elimination. Here index[] stores pivoting order.

	private static void gaussian(double a[][], int index[]) {
		int n = index.length;
		double c[] = new double[n];

		// Initialize the index
		for (int i = 0; i < n; ++i)
			index[i] = i;

		// Find the rescaling factors, one from each row
		for (int i = 0; i < n; ++i) {
			double c1 = 0;
			for (int j = 0; j < n; ++j) {
				double c0 = Math.abs(a[i][j]);
				if (c0 > c1)
					c1 = c0;
			}
			c[i] = c1;
		}

		// Search the pivoting element from each column
		int k = 0;
		for (int j = 0; j < n - 1; ++j) {
			double pi1 = 0;
			for (int i = j; i < n; ++i) {
				double pi0 = Math.abs(a[index[i]][j]);
				pi0 /= c[index[i]];
				if (pi0 > pi1) {
					pi1 = pi0;
					k = i;
				}
			}

			// Interchange rows according to the pivoting order
			int itmp = index[j];
			index[j] = index[k];
			index[k] = itmp;
			for (int i = j + 1; i < n; ++i) {
				double pj = a[index[i]][j] / a[index[j]][j];

				// Record pivoting ratios below the diagonal
				a[index[i]][j] = pj;

				// Modify other elements accordingly
				for (int l = j + 1; l < n; ++l)
					a[index[i]][l] -= pj * a[index[j]][l];
			}
		}
	}

	public Matrix sub(Matrix b) throws MatrixException {
		if ((row != b.row )|| (col != b.col))
			throw new MatrixException("Hai ma tran khong cung kich thuoc");
		double[][] res = new double[row][col];
		double[][] bmg = b.mg;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++)
				res[i][j] = mg[i][j] - bmg[i][j];
		}
		return new Matrix(res);
	}

	public Matrix sub(double[][] bmg) throws MatrixException {
		if (row != bmg.length || col != bmg[0].length)
			throw new MatrixException("Hai ma tran khong cung kich thuoc");
		double[][] res = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++)
				res[i][j] = mg[i][j] - bmg[i][j];
		}
		return new Matrix(res);
	}

	public void update(Matrix s) {
		mg = s.mg;
	}
}
