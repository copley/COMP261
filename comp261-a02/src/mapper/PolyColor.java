package mapper;

/**
 * Simple class to contain RGB color information
 * 
 * @author diego
 *
 */
public class PolyColor {

	int red;
	int green;
	int blue;

	public PolyColor(int red, int green, int blue) {
		this.red = red % 255;
		this.green = green % 255;
		this.blue = blue % 255;
	}

	public int getR() {
		return red;
	}

	public int getG() {
		return green;
	}

	public int getB() {
		return blue;
	}
}
