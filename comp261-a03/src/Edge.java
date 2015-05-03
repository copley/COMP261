public class Edge {
	
	public final int y;
	public final float x;
	public final float z;
	
	public Edge(int y, float x, float z) {
		this.y = y;
		this.x = x;
		this.z = z;
	}

	@Override
	public String toString() {
		return "Edge [y=" + y + ", x=" + x + ", z=" + z + "]";
	}
	
}