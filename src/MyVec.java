
public class MyVec {
	private double x;
	private double y;
	private double z;
	private double w;
	
	public MyVec(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public MyVec(double[] array) {
		this.x = array[0];
		this.y = array[1];
		this.z = array[2];
		this.w = array[3];
	}
	
	public String toString() {
		return x + " " + y + " " + " " + z;
	}
	
	public double[] getVector() {
		double [] array = {this.x, this.y, this.z, this.w};
		return array;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}
	
	
	
}
