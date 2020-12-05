public class Transformator {
	
	public static IndexedFace scale(IndexedFace faces, double scaleFactorX, double scaleFactorY) {
		MyVec[] oldVectors = new MyVec[faces.getVecs().size()];
		oldVectors = faces.getVecs().toArray(oldVectors);
		
		IndexedFace newFace = new IndexedFace();
		newFace.addFace(faces.getIndices());
		newFace.setName(faces.getName());
		
		MyMatrix scalingMatrix = new MyMatrix(
				new double [][] {
					new double[] {scaleFactorX,0,0,0},
					new double[] {0,scaleFactorY,0,0},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		
		for(int i=0;i<faces.getVecs().size();i++) {
			MyVec newVec = scalingMatrix.multiply(oldVectors[i]);
			newFace.addVec(newVec);
		}
		
		return newFace;
	}
	
	public static IndexedFace translate(IndexedFace faces, double directionX, double directionY) {
		MyMatrix translatingMatrix = new MyMatrix(
				new double[][] {
					new double[] {1,0,0,directionX},
					new double[] {0,1,0,directionY},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		
		MyVec[] oldVectors = new MyVec[faces.getVecs().size()];
		oldVectors = faces.getVecs().toArray(oldVectors);
		
		IndexedFace newFace = new IndexedFace();
		newFace.addFace(faces.getIndices());
		newFace.setName(faces.getName());
		
		for(int i=0;i<faces.getVecs().size();i++) {
			MyVec newVec = translatingMatrix.multiply(oldVectors[i]);
			newFace.addVec(newVec);
		}
		
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
	
	public static IndexedFace rotate(IndexedFace faces, char axis, double radians) {
		
		MyMatrix rotationMatrix = Transformator.getRotationMatrix(axis, radians);
		
		MyVec[] oldVectors = new MyVec[faces.getVecs().size()];
		oldVectors = faces.getVecs().toArray(oldVectors);
		
		IndexedFace newFace = new IndexedFace();
		newFace.addFace(faces.getIndices());
		newFace.setName(faces.getName());
		
		for(int i=0;i<faces.getVecs().size();i++) {
			MyVec newVec = rotationMatrix.multiply(oldVectors[i]);
			newFace.addVec(newVec);
		}
		
		return newFace;
	}
}
