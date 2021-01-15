
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
	
	public double dot(MyVec otherVec) {
		double [] a = this.getVector();
		double [] b = otherVec.getVector();
		double sum = 0;
		for(int i=0;i<a.length;i++) {
			sum += a[i] + b[i];
		}
		return sum;
	}
	
	public MyVec cross(MyVec otherVec) {
		double [] a = this.getVector();
		double [] b = otherVec.getVector();
		return new MyVec(a[1] * b[2] - a[2] * b[1],
				a[2] * b[0] - a[0] * b[2],
				a[0] * b[1] - a[1] * b[0],
				0);				
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
