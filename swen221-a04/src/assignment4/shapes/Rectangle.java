package assignment4.shapes;

public class Rectangle implements Shape {

	private int x;
	private int y;
	private int width;
	private int height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @param width
	 *            sdts the width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height
	 *            sets the height 
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public boolean contains(int x, int y) {
		
		int minX = this.x;
		int minY = this.y;
		int maxX = this.x + this.width;
		int maxY = this.y + this.height;
		
		if (x >= minX && x < maxX && y >= minY && y < maxY ) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Rectangle boundingBox() {
		return this;
	}

}
