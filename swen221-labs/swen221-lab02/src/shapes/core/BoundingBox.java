package shapes.core;

/**
 * Represents a bounding box on the screen. Thus, the position and dimension of
 * the box is represented using actual screen coordinates.
 * 
 * @author David J. Pearce
 * 
 */
public final class BoundingBox {
	private final int minX;
	private final int minY;	
	private final int maxX;
	private final int maxY;	
		
	public BoundingBox(int minX, int minY, int maxX, int maxY) {
		this.minX=minX;
		this.minY=minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}		
	
	public int getMinX() {
		return minX;
	}
	public int getMinY() {
		return minY;
	}

	public int getMaxX() {
		return maxX;
	}
	public int getMaxY() {
		return maxY;
	}
	
	/**
	 * Intersect two bounding boxes to produce either null (indicating no
	 * intersection), or the bounding box representing the intersection.
	 * 
	 * @param b
	 * @return
	 */
	public BoundingBox intersect(BoundingBox b) {
		int lx = Math.max(minX, b.minX);
		int rx = Math.min(maxX, b.maxX);
		int ly = Math.max(minY, b.minY);
		int ry = Math.min(maxY, b.maxY);
		if (lx < rx && ly < ry) {
			return new BoundingBox(lx, ly, rx, ry);
		} else {
			return null;
		}
	}
	
	public String toString() {
		return "(" + minX + ", " + minY + ")";
	}
}
