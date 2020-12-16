

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
		//if (m != vector.length) {
		//	throw new Exception("You cant multiply matrix and vector with this size");
		//}
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
		//System.out.println("Multiply:");
		//System.out.println(this);
		//System.out.println();
		//System.out.println(otherMyMatrix);
		int m = this.matrix[0].length;
		int n = this.matrix.length;
		double [][] newMatrix = new double[m][n];
		//if (m != otherMatrix.length) {
		//	throw new Exception("You cant multiply matrices with this size");
		//}
		for (int i=0; i < m;i++) {
			for(int j=0; j<n;j++) {
				newMatrix[i][j] = 0;
				for(int k=0; k<m;k++)
				{
					try {
						newMatrix[i][j] += this.matrix[i][k] * otherMatrix[k][j];
					} catch (Exception e) {
						System.out.println(i + " " + j + " " + k);
					}
					
				}
			}
		}
		//System.out.println("Result:");
		//System.out.println(new MyMatrix(newMatrix));
		return new MyMatrix(newMatrix);
	}
}
