import java.awt.Color;
import java.util.*;

public class Polygon {

<<<<<<< HEAD
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
=======
	private Vector3D[] vertices = new Vector3D[3];
	private final int red;
	private final int green;
	private final int blue;
	private Bounds bounds;
	private Vector3D normal;
	private Model model;

	private Color color;

//	public Polygon(Vertex p1, Vertex p2, Vertex p3, int r, int g, int b, Model m) {
//		this.model = m;
//		this.vertices[0] = p1;
//		this.vertices[1] = p2;
//		this.vertices[2] = p3;
//		this.red = r;
//		this.green = g;
//		this.blue = b;
//		this.color = new Color(red, green, blue);
//	}
	
	

	public Polygon(String line) { // constructor form line
		System.out.println("Adding new polygon triangle");
		String[] vals = line.split(" ");
		this.vertices[0] = new Vector3D(Float.parseFloat(vals[0]), Float.parseFloat(vals[1]), Float.parseFloat(vals[2]));
		this.vertices[1] = new Vector3D(Float.parseFloat(vals[3]), Float.parseFloat(vals[4]), Float.parseFloat(vals[5]));
		this.vertices[2] = new Vector3D(Float.parseFloat(vals[6]), Float.parseFloat(vals[7]), Float.parseFloat(vals[8]));
		System.out.println(this.vertices[0].toString()+"\t"+this.vertices[1].toString()+"\t"+this.vertices[2].toString());
		this.red = Integer.parseInt(vals[9]);
		this.green = Integer.parseInt(vals[10]);
		this.blue = Integer.parseInt(vals[11]);
		this.color = new Color(red, green, blue);
	}
	
	/*
	 * Calculates the bounds of the polygon
	 */
	private Bounds bounds() {
		float minX = Math.min(Math.min(vertices[0].x, vertices[1].x),vertices[2].x);
		float minY = Math.min(Math.min(vertices[0].y, vertices[1].y),vertices[2].y);
		float maxX = Math.max(Math.max(vertices[0].x, vertices[1].x),vertices[2].x);
		float maxY = Math.max(Math.max(vertices[0].y, vertices[1].y),vertices[2].y);
		bounds = new Bounds(minX, minY, (maxX - minX), (maxY - minY));
		return bounds;
	}
	
	public int getMinY(){
		return Math.round(bounds.getY());
	}

	// TRANSLATION
//	public void translate(int x, int y) {
//		this.vertices[0] = new Vertex(this.vertices[0].x + x, this.vertices[0].y + y, this.vertices[0].z);
//		this.vertices[1] = new Vertex(this.vertices[1].x + x, this.vertices[1].y + y, this.vertices[1].z);
//		this.vertices[2] = new Vertex(this.vertices[2].x + x, this.vertices[2].y + y, this.vertices[2].z);
//	}

	public void zoom(float rate) {
		System.out.println("Zooming:");
		Transform doubler = Transform.newScale(1, 1, rate);
		this.vertices[0] = doubler.multiply(this.vertices[0]);
		this.vertices[1] = doubler.multiply(this.vertices[1]);
		this.vertices[2] = doubler.multiply(this.vertices[2]);
		System.out.println(this.vertices[0].toString()+"\t"+this.vertices[1].toString()+"\t"+this.vertices[2].toString());
//		this.vertices[0] = new Vertex(this.vertices[0].x * rate, this.vertices[0].y * rate, this.vertices[0].z * rate);
//		this.vertices[1] = new Vertex(this.vertices[1].x * rate, this.vertices[1].y * rate, this.vertices[1].z * rate);
//		this.vertices[2] = new Vertex(this.vertices[2].x * rate, this.vertices[2].y * rate, this.vertices[2].z * rate);
	}
>>>>>>> d771c88ca6136e70550d55aaba2fd9ece834914e

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

<<<<<<< HEAD
	@Override
	public String toString() {
=======
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
	
	
	public EdgeList[] edgeList() {
		bounds();
		EdgeList[] e = new EdgeList[(int) (bounds.getHeight() + 1)];
		
		for(int i = 0; i<3; i++){
			//System.out.println("Edgelist vertices " +i + " "+ (i+1)%3);
			Vector3D va = vertices[i];
			Vector3D vb = vertices[(i+1)%3];
			
			//System.out.println("va.y " + va.y + " vb.y " + vb.y);
			
			if(va.y > vb.y){
				vb = va;
				va = vertices[(i+1)%3];
			}
			
			
			//System.out.println("va.y " + va.y + " vb.y " + vb.y);
			
			float mx = (vb.x - va.x)/(vb.y - va.y);
			float mz = (vb.z - va.z)/(vb.z - va.z);
			float x = va.x;
			float z = va.z;
			
			int j = Math.round(va.y)- Math.round(bounds.getY());
			int maxj = Math.round(vb.y) - Math.round(bounds.getY());
			
			while(j < maxj){
				if(e[j] == null){
					e[j] = new EdgeList(x, z);
				} else{
					e[j].add(x, z);
					
				}
				j++;
				x += mx;
				z += mz;
			}
			
		}
		return e;

	}
	
	
	
>>>>>>> d771c88ca6136e70550d55aaba2fd9ece834914e

		return "Polygon [vectors=" + vertices + ", colour=" + colorOut + "]";
	}
<<<<<<< HEAD
}
=======
	
	
	public Color shading(Light ambient, float intensity) {
//		float cost = convertUnitVector(normalVector).dotProduct(convertUnitVector(vectorLight));
		float cost = 1;

		int newRed = (int) ((ambient.getRed() + intensity * cost) * this.red);
		int newGreen = (int) ((ambient.getGreen() + intensity * cost) * this.green);
		int newBlue = (int) ((ambient.getBlue() + intensity * cost) * this.blue);

		if (newRed < 0) {
			newRed = 0;
		} else if (newRed > 255) {
			newRed = 255;
		}
		
		if (newGreen < 0) {
			newGreen = 0;
		} else if (newGreen > 255) {
			newGreen = 255;
		}
		
		if (newBlue < 0) {
			newBlue = 0;
		} else if (newBlue > 255) {
			newBlue = 255;
		}
		
		return new Color(newRed,newGreen,newBlue);

	}
	
	
}
>>>>>>> d771c88ca6136e70550d55aaba2fd9ece834914e
