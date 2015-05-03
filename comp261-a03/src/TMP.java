import java.awt.Color;
import java.util.Scanner;

public class Polygon {

	private Vector3D[] vertices = new Vector3D[3];
	private Color reflectivity;
	private Vector3D normal;
	private boolean facing = true;
	private Bounds bounds;
	private Color colour;

	public Polygon(String s) {

		Scanner sc = new Scanner(s);
		for (int v = 0; v < 3; v++) {
			vertices[v] = new Vector3D(sc.nextFloat(), sc.nextFloat(), sc.nextFloat());
		}
		reflectivity = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());

		calculateNormal();
		sc.close();
	}

	public int getMinY() {

		return Math.round(bounds.getY());
	}

	// Calculates the bounds of the polygon here
	private Bounds bounds() {

		float minX = Math.min(Math.min(vertices[0].x, vertices[1].x), vertices[2].x);
		float minY = Math.min(Math.min(vertices[0].y, vertices[1].y), vertices[2].y);
		float maxX = Math.max(Math.max(vertices[0].x, vertices[1].x), vertices[2].x);
		float maxY = Math.max(Math.max(vertices[0].y, vertices[1].y), vertices[2].y);
		bounds = new Bounds(minX, minY, (maxX - minX), (maxY - minY));
		return bounds;
	}

	// Updates/calculates the normal of polygon

	private void calculateNormal() {

		normal = ((vertices[1].minus(vertices[0])).crossProduct(vertices[2].minus(vertices[1]))).unitVector();

		// update the facing boolean
		if (normal.z > 0)
			facing = false;
		else
			facing = true;
	}

	// Calculates the shading of this polygon

	public void shading(Vector3D lightSource, float ambientLight) {

		float reflect = ambientLight;
		if (normal.cosTheta(lightSource) > 0)
			reflect = ambientLight + normal.cosTheta(lightSource);

		int r = checkColourRange((int) (reflectivity.getRed() * reflect));
		int g = checkColourRange((int) (reflectivity.getGreen() * reflect));
		int b = checkColourRange((int) (reflectivity.getBlue() * reflect));

		colour = new Color(r, g, b);
	}

	// ensure the range of values for rgb are between 0 and 255 .getRGB() method return BIT binary number in other classes*

	private int checkColourRange(int x) {

		if (x <= 0)
			x = 0;

		if (x >= 255)
			x = 255;

		return x;
	}

	public boolean facing() {

		return facing;
	}

	public Color getColour() {

		return colour;
	}

	public String toString() {

		StringBuilder sb = new StringBuilder("Poly: ");
		for (int i = 0; i < 3; i++) {
			sb.append(vertices[i].toString()).append("\t");
		}
		sb.append(reflectivity).append("\t");
		sb.append(normal.toString()).append("\t");
		sb.append(facing);

		return sb.toString();
	}

	public EdgeList[] edgeList() {

		bounds();
		EdgeList[] e = new EdgeList[(int) (bounds.getHeight() + 1)];

		for (int i = 0; i < 3; i++) {
			Vector3D verticesA = vertices[i];
			Vector3D verticesB = vertices[(i + 1) % 3];
			if (verticesA.y > verticesB.y) {
				verticesB = verticesA;
				verticesA = vertices[(i + 1) % 3];
			}
			float mx = (verticesB.x - verticesA.x) / (verticesB.y - verticesA.y);
			float mz = (verticesB.z - verticesA.z) / (verticesB.z - verticesA.z);
			float x = verticesA.x;
			float z = verticesA.z;

			int j = Math.round(verticesA.y) - Math.round(bounds.getY());
			int maxj = Math.round(verticesB.y) - Math.round(bounds.getY());

			while (j < maxj) {
				if (e[j] == null) {
					e[j] = new EdgeList(x, z);
				} else {
					e[j].add(x, z);

				}
				j++;
				x += mx;
				z += mz;
			}

		}
		return e;

	}
}