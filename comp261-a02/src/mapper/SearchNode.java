package mapper;

public class SearchNode implements Comparable<SearchNode> {

	Node node;
	SearchNode fromNode;
	Segment segment;
	double costSoFar;
	double estTotalCost;

	public SearchNode(Node node, SearchNode fromNode, double costSoFar, double estCostToGoal) {
		this.node = node;
		this.fromNode = fromNode;
		this.costSoFar = costSoFar;
		this.estTotalCost = estCostToGoal;
	}

	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node
	 *            the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * @return the segment
	 */
	public Segment getSegment() {
		return segment;
	}

	/**
	 * @param segment the segment to set
	 */
	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	/**
	 * @return the fromNode
	 */
	public SearchNode getFromNode() {
		return fromNode;
	}

	/**
	 * @param fromNode
	 *            the fromNode to set
	 */
	public void setFromNode(SearchNode fromNode) {
		this.fromNode = fromNode;
	}

	/**
	 * @return the costSoFar
	 */
	public double getCostSoFar() {
		return costSoFar;
	}

	/**
	 * @param costSoFar
	 *            the costSoFar to set
	 */
	public void setCostSoFar(double costSoFar) {
		this.costSoFar = costSoFar;
	}

	/**
	 * @return the estCostSoFar
	 */
	public double getEstTotalCost() {
		return estTotalCost;
	}

	/**
	 * @param estCostSoFar
	 *            the estCostSoFar to set
	 */
	public void setEstTotalCost(double estCostSoFar) {
		this.estTotalCost = estCostSoFar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchNode [node=" + node + ", fromNode=" + fromNode + ", costSoFar=" + costSoFar + ", estCostSoFar=" + estTotalCost + "]";
	}

	@Override
	public int compareTo(SearchNode node) {
		if (estTotalCost < node.getEstTotalCost()) {
			return -1;
		} else if (estTotalCost > node.getEstTotalCost()) {
			return 1;
		}
		return 0;
	}
}
