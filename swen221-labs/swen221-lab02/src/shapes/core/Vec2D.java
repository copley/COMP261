package shapes.core;

/**
 * Represents a 2-Dimensional Vector. Note that Vec2D objects are immutable
 * (i.e. they cannot be changed);
 * 
 * @author David J. Pearce
 * 
 */
public final class Vec2D {
	private final double xComponent;
	private final double yComponent;	
	
	public Vec2D(double x, double y) {
		xComponent=x;
		yComponent=y;			
	}		

	/**
	 * Get x component of this vector
	 * @return
	 */
	public double getX() {
		return xComponent;
	}

	/**
	 * Get y component of this vector
	 * @return
	 */
	public double getY() {
		return yComponent;
	}

	/**
	 * Reflect this velocity along the x-axis
	 * @return
	 */
	public Vec2D negateX() {
		return new Vec2D(-xComponent,yComponent);
	}

	/**
	 * Reflect this velocity along the y-axis
	 * @return
	 */
	public Vec2D negateY() {
		return new Vec2D(xComponent,-yComponent);		
	}
	
	/**
	 * Invert both components
	 * @return
	 */
	public Vec2D negate() {
		return new Vec2D(-xComponent,-yComponent);		
	}
	
	/**
	 * Add a vector onto this vector.
	 * 
	 * @param other --- vector to add.
	 */
	public Vec2D add(Vec2D other) {
		return new Vec2D(xComponent + other.xComponent, yComponent
				+ other.yComponent);
	}
	
	/**
	 * Subtract a vector from this vector.
	 * 
	 * @param other --- vector to subtract.
	 */
	public Vec2D subtract(Vec2D other) {
		return new Vec2D(xComponent - other.xComponent, yComponent
				- other.yComponent);
	}
	
	/**
	 * Multiple a vector by a constant
	 * 
	 * @param constant
	 */
	public Vec2D multiply(double constant) {
		return new Vec2D(xComponent * constant, yComponent * constant);
	}
	
	/**
	 * Divide a vector by a constant.
	 * 
	 * @param constant
	 *            --- should not be zero!
	 */
	public Vec2D divide(double constant) {
		return new Vec2D(xComponent / constant, yComponent / constant);
	}
	
	public String toString() {
		return "(" + xComponent + ", " + yComponent + ")";
	}
}
