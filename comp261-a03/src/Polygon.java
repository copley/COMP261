import java.awt.Color;

public class Polygon {

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
	
	
	

	public Color getColor() {
		return color;
	}
	
	
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
