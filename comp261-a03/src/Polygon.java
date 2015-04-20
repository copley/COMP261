import java.awt.Color;

public class Polygon {

	private Vector3D[] vertices = new Vector3D[3];
	private final int red;
	private final int green;
	private final int blue;
	private BBox bbox;
	private Transform transform;
	private Color color;

	public Polygon(String line) { // constructor form line
		String[] vals = line.split(" ");
		this.vertices[0] = new Vector3D(Float.parseFloat(vals[0]), Float.parseFloat(vals[1]), Float.parseFloat(vals[2]));
		this.vertices[1] = new Vector3D(Float.parseFloat(vals[3]), Float.parseFloat(vals[4]), Float.parseFloat(vals[5]));
		this.vertices[2] = new Vector3D(Float.parseFloat(vals[6]), Float.parseFloat(vals[7]), Float.parseFloat(vals[8]));
		this.red = Integer.parseInt(vals[9]);
		this.green = Integer.parseInt(vals[10]);
		this.blue = Integer.parseInt(vals[11]);
		this.color = new Color(red, green, blue);
	}

	/**
	 * Find the bounding box for the polygon
	 * 
	 * @return
	 */
	public BBox bounds() {
		float minX = Math.min(Math.min(this.vertices[0].x, this.vertices[1].x), this.vertices[2].x);
		float minY = Math.min(Math.min(this.vertices[0].y, this.vertices[1].y), this.vertices[2].y);
		float maxX = Math.max(Math.max(this.vertices[0].x, this.vertices[1].x), this.vertices[2].x);
		float maxY = Math.max(Math.max(this.vertices[0].y, this.vertices[1].y), this.vertices[2].y);
		bbox = new BBox(minX, minY, (maxX - minX), (maxY - minY));
		return bbox;
	}

	// TRANSLATION
	public void translate(int x, int y) {
		transform = Transform.newTranslation(x, y, 0);
		this.vertices[0] = transform.multiply(this.vertices[0]);
		this.vertices[1] = transform.multiply(this.vertices[1]);
		this.vertices[2] = transform.multiply(this.vertices[2]);
	}

	public EdgeList[] edgeList() {
		bounds();
		EdgeList[] e = new EdgeList[(int) (bbox.getHeight() + 1)];
		
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
			
			int j = Math.round(va.y)- Math.round(bbox.getY());
			int maxj = Math.round(vb.y) - Math.round(bbox.getY());
			
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
}
