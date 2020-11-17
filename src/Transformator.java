import java.util.ArrayList;
import java.util.List;

import org.ejml.simple.SimpleMatrix;

import com.sun.javafx.geom.Vec4d;

public class Transformator {

	public static SimpleMatrix scale(Vec4d vector, double scaleFactorX, double scaleFactorY) {
		SimpleMatrix scalingMatrix = new SimpleMatrix(
				new double [][] {
					new double[] {scaleFactorX,0,0,0},
					new double[] {0,scaleFactorY,0,0},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		SimpleMatrix scaled = new SimpleMatrix(
				new double [][] {
					new double[] {vector.x},
					new double[] {vector.y},
					new double[] {vector.z},
					new double[] {vector.w}
				});
		return scalingMatrix.mult(scaled);
	}
	
	public static IndexedFace scale(IndexedFace faces, double scaleFactorX, double scaleFactorY) {
		Vec4d[] oldVectors = new Vec4d[faces.getVecs().size()];
		oldVectors = faces.getVecs().toArray(oldVectors);
		IndexedFace newFace = new IndexedFace();
		newFace.addFace(faces.getIndices());
		newFace.setName(faces.getName());
		System.out.println("Scaling factors: " + scaleFactorX + ", " + scaleFactorY);
		SimpleMatrix scalingMatrix = new SimpleMatrix(
				new double [][] {
					new double[] {scaleFactorX,0,0,0},
					new double[] {0,scaleFactorY,0,0},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		
		for(int i=0;i<faces.getVecs().size();i++) {
			SimpleMatrix scaled = new SimpleMatrix(
					new double [][] {
						new double[] {oldVectors[i].x},
						new double[] {oldVectors[i].y},
						new double[] {oldVectors[i].z},
						new double[] {oldVectors[i].w}
					});
			SimpleMatrix newVec = scalingMatrix.mult(scaled);
			newFace.addVec(Transformator.simpleMatrixToVec4d(newVec));
		}
		
		return newFace;
	}
	
	public static SimpleMatrix translate(Vec4d vector, double directionX, double directionY) {
		SimpleMatrix translatingMatrix = new SimpleMatrix(
				new double[][] {
					new double[] {1,0,0,directionX},
					new double[] {0,1,0,directionY},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		SimpleMatrix translated = new SimpleMatrix(
				new double [][] {
					new double[] {vector.x},
					new double[] {vector.y},
					new double[] {vector.z},
					new double[] {vector.w}
				});
		return translatingMatrix.mult(translated);
	}
	
	public static Vec4d simpleMatrixToVec4d(SimpleMatrix matrix) {
		return new Vec4d(matrix.get(0), matrix.get(1), matrix.get(2), matrix.get(3));
		
	}
	
	public static IndexedFace translate(IndexedFace faces, double directionX, double directionY) {
		SimpleMatrix translatingMatrix = new SimpleMatrix(
				new double[][] {
					new double[] {1,0,0,directionX},
					new double[] {0,1,0,directionY},
					new double[] {0,0,1,0},
					new double[] {0,0,0,1}
				});
		Vec4d[] oldVectors = new Vec4d[faces.getVecs().size()];
		oldVectors = faces.getVecs().toArray(oldVectors);
		IndexedFace newFace = new IndexedFace();
		newFace.addFace(faces.getIndices());
		newFace.setName(faces.getName());
		for(int i=0;i<faces.getVecs().size();i++) {
			SimpleMatrix scaled = new SimpleMatrix(
					new double [][] {
						new double[] {oldVectors[i].x},
						new double[] {oldVectors[i].y},
						new double[] {oldVectors[i].z},
						new double[] {oldVectors[i].w}
					});
			SimpleMatrix newVec = translatingMatrix.mult(scaled);
			newFace.addVec(Transformator.simpleMatrixToVec4d(newVec));
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
		Vec4d[] oldVectors = new Vec4d[faces.getVecs().size()];
		oldVectors = faces.getVecs().toArray(oldVectors);
		IndexedFace newFace = new IndexedFace();
		newFace.addFace(faces.getIndices());
		newFace.setName(faces.getName());
		for(int i=0;i<faces.getVecs().size();i++) {
			SimpleMatrix scaled = new SimpleMatrix(
					new double [][] {
						new double[] {oldVectors[i].x},
						new double[] {oldVectors[i].y},
						new double[] {oldVectors[i].z},
						new double[] {oldVectors[i].w}
					});
			SimpleMatrix newVec = rotationMatrix.mult(scaled);
			newFace.addVec(Transformator.simpleMatrixToVec4d(newVec));
		}
		
		return newFace;
	}
}
