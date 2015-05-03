
public class Light {
	
	public Vector3D direction;
	private double intensity;
	private int red;
	private int green;
	private int blue;
	private final int DEFAULT_INTENSITY = 1;
	
	public Light(Vector3D direction, double intensity, int red, int green, int blue) {
		this.direction = direction;
		this.intensity = intensity;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public Light(Vector3D direction) {
		this.direction = direction;
		this.intensity = DEFAULT_INTENSITY;
		this.red = 255;
		this.green = 255;
		this.blue = 255;
	}

	@Override
	public String toString() {
		return "Light [direction=" + direction + ", intensity=" + intensity + ", red=" + red + ", green=" + green + ", blue=" + blue + "]";
	}

}
