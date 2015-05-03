import java.awt.Color;

public class Edge {

	public final int y;
	public final float x;
	public final float z;
	public final Color color;

	public Edge(int y, float x, float z, Color color) {

		this.y = y;
		this.x = x;
		this.z = z;
		this.color = color;
	}

	@Override
	public String toString() {

		return "Edge [y=" + y + ", x=" + x + ", z=" + z + ", color=" + color + "]";
	}
}