import java.util.ArrayList;
import java.util.List;

public class IndexedFace implements Cloneable{
	private List<MyVec> vecs = new ArrayList<MyVec>();
	private List<int[]> indices = new ArrayList<>();
	private String name;
	private double[] translation = {0,0};
	
	public IndexedFace clone() {
		try {
			return (IndexedFace) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Cloning failed.");
			return null;
		}
	}
	
	public void addVec(MyVec v) {
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

	public List<MyVec> getVecs() {
		return vecs;
	}

	public List<int[]> getIndices() {
		return indices;
	}

	public String getName() {
		return name;
	}
	
	public void actualizeTranslation(IndexedFace oldFace, double x, double y) {
		translate(x + oldFace.translation[0], y + oldFace.translation[1]);
	}
	
	private void translate(double x, double y) {

		translation[0] += x;
		translation[1] += y;
	}
	
	public double[] getTranslation() {
		return translation;
	}
	
}
