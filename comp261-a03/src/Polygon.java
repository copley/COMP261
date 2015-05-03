import java.awt.Color;
import java.util.*;

public class Polygon {

	public Vertex[] vertices = new Vertex[3];
	public Vertex[] verticesOrigin = new Vertex[3];
	public Main rend = null;
	public Vector3D minVector = null;
	public Vector3D maxVector = null;
	public Vector3D surfaceNorm = null;
	public Color color = null;
	public Color colorOut = null; // surface color
	public boolean hidden = false;

	// private double specularPower = 10;
	public Polygon(Vertex[] vertices, Color color, Main rend) {

		this.rend = rend;
		this.color = color;
		this.vertices = vertices;
		this.verticesOrigin = vertices;
		computeNormals();
	}

	private void computeColour() {

		// Initialise all the incoming and outgoing vars
		int[] ambLight = rend.getAmbientLight();
		float ambient = rend.ambentIntensity;
		Vector3D normal = surfaceNorm.unitVector();
		float specular = 0;
		float redDiff = color.getRed() / 255f;
		float greenDiff = color.getGreen() / 255f;
		float blueDiff = color.getBlue() / 255f;
		float ambRed = ambLight[0] / 255f * ambient;
		float ambGreen = ambLight[1] / 255f * ambient;
		float ambBlue = ambLight[2] / 255f * ambient;
		// Loop though all lights
		for (Vector3D light : rend.ligths) {
			Vector3D lightDir = light.unitVector();
			// COMPUTE DIFFUSE COMPONENT
			float costh = normal.dotProduct(lightDir);
			redDiff = +costh * redDiff;
			greenDiff = +costh * greenDiff;
			blueDiff = +costh * blueDiff;
			// COMPUTE SPECULAR COMPONENT -- FOR PHONG IMPLEMENTATION
			Vector3D beta = normal.mult(2 * costh);
			Vector3D mirroredLight = lightDir.minus(beta).unitVector();
			if (mirroredLight.z < 0) mirroredLight.z = 0;
			specular = specular + (float) Math.pow(mirroredLight.z, rend.getSpecPow());
		}
		// CLAMP THE OUTPUT BETWEEN 0-1
		float redOut = Math.max(0, Math.min(ambRed + redDiff + redDiff * specular, 1));
		float greenOut = Math.max(0, Math.min(ambGreen + greenDiff + greenDiff * specular, 1));
		float blueOut = Math.max(0, Math.min(ambBlue + blueDiff + blueDiff * specular, 1));
		this.colorOut = new Color(redOut, greenOut, blueOut);
	}

	public Map<Integer, EdgeBounds> getEdgeList() {
		
		HashMap<Integer, EdgeBounds> edgeList = new HashMap<Integer, EdgeBounds>();
		for (int i = 0; i < vertices.length; i++) {
			Vertex first = vertices[i];
			Vertex second = null, temp = first;
			if (i == (vertices.length - 1)) {
				second = vertices[0];
			} else {
				second = vertices[i + 1];
			}
			first = (first.vector.y < second.vector.y) ? first : second;
			second = (temp.vector.y > second.vector.y) ? temp : second;
			
			float x = first.vector.x;
			float z = first.vector.z;
			
			int r = first.colorOut.getRed();
			int g = first.colorOut.getGreen();
			int b = first.colorOut.getBlue();
			
			float deltaX = (second.vector.x - first.vector.x) / (second.vector.y - first.vector.y);
			float deltaZ = (second.vector.z - first.vector.z) / (second.vector.y - first.vector.y);
			float deltaR = (second.colorOut.getRed() - first.colorOut.getRed()) / (second.vector.y - first.vector.y);
			float deltaG = (second.colorOut.getGreen() - first.colorOut.getGreen()) / (second.vector.y - first.vector.y);
			float deltaB = (second.colorOut.getBlue() - first.colorOut.getBlue()) / (second.vector.y - first.vector.y);

			int yPosition = Math.round(first.vector.y);
			int end = Math.round(second.vector.y);
			EdgeBounds leftRight = new EdgeBounds();
			while (yPosition < end) {
				leftRight = new EdgeBounds();
				if (edgeList.containsKey(yPosition)) {
					leftRight = edgeList.get(yPosition);
				}
				Edge edge = new Edge(yPosition, x, z, new Color(r, g, b));
//				System.out.println("NEW EDGE COLOR: "+r+g+b);

				if (edge.x < leftRight.getLeft().x) {
					leftRight.setLeft(edge);
				}
				if (edge.x > leftRight.getRight().x) {
					leftRight.setRight(edge);
				}
				edgeList.put(yPosition, leftRight);
				yPosition++;
				x = x + deltaX;
				z = z + deltaZ;
				r = (int) (r + deltaR);
				g = (int) (g + deltaG);
				b = (int) (b + deltaB);
			}
		}
		return edgeList;
	}

	public void applyTransformation(Transform trans) {

		for (int v = 0; v < 3; v++) {
			this.vertices[v].vector = trans.multiply(verticesOrigin[v].vector);
		}
		// calculate normals
		computeNormals();
	}

	public void computeNormals() {

		// set min and max Y for edgelist calc
		Vector3D minY = new Vector3D(0, Integer.MAX_VALUE, 0);
		Vector3D maxY = new Vector3D(0, Integer.MIN_VALUE, 0);
		for (Vertex vertex : vertices) {
			if (vertex.vector.y < minY.y) {
				minY = vertex.vector;
			}
			if (vertex.vector.y > maxY.y) {
				maxY = vertex.vector;
			}
		}
		minVector = minY;
		maxVector = maxY;
		// Computes the normals
		Vector3D first = vertices[1].vector.minus(vertices[0].vector);
		Vector3D second = vertices[2].vector.minus(vertices[1].vector);
		surfaceNorm = first.crossProduct(second);
		if (surfaceNorm.z > 0) {
			hidden = true;
		} else {
			hidden = false;
		}
		computeColour();
	}

	@Override
	public String toString() {

		return "Polygon [vectors=" + vertices + ", colour=" + colorOut + "]";
	}
}