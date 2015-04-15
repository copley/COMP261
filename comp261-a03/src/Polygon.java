import java.awt.Color;

public class Polygon {

	private Vertex[] vertices = new Vertex[3];
	private final int red;
	private final int green;
	private final int blue;

	private Vector3D normal;
	private Model model;

	private Color color;

	public Polygon(Vertex p1, Vertex p2, Vertex p3, int r, int g, int b, Model m) {
		this.model = m;
		this.vertices[0] = p1;
		this.vertices[1] = p2;
		this.vertices[2] = p3;
		this.red = r;
		this.green = g;
		this.blue = b;
		this.color = new Color(red, green, blue);
	}

	public Polygon(String line) { // constructor form line
		String[] vals = line.split(" ");
		this.vertices[0] = new Vertex(Double.parseDouble(vals[0]), Double.parseDouble(vals[1]), Double.parseDouble(vals[2]));
		this.vertices[1] = new Vertex(Double.parseDouble(vals[3]), Double.parseDouble(vals[4]), Double.parseDouble(vals[5]));
		this.vertices[2] = new Vertex(Double.parseDouble(vals[6]), Double.parseDouble(vals[7]), Double.parseDouble(vals[8]));
		this.red = Integer.parseInt(vals[9]);
		this.green = Integer.parseInt(vals[10]);
		this.blue = Integer.parseInt(vals[11]);
		this.color = new Color(red, green, blue);
	}
	
	// TRANSLATION
		public void translate(int x, int y) {
			this.vertices[0] = new Vertex(this.vertices[0].x + x, this.vertices[0].y + y, this.vertices[0].z);
			this.vertices[1] = new Vertex(this.vertices[1].x + x, this.vertices[1].y + y, this.vertices[1].z);
			this.vertices[2] = new Vertex(this.vertices[2].x + x, this.vertices[2].y + y, this.vertices[2].z);
//			translationRefresh();
		}

		public void zoom(float rate) {
			this.vertices[0] = new Vertex(this.vertices[0].x * rate, this.vertices[0].y * rate, this.vertices[0].z * rate);
			this.vertices[1] = new Vertex(this.vertices[1].x * rate, this.vertices[1].y * rate, this.vertices[1].z * rate);
			this.vertices[2] = new Vertex(this.vertices[2].x * rate, this.vertices[2].y * rate, this.vertices[2].z * rate);
//			translationRefresh();
		}


//		public void rotateXaxis(double cosTheta, double sinTheta) {
//			coords[0] = new Coord(coords[0].x, (coords[0].y * cosTheta - coords[0].z * sinTheta), (coords[0].y * sinTheta + coords[0].z * cosTheta));
//			coords[1] = new Coord(coords[1].x, (coords[1].y * cosTheta - coords[1].z * sinTheta), (coords[1].y * sinTheta + coords[1].z * cosTheta));
//			coords[2] = new Coord(coords[2].x, (coords[2].y * cosTheta - coords[2].z * sinTheta), (coords[2].y * sinTheta + coords[2].z * cosTheta));
//			totalRefresh();
//		}
//
//		public void rotateYaxis(double cosTheta, double sinTheta) {
//			coords[0] = new Coord((coords[0].x * cosTheta - coords[0].z * sinTheta), (coords[0].y), (coords[0].x * sinTheta + coords[0].z * cosTheta));
//			coords[1] = new Coord((coords[1].x * cosTheta - coords[1].z * sinTheta), (coords[1].y), (coords[1].x * sinTheta + coords[1].z * cosTheta));
//			coords[2] = new Coord((coords[2].x * cosTheta - coords[2].z * sinTheta), (coords[2].y), (coords[2].x * sinTheta + coords[2].z * cosTheta));
//			totalRefresh();
//		}

	public Color getColor() {
		return color;
	}
	
}
