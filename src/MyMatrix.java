

public class MyMatrix {
	private double[][] matrix;
	
	public String toString() {
		return matrix[0][0] + " " + matrix[0][1] + " " + matrix[0][2] + " " + matrix[0][3] + "\n" +
				matrix[1][0] + " " + matrix[1][1] + " " + matrix[1][2] + " " + matrix[1][3] + "\n" +
				matrix[2][0] + " " + matrix[2][1] + " " + matrix[2][2] + " " + matrix[2][3] + "\n" +
				matrix[3][0] + " " + matrix[3][1] + " " + matrix[3][2] + " " + matrix[3][3];
	}
	
	public MyMatrix(double[][] nums) {
		this.matrix = nums;
	}
	
	public double[][] getMatrix() {
		return this.matrix;
	}
	
	public MyVec multiply(MyVec vec) {
		double []  vector = vec.getVector();
		int m = this.matrix[0].length;
		int n = this.matrix.length;
		double [] newVector = new double[m];
		for (int i=0; i < m;i++) {
			newVector[i] = 0;
			for(int j=0; j<n;j++) {
				newVector[i] += matrix[i][j] * vector[j];
			}
		}

		return new MyVec(newVector);
	}
	
	public MyMatrix multiply(MyMatrix otherMyMatrix) {
		double [][] otherMatrix = otherMyMatrix.getMatrix();
		int m = this.matrix[0].length;
		int n = this.matrix.length;
		double [][] newMatrix = new double[m][n];
		for (int i=0; i < m;i++) {
			for(int j=0; j<n;j++) {
				newMatrix[i][j] = 0;
				for(int k=0; k<m;k++)
					newMatrix[i][j] += this.matrix[i][k] * otherMatrix[k][j];
			}
		}
		return new MyMatrix(newMatrix);
	}
}
