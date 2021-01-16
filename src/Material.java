
public class Material {
	private double ka;
	private double kd;
	private double ks;
	private double shininess;
	private double color_R;
	private double color_G;
	private double color_B;
	
	public Material() {
		this.ka = 0.8;
		this.kd = 2.7;
		this.ks = 1.5;
		this.shininess = 1;
		this.color_R = 0;
		this.color_G = 0;
		this.color_B = 0.75;
	}

	public double getKa() {
		return ka;
	}

	public void setKa(double ka) {
		this.ka = ka;
	}

	public double getKd() {
		return kd;
	}

	public void setKd(double kd) {
		this.kd = kd;
	}

	public double getKs() {
		return ks;
	}

	public void setKs(double ks) {
		this.ks = ks;
	}

	public double getShininess() {
		return shininess;
	}

	public void setShininess(double shininess) {
		this.shininess = shininess;
	}

	public double getColor_R() {
		return color_R;
	}

	public void setColor_R(double color_R) {
		this.color_R = color_R;
	}

	public double getColor_G() {
		return color_G;
	}

	public void setColor_G(double color_G) {
		this.color_G = color_G;
	}

	public double getColor_B() {
		return color_B;
	}

	public void setColor_B(double color_B) {
		this.color_B = color_B;
	}

}
