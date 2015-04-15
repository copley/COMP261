package mapper;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;


/**
 * Node class
 *
 * @author Diego Trazzi
 * @version 0.0.1
 */

public class Node {

	private int id;
	private Location location;
	private List<Segment> segments = new ArrayList<Segment>();

	// Constructor from Location
	public Node(int id, Location location) {
		this.location = location;
		this.id = id;
	}

	// Constructor from text file string
	public Node(String line) {
		String[] l = line.split("\t");
		this.id = Integer.parseInt(l[0]);
		this.location = Location.newFromLatLon(Double.parseDouble(l[1]), Double.parseDouble(l[2]));
		}

	// Get ID
	public int getId(){
		return id;
	}

	// Get Location
	public Location getLocation(){
		return this.location;
	}

	// Add a segment
	public void addSegment(Segment segment){
		segments.add(segment);
	}

	// Get Segments
	public List<Segment> getSegments(){
		return segments;
	}

	// Draw the node
	public void draw(Graphics g, Location origin, double scale, int dotSize) {
		Point p = this.location.asPoint(origin, scale);
		g.fillRect((int) p.getX()-dotSize/2, (int) p.getY()-dotSize/2, dotSize, dotSize);
	}
}
