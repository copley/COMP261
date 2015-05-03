
public class Vertex {

	public final double x;
	public final double y;
	public final double z;
	
	public Vertex(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString(){
		String s = "("+x+","+y+","+z+")";
		return s;
	}
	
}
