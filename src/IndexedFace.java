import java.util.ArrayList;
import java.util.List;

import org.ejml.simple.SimpleMatrix;

public class IndexedFace {
	private List<SimpleMatrix> vecs = new ArrayList<SimpleMatrix>();
	private List<int[]> indices = new ArrayList<>();
	private String name;
	
	public void addVec(SimpleMatrix v) {
		vecs.add(v);
	}
	
	public void addFace(int v1, int v2, int v3) {
		int[] face = {v1,v2,v3};
		indices.add(face);
	}
	
	public void addFace(List<int[]> faces) {
		indices.addAll(faces);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<SimpleMatrix> getVecs() {
		return vecs;
	}

	public List<int[]> getIndices() {
		return indices;
	}

	public String getName() {
		return name;
	}
	
	
}
