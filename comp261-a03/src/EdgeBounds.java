public class EdgeBounds {
	
	private Edge left = new Edge(0, Integer.MAX_VALUE, 0);
	private Edge right = new Edge(0, Integer.MIN_VALUE, 0);
	
	/*
	 * Getters
	 */
	public Edge getLeft() {
		return left;
	}
	public Edge getRight() {
		return right;
	}
	
	/*
	 * Setters
	 */
	public void setLeft(Edge left) {
		this.left = left;
	}
	public void setRight(Edge right) {
		this.right = right;
	}
	
	@Override
	public String toString() {
		return "EdgeLeftRight [left=" + left + ", right=" + right + "]";
	}
	
}