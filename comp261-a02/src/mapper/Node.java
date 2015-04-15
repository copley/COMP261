package mapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.image.ImageObserver;


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
	private List<Segment> segmentsIn = new ArrayList<Segment>();
	private List<Segment> segmentsOut = new ArrayList<Segment>();
	private int depth;
	private boolean trafficLights = false;

	// Constructor from Location
	public Node(int id, Location location) {
		this.location = location;
		this.id = id;
		this.depth = Integer.MAX_VALUE;
	}

	// Constructor from text file string
	public Node(String line) {
		String[] l = line.split("\t");
		this.id = Integer.parseInt(l[0]);
		this.location = Location.newFromLatLon(Double.parseDouble(l[1]), Double.parseDouble(l[2]));
		}
	
	/**
	 * Reset the depth of current node to infinity
	 * 
	 */
	public void resetDepth() {
		this.depth = Integer.MAX_VALUE;
	}
		
	/**
	 * Gets the depth of this node
	 * @return the depth of this node
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Set the depth of this node
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * Gets the node id
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Sets if the node has/hasn't a traffic light
	 */
	public void setTrafficLights(boolean hasLights) {
		this.trafficLights = hasLights;
	}

	/**
	 * Gets if the node has traffic lights
	 * @return
	 */
	public boolean hasLights() {
		return trafficLights;
	}
	
	// Get Location
	public Location getLocation(){
		return this.location;
	}

	// Add a segment
	public void addSegment(Segment segment){
		segments.add(segment);
	}
	
	// Add a segment in
	public void addSegmentIn(Segment segment){
		segmentsIn.add(segment);
	}
	
	// Add a segment out
	public void addSegmentOut(Segment segment){
		segmentsOut.add(segment);
	}
	
	// Get Segments
	public List<Segment> getSegments(){
		return segments;
	}

	// Get Segments in
	public List<Segment> getSegmentsIn(){
		return segmentsIn;
	}
	
	// Get Segments out
	public List<Segment> getSegmentsOut(){
		return segmentsOut;
	}
	
	/**
	 * Calculates the Euclidean distance between this node and the endNode
	 * @param endNode
	 * @return the distance between the two nodes
	 */
	public double distanceBetween (Node endNode) {
		double startX = this.location.x;
		double startY = this.location.y;
		double endX = endNode.location.x;
		double endY = endNode.location.y;
			return	Math.sqrt(Math.pow((startX-endX),2)+Math.pow((startY-endY),2));
	}

	public Map<Node, Segment> getNeighbours(){
		Map<Node, Segment> neighbours = new HashMap<Node, Segment>();
		for (Segment seg : segments) {
			if (seg.getRoad().isOneway()) { // if is a one way road only return the end road nodes
				if (seg.getNodeEnd().getId() != this.getId()) { // if node end is not this node --> add (nodeEnd, distance) to neighbors
					neighbours.put(seg.getNodeEnd(), seg);
				}
			} else { // if is a two way road return all nodes connected
				if (seg.getNodeEnd().getId() != this.getId()) { // if node end is not this node --> add (nodeEnd, distance) to neighbors
					neighbours.put(seg.getNodeEnd(), seg);
				} else if (seg.getNodeStart().getId() != this.getId()) { // if node start is not this node --> add (nodeStart, distance) to neighbors
					neighbours.put(seg.getNodeStart(), seg);
				}
			}
		}
		return neighbours;
	}

	public Map<Node, Segment> getAllNeighbours(){
		Map<Node, Segment> neighbours = new HashMap<Node, Segment>();
		for (Segment seg : segments) {
			if (seg.getNodeEnd().getId() != this.getId()) { // if node end is not this node --> add (nodeEnd, distance) to neighbors
				neighbours.put(seg.getNodeEnd(), seg);
			} else if (seg.getNodeStart().getId() != this.getId()) { // if node start is not this node --> add (nodeStart, distance) to neighbors
				neighbours.put(seg.getNodeStart(), seg);
			}
		}
		return neighbours;
	}

	// Draw the node
	public void draw(Graphics g, Location origin, double scale, int dotSize, int isPathNode, ImageObserver observer) {
		Graphics2D g2 = (Graphics2D) g;
		Point p = this.location.asPoint(origin, scale);
		if (isPathNode == 0) { // is is not a path object draw normal nodes
			if (scale > 100) {
			g.drawString( Integer.toString(this.id), (int) p.getX()-dotSize*1, (int) p.getY()-dotSize*1);
			}
			g.fillRect((int) p.getX()-dotSize/2, (int) p.getY()-dotSize/2, dotSize, dotSize);
		} else if (isPathNode == 1) { // is start path node
			Image img1 = Toolkit.getDefaultToolkit().getImage("/Users/diego/Documents/Victoria/Git/comp261/comp261-a02/src/mapper/start.gif");
			//	    Image img1 = Toolkit.getDefaultToolkit().getImage("./start.gif");
			g2.drawImage(img1, (int) p.getX()-15, (int) p.getY()-50, observer);		
			g2.finalize();
		} else if (isPathNode == 2) { // is arrival path node 
			Image img1 = Toolkit.getDefaultToolkit().getImage("/Users/diego/Documents/Victoria/Git/comp261/comp261-a02/src/mapper/arrival.gif");
			//		Image img1 = Toolkit.getDefaultToolkit().getImage("./arrival.gif");
			g2.drawImage(img1, (int) p.getX()-15, (int) p.getY()-50, observer);		
			g2.finalize();
		}	
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String segInToString = new String("");
		String segOutToString = new String("");
		
		for (Segment seg : segmentsIn) {
			segInToString = segInToString.concat(" "+seg.getNodeStart().getId());
		}
		for (Segment seg : segmentsOut) {
			segOutToString = segOutToString.concat(" "+seg.getNodeEnd().getId());
		}
		return "Node "+this.id+" [segments=" + segments.size() + ", segmentsIn=" + segInToString + ", segmentsOut=" + segOutToString + "]";
	}
}
