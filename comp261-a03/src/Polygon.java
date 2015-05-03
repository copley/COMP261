import java.awt.Color;
import java.util.*;

public class Polygon {

	public Vector3D[] vertices = new Vector3D[3];
	public Vector3D[] verticesOrigin = new Vector3D[3];
	private Main rend = null;
	public Vector3D minVector = null;
	public Vector3D maxVector = null;
	private Vector3D surfaceNorm = null;
	private Color color = null;
	public Color colorOut = null; // surface color
	public boolean hidden = false;
	private double shinynessExponent = 10;

	public Polygon(Vector3D[] vertices, Color colour, Main rend) {

		this.rend = rend;
		this.color = colour;
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
//			float costh = normal.dotProduct(lightDir) * rend.gamma;
			// COMPUTE DIFFUSE COMPONENT
			float costh = normal.dotProduct(lightDir);
			redDiff =+ costh * redDiff;
			greenDiff =+ costh * greenDiff;
			blueDiff =+ costh * blueDiff;
			// COMPUTE SPECULAR COMPONENT -- FOR PHONG IMPLEMENTATION
			Vector3D beta = normal.mult(2*costh);
			Vector3D mirroredLight = lightDir.minus(beta).unitVector();
			if (mirroredLight.z < 0) mirroredLight.z = 0;
			specular =+ (float) Math.pow(mirroredLight.z, shinynessExponent);
		}
		// CLAMP THE OUTPUT BETWEEN 0-1
		float redOut   = Math.max(0, Math.min(ambRed + redDiff   + redDiff*specular, 1));
		float greenOut = Math.max(0, Math.min(ambGreen + greenDiff + greenDiff*specular, 1));
		float blueOut  = Math.max(0, Math.min(ambBlue + blueDiff  + blueDiff*specular, 1));

		this.colorOut = new Color(redOut, greenOut, blueOut);
	}

	public HashMap<Integer, EdgeBounds> getEdgeList() {

		HashMap<Integer, EdgeBounds> edgeList = new HashMap<Integer, EdgeBounds>();
		for (int i = 0; i < vertices.length; i++) {
			Vector3D first = vertices[i];
			Vector3D second = null, temp = first;
			if (i == (vertices.length - 1)) {
				second = vertices[0];
			} else {
				second = vertices[i + 1];
			}
			first = (first.y < second.y) ? first : second;
			second = (temp.y > second.y) ? temp : second;
			float x = first.x;
			float z = first.z;
			float changeX = (second.x - first.x) / (second.y - first.y);
			float changeZ = (second.z - first.z) / (second.y - first.y);
			int yPosition = Math.round(first.y);
			int end = Math.round(second.y);
			EdgeBounds leftRight = new EdgeBounds();
			while (yPosition < end) {
				leftRight = new EdgeBounds();
				if (edgeList.containsKey(yPosition)) {
					leftRight = edgeList.get(yPosition);
				}
				Edge edge = new Edge(yPosition, x, z);
				if (edge.x < leftRight.getLeft().x) {
					leftRight.setLeft(edge);
				}
				if (edge.x > leftRight.getRight().x) {
					leftRight.setRight(edge);
				}
				edgeList.put(yPosition, leftRight);
				yPosition++;
				x = x + changeX;
				z = z + changeZ;
			}
		}
		return edgeList;
	}

	public void applyTransformation(Transform trans) {

		for (int v = 0; v < 3; v++) {
			this.vertices[v] = trans.multiply(verticesOrigin[v]);
		}
		// calculate normals
		computeNormals();
	}

	public void computeNormals() {

		// set min and max Y for edgelist calc
		Vector3D minY = new Vector3D(0, Integer.MAX_VALUE, 0);
		Vector3D maxY = new Vector3D(0, Integer.MIN_VALUE, 0);
		for (Vector3D vec : vertices) {
			if (vec.y < minY.y) {
				minY = vec;
			}
			if (vec.y > maxY.y) {
				maxY = vec;
			}
		}
		minVector = minY;
		maxVector = maxY;
		// Computes the normals
		Vector3D first = vertices[1].minus(vertices[0]);
		Vector3D second = vertices[2].minus(vertices[1]);
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