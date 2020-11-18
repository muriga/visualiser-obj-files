import org.ejml.simple.SimpleMatrix;


public class Transformator {
	
	public static IndexedFace scale(IndexedFace faces, double scaleFactorX, double scaleFactorY) {
		SimpleMatrix[] oldVectors = new SimpleMatrix[faces.getVecs().size()];
		oldVectors = faces.getVecs().toArray(oldVectors);
		
		IndexedFace newFace = new IndexedFace();
		newFace.addFace(faces.getIndices());
		newFace.setName(faces.getName());
		
		SimpleMatrix scalingMatrix = new SimpleMatrix(
				new double [][] {
					new double[] {scaleFactorX,0,0,0},
					new double[] {0,scaleFactorY,0,0},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		
		for(int i=0;i<faces.getVecs().size();i++) {
			SimpleMatrix newVec = scalingMatrix.mult(oldVectors[i]);
			newFace.addVec(newVec);
		}
		
		return newFace;
	}
	
	public static IndexedFace translate(IndexedFace faces, double directionX, double directionY) {
		SimpleMatrix translatingMatrix = new SimpleMatrix(
				new double[][] {
					new double[] {1,0,0,directionX},
					new double[] {0,1,0,directionY},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		
		SimpleMatrix[] oldVectors = new SimpleMatrix[faces.getVecs().size()];
		oldVectors = faces.getVecs().toArray(oldVectors);
		
		IndexedFace newFace = new IndexedFace();
		newFace.addFace(faces.getIndices());
		newFace.setName(faces.getName());
		
		for(int i=0;i<faces.getVecs().size();i++) {
			SimpleMatrix newVec = translatingMatrix.mult(oldVectors[i]);
			newFace.addVec(newVec);
		}
		
		return newFace;
	}
	
	public static IndexedFace rotate(IndexedFace faces, double degrees) {
		double radians = Math.toRadians(degrees);
		double sinus = Math.sin(radians);
		double cosinus = Math.cos(radians);
		SimpleMatrix rotationMatrix = new SimpleMatrix(
				new double[][] {
					new double[] {cosinus,-sinus,0,0},
					new double[] {sinus,cosinus,0,0},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		
		SimpleMatrix[] oldVectors = new SimpleMatrix[faces.getVecs().size()];
		oldVectors = faces.getVecs().toArray(oldVectors);
		
		IndexedFace newFace = new IndexedFace();
		newFace.addFace(faces.getIndices());
		newFace.setName(faces.getName());
		
		for(int i=0;i<faces.getVecs().size();i++) {
			SimpleMatrix newVec = rotationMatrix.mult(oldVectors[i]);
			newFace.addVec(newVec);
		}
		
		return newFace;
	}
}
