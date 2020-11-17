import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.geom.Vec4d;

public class IndexedFace {
	private List<Vec4d> vecs = new ArrayList<Vec4d>();
	private List<int[]> indices = new ArrayList<>();
	private String name;
	
	public void addVec(Vec4d v) {
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

	public List<Vec4d> getVecs() {
		return vecs;
	}

	public List<int[]> getIndices() {
		return indices;
	}

	public String getName() {
		return name;
	}
	
	
}
