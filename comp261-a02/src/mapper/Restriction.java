package mapper;

public class Restriction {

	int fromNodeID;
	int toNodeID;
	int nodeID;
	int fromRoadID;
	int toRoadID;
	
	public Restriction(int fromNodeID, int toNodeID, int nodeID, int fromRoadID, int toRoadID) {
		this.fromNodeID = fromNodeID;
		this.toNodeID = toNodeID;
		this.nodeID = nodeID;
		this.fromRoadID = fromRoadID;
		this.toRoadID = toRoadID;
	}
	
	public Restriction(String line) {
		String[] l = line.split("\t");
		this.fromNodeID = Integer.parseInt(l[0]);
		this.fromRoadID = Integer.parseInt(l[1]);
		this.nodeID = Integer.parseInt(l[2]);
		this.toRoadID = Integer.parseInt(l[3]);
		this.toNodeID = Integer.parseInt(l[4]);
	}

	/**
	 * @return the fromNodeID
	 */
	public int getFromNodeID() {
		return fromNodeID;
	}

	/**
	 * @return the toNodeID
	 */
	public int getToNodeID() {
		return toNodeID;
	}

	/**
	 * @return the nodeID
	 */
	public int getNodeID() {
		return nodeID;
	}

	/**
	 * @return the fromRoadID
	 */
	public int getFromRoadID() {
		return fromRoadID;
	}

	/**
	 * @return the toRoadID
	 */
	public int getToRoadID() {
		return toRoadID;
	}

	/**
	 * @param fromNodeID the fromNodeID to set
	 */
	public void setFromNodeID(int fromNodeID) {
		this.fromNodeID = fromNodeID;
	}

	/**
	 * @param toNodeID the toNodeID to set
	 */
	public void setToNodeID(int toNodeID) {
		this.toNodeID = toNodeID;
	}

	/**
	 * @param nodeID the nodeID to set
	 */
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	/**
	 * @param fromRoadID the fromRoadID to set
	 */
	public void setFromRoadID(int fromRoadID) {
		this.fromRoadID = fromRoadID;
	}

	/**
	 * @param toRoadID the toRoadID to set
	 */
	public void setToRoadID(int toRoadID) {
		this.toRoadID = toRoadID;
	}
	
	
	
}
