import java.awt.Color;

public class Pixel {

	public int x = 0;
	public int y = 0;
	public float z = Integer.MAX_VALUE;
	public Color color = Color.BLACK;

	public Pixel(int x, int y) {

		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Pixel other = (Pixel) obj;
		if (x != other.x) return false;
		if (y != other.y) return false;
		return true;
	}

	@Override
	public String toString() {

		return "Pixel [x=" + x + ", y=" + y + ", z=" + z + ", colour=" + color + "]";
	}
}