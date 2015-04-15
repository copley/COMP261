package mapper;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class artPNode {
	
	private Node node;
	private int reach;
	private artPNode parent;
	private int depth;
	private ArrayDeque<Node> children;
		
	public artPNode(Node node, int reach, artPNode parent) {
		this.node = node;
		this.reach = reach;
		this.depth = reach;
		this.parent = parent;
	}
	
	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public void setChildren(ArrayDeque<Node> children){
		this.children = children;
	}
	
	public ArrayDeque<Node> children(){
		return this.children;
	}
	
	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}
	
	/**
	 * @param node the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}
	
	/**
	 * @return the reach
	 */
	public int getReach() {
		return reach;
	}
	
	/**
	 * @param reach the reach to set
	 */
	public void setReach(int reach) {
		this.reach = reach;
	}
	
	/**
	 * @return the parent
	 */
	public artPNode getParent() {
		return parent;
	}
	
	/**
	 * @param parent the parent to set
	 */
	public void setParent(artPNode parent) {
		this.parent = parent;
	}
	
	
	
}
