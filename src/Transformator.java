public class Transformator {

	public static MyMatrix translate(MyMatrix matrix, double directionX, double directionY) {
		MyMatrix translatingMatrix = getTranslatingMatrix(directionX, directionY);
		return translatingMatrix.multiply(matrix);
	}

	public static MyVec translate(MyVec vector, double directionX, double directionY) {
		MyMatrix translatingMatrix = getTranslatingMatrix(directionX, directionY);
		return translatingMatrix.multiply(vector);
	}
	
	public static MyMatrix scale(MyMatrix matrix, double scaleFactorX, double scaleFactorY, double scaleFactorZ) {
		double[] translation = extractTranslation(matrix);
		if(needTranslation(translation))
			matrix = Transformator.translate(matrix, -translation[0], -translation[1]);
		
		MyMatrix scalingMatrix = getScalingMatrix(scaleFactorX, scaleFactorY, scaleFactorZ);
		
		matrix = scalingMatrix.multiply(matrix);
		if(needTranslation(translation))
			matrix = Transformator.translate(matrix, translation[0], translation[1]);
		return matrix;
	}
	
	public static MyVec scale(MyVec vector, double scaleFactorX, double scaleFactorY, double scaleFactorZ) {
		MyMatrix scalingMatrix = getScalingMatrix(scaleFactorX, scaleFactorY, scaleFactorZ);
		return scalingMatrix.multiply(vector);
	}
	public static MyMatrix rotate(MyMatrix matrix, char axis, double radians) {
		double[] translation = extractTranslation(matrix);
		if(needTranslation(translation))
			matrix = Transformator.translate(matrix, -translation[0], -translation[1]);
		
		MyMatrix rotationMatrix = Transformator.getRotationMatrix(axis, radians);
		matrix = rotationMatrix.multiply(matrix);
		
		if(needTranslation(translation))
			matrix = Transformator.translate(matrix, translation[0], translation[1]);
		return matrix;
	}
	
	public static MyVec rotate(MyVec vector, char axis, double radians) {
		MyMatrix rotationMatrix = Transformator.getRotationMatrix(axis, radians);
		return rotationMatrix.multiply(vector);
	}
	
	public static IndexedFace transform(IndexedFace oldFace, MyMatrix matrix) {
		MyVec[] oldVectors = new MyVec[oldFace.getVecs().size()];
		oldVectors = oldFace.getVecs().toArray(oldVectors);
		
		IndexedFace newFace = newFaceInit(oldFace);
		
		for(int i=0;i<oldFace.getVecs().size();i++) {
			MyVec newVec = matrix.multiply(oldVectors[i]);
			newFace.addVec(newVec);
		}
		return newFace;
	}
	
	private static double[] extractTranslation(MyMatrix matrix) {
		double[][] matrix_double = matrix.getMatrix();
		double[] translation = new double[] {matrix_double[0][3], matrix_double[1][3], matrix_double[2][3]};
		return translation;
	}
	
	private static boolean needTranslation(double[] translation) {
		return translation[0] != 0 || translation[1] != 0 || translation[2] != 0;
	}
	
	private static IndexedFace newFaceInit(IndexedFace oldFace) {

		IndexedFace newFace = new IndexedFace();
		newFace.addFace(oldFace.getIndices());
		newFace.setName(oldFace.getName());
		return newFace;
	}
	
	private static MyMatrix getRotationMatrix(char axis, double radians) {
		double sinus = Math.sin(radians);
		double cosinus = Math.cos(radians);
		if(Character.compare(axis, 'x') == 0) {
			return  new MyMatrix(
					new double[][] {
						new double[] {1,0,0,0},
						new double[] {0,cosinus,-sinus,0},
						new double[] {0,sinus,cosinus,0},
						new double[] {0,0,0,1}
					});
		}
		else if(Character.compare(axis, 'y') == 0) {
			 return new MyMatrix(
						new double[][] {
							new double[] {cosinus,0,sinus,0},
							new double[] {0,1,0,0},
							new double[] {-sinus,0,cosinus,0},
							new double[] {0,0,0,1}
						});
		}
		else if(Character.compare(axis, 'z') == 0) {
			 return new MyMatrix(
						new double[][] {
							new double[] {cosinus,-sinus,0,0},
							new double[] {sinus,cosinus,0,0},
							new double[] {0,0,1,0},
							new double[] {0,0,0,1}
						});
		}
		else return null;
	}
	
	private static MyMatrix getScalingMatrix(double scaleFactorX, double scaleFactorY, double scaleFactorZ) {
		return new MyMatrix(
				new double [][] {
					new double[] {scaleFactorX,0,0,0},
					new double[] {0,scaleFactorY,0,0},
					new double[] {0,0,scaleFactorZ,0},
					new double[] {0,0,0,1}
				});
	}
	
	private static MyMatrix getTranslatingMatrix(double directionX, double directionY) {
		return new MyMatrix(
				new double[][] {
					new double[] {1,0,0,directionX},
					new double[] {0,1,0,directionY},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
	}
}
