package mapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

public class Segment {

	private Road road; // Name of the road that the segment belongs to
	private double length; // Length of the segment (in Km)
	private Node nodeStart; // Node at which the segment starts
	private Node nodeEnd; // Node at which the segment ends
	private List<Location> coordList = new ArrayList<Location>();
	
/**
 * Segment constructor from string line
 * @param line
 * @param roads
 * @param nodes
 */
	public Segment(String line, Map<Integer, Road> roads, Map<Integer, Node> nodes) {
		String[] l = line.split("\t");
		road = roads.get(Integer.parseInt(l[0]));
		length = Double.parseDouble(l[1]);
		nodeStart = nodes.get(Integer.parseInt(l[2]));
		nodeEnd = nodes.get(Integer.parseInt(l[3]));
		// Loop to add segment coordinates
		for (int i=4; i<l.length; i=i+2) {
			coordList.add(Location.newFromLatLon(Double.parseDouble(l[i]), Double.parseDouble(l[i+1])));
		}
	}
	
	public Road getRoad(){
		return road;
	}
	
	public double getLength(){
		return length;
	}
	
	public Node getNodeStart(){
		return nodeStart;
	}
	
	public Node getNodeEnd(){
		return nodeEnd;
	}
	
/**
 * Draw method for segments
 * @param g
 * @param origin
 * @param scale
 * @param thickness
 */
	public void draw(Graphics g, Location origin, double scale, int thickness) {
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(thickness));
		Point p1 = coordList.get(0).asPoint(origin, scale);
		for (int i=1; i<coordList.size(); i++){
			Point p2 = coordList.get(i).asPoint(origin, scale);
			g2.draw(new Line2D.Float((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY()));			
			p1=p2;
		}
	}
}
