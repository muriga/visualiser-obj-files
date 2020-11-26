

public class MyMatrix {
	private double[][] matrix;
	
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

		//System.out.println("old: " + vector[0] + ", " + vector[1]);
		//System.out.println("new: " + newVector[0] + ", " + newVector[1]);
		return new MyVec(newVector);
	}
	
	public MyMatrix multiply(MyMatrix otherMyMatrix) {
		double [][] otherMatrix = otherMyMatrix.getMatrix();
		int m = this.matrix[0].length;
		int n = this.matrix.length;
		double [][] newMatrix = new double[m][n];
		//if (m != otherMatrix.length) {
		//	throw new Exception("You cant multiply matrices with this size");
		//}
		for (int i=0; i < m;i++) {
			for(int j=0; j<n;j++) {
				newMatrix[i][j] = 0;
				for(int k=0; k<m;j++)
				{
					newMatrix[i][j] += this.matrix[i][k] * otherMatrix[k][j];
					
				}
			}
		}
		//System.out.println("old: " + this.matrix);
		//System.out.println("new: " + newMatrix);
		return new MyMatrix(newMatrix);
	}
}
