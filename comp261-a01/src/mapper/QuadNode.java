package mapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class QuadNode {

	private static final int MAX_NODES = 4;
	private List<Node> nodeList = new ArrayList<Node>();
	private Map<String, QuadNode> children = new HashMap<String, QuadNode>();
	private Location topLeft, bottomRight, centre;

	public QuadNode(Location topLeft, Location bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		setCentre();
		//System.out.printf("Top Left: %.4f %.4f \t Bottom Right: %.4f %.4f\n", this.topLeft.x, this.topLeft.y, this.bottomRight.x, this.bottomRight.y);
	}

	public List<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public Map<String, QuadNode> getChildren() {
		return children;
	}

	public void setChildren(Map<String, QuadNode> children) {
		this.children = children;
	}

	public Location getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(Location topLeft) {
		this.topLeft = topLeft;
	}

	public Location getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(Location bottomRight) {
		this.bottomRight = bottomRight;
	}

	/**
	 * If the tile contains more than MAX_NODES splits into sub-tiles
	 * @param node node to add to the quad-tree
	 */
	public void addNode(Node node) {
		// Check if the current Quad has childs already
		if (children.size() > 0) {
			// call addNode on the child relative to the quadrant
			String nodeQuad = this.getNodeQuadrant(node);
			// Try to pass the node to this node's children
			children.get(nodeQuad).addNode(node);
			return;
		}
		// Add node to the node list
		nodeList.add(node);
		// If the nodelist is larger than max nodes, split this quad into 4
		if (nodeList.size() >= MAX_NODES) {
			subdivide();
		}
	}
	
	/**
	 * Set the centre location of the quad based on this quadtree bounds
	 */
	private void setCentre() {
		double centreX = (topLeft.x + bottomRight.x) / 2;
		double centreY = (topLeft.y + bottomRight.y) / 2;
		this.centre = new Location(centreX, centreY);
	}
	
	/**
	 * Splits the current Quad-node into 4 children and passes its current nodes onto the appropriate child 
	 */
	private void subdivide() {
		// Find locations of the 4 new sub-tiles
		Location NWTopL = topLeft;
		Location NWBotR = centre;
		Location NETopL = new Location(centre.x, topLeft.y);
		Location NEBotR = new Location(bottomRight.x, centre.y);		
		Location SWTopL = new Location(topLeft.x, centre.y);
		Location SWBotR = new Location(centre.x, bottomRight.y);
		Location SETopL = new Location(centre.x, centre.y);
		Location SEBotR = new Location(bottomRight.x, bottomRight.y);		
		// Add locations to children map to store quadrant name (key) and location (value)
		children.put("NW", new QuadNode(NWTopL, NWBotR));
		children.put("NE", new QuadNode(NETopL, NEBotR));
		children.put("SW", new QuadNode(SWTopL, SWBotR));
		children.put("SE", new QuadNode(SETopL, SEBotR));
		// Add all the nodes from this tile to the appropriate child
		for (Node node : nodeList) {
			String quadrant = this.getNodeQuadrant(node);
			children.get(quadrant).addNode(node);
		}
	}
	
	/** Retrives the quadrant in which the node would be placed
	 * @param node node to which return the quandrant it's sitting in
	 */
	private String getNodeQuadrant(Node node) {
		String quad = "OUT-OF-BOUNDS";
		// Check if is in the positive X quadrant
		if (node.getLocation().x > centre.x) {
			// Check if is in the positive Y quadrant
			if (node.getLocation().y > centre.y) {
				// It's in the first quadrant
				quad = "NE";
			} else {
				// It's in the forth quadrant
				quad = "SE";
			}
		} else {
			if (node.getLocation().y > centre.y) {
				// It's in the second quadrant
				quad = "NW";
			} else {
				// It's in the third quadrant
				quad = "SW";
			}
		}
		return quad;
	}
	
	public QuadNode getPosition(double x, double y) {
		String position = getQuadrantFromPosition(x, y);
		if(children.containsKey(position)) {
			return children.get(position).getPosition(x, y);
		}
		return this;
	}
	
	public String getQuadrantFromPosition(double x, double y){
		String quad = "OUT-OF-BOUNDS";
		// Check if is in the positive X quadrant
		if (x > centre.x) {
			if (y > centre.y) {
				// It's in the first quadrant
				quad = "NE";
			} else {
				// It's in the forth quadrant
				quad = "SE";
			}
		} else {
			if (y > centre.y) {
				// It's in the second quadrant
				quad = "NW";
			} else {
				// It's in the third quadrant
				quad = "SW";
			}
		}
		return quad;
	}
	

	/**
	 * Performs full tree traversal using recursion.
	 */
	public void drawRec(QuadNode QNode, Graphics g, Location origin, double scale) {
		if (QNode.getChildren().size() > 0) {
			for (Map.Entry<String, QuadNode> entry : QNode.getChildren().entrySet()) {
				QuadNode childNode = entry.getValue();
				drawRec(childNode, g, origin, scale);
				childNode.draw(g, origin, scale);
			}
		}
	}
	
	/**
	 * Draw the QuadNOde as rectangle in the GUI panel
	 */
	public void draw(Graphics g, Location origin, double scale) {
		Point pTopL = this.topLeft.asPoint(origin, scale);
		Point pBotR = this.bottomRight.asPoint(origin, scale);
		int x = (int) pTopL.x;
		int y = (int) pTopL.y;
		int width = (int) Math.abs(pBotR.getX() - pTopL.getX());
		int heigth = (int) Math.abs(pBotR.getY() - pTopL.getY());
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, heigth);
	}
}
