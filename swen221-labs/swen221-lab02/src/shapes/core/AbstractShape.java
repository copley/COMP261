package shapes.core;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Provides a standard implementation of the Shape interface. In particular,
 * this provides a standard model for collision between shapes. To implement a
 * shape based on this class is easy, and only requires implemented the
 * <code>getArea()</code>, <code>isConstained()</code>,
 * <code>getBoundingBox()</code> and <code>paint()</code> methods.
 * 
 * @author David J. Pearce
 * 
 */
public abstract class AbstractShape implements Shape {
	
	/**
	 * The coefficient of restitution (COR) of two colliding objects is a
	 * fractional value representing the ratio of speeds after and before an
	 * impact, taken along the line of the impact. Pairs of objects with COR 1
	 * collide elastically, while objects with COR < 1 collide inelastically.
	 * 
	 * See: http://en.wikipedia.org/wiki/Coefficient_of_restitution
	 */
	private static final double coefficientOfRestitution = 0.8;
	
	/**
	 * Position is the current center-most position of the shape.
	 */
	private Vec2D position;
	
	/**
	 * Current velocity is the current velocity of the shape. This determines
	 * the current speed and direction of the shape.
	 */
	private Vec2D currentVelocity;
	
	/**
	 * Next velocity is the velocity this shape will have in the next tick.
	 */
	private Vec2D nextVelocity;
		
	/**
	 * Set of current collisions
	 */
	private ArrayList<Shape> currentCollisions = new ArrayList<Shape>();
	
	/**
	 * The color of this shape.
	 */
	private Color color;
	
	
	public AbstractShape(Vec2D position, Vec2D velocity, Color color) {		
		this.position = position;
		this.currentVelocity = velocity;		
		this.nextVelocity = velocity;
		this.color = color;
	}
	
	/**
	 * Get the reference position of this shape
	 */
	public Vec2D getPosition() {
		return position;
	}
	
	/**
	 * Get the velocity of this shape
	 */
	public Vec2D getVelocity() {
		return currentVelocity;
	}	
	
	/**
	 * Get the color of this shape
	 */
	public Color getColor() {
		return color;
	}
	
	public void clockTick() {
		currentVelocity = nextVelocity;
		position = position.add(currentVelocity);
	}
		
	public void checkWallCollision(int width, int height) {
		BoundingBox box = getBoundingBox();
		int lx = box.getMinX();
		int rx = box.getMaxX();
		int ly = box.getMinY();
		int ry = box.getMaxY();

		if (lx <= 0 && nextVelocity.getX() < 0) {
			nextVelocity = nextVelocity.negateX();
			nextVelocity = nextVelocity.multiply(0.95); // friction
		} else if (rx >= width && nextVelocity.getX() > 0) {
			nextVelocity = nextVelocity.negateX();
			nextVelocity = nextVelocity.multiply(0.95); // friction
		}

		if (ly <= 0 && nextVelocity.getY() < 0) {
			nextVelocity = nextVelocity.negateY();
			nextVelocity = nextVelocity.multiply(0.95); // friction
		} else if (ry >= height && nextVelocity.getY() > 0) {
			nextVelocity = nextVelocity.negateY();
			nextVelocity = nextVelocity.multiply(0.95); // friction
		}
	}
	
	public void checkShapeCollision(Shape shape) {
		BoundingBox intersection = getBoundingBox().intersect(
				shape.getBoundingBox());

		if (intersection != null) {
			// Bounding boxes intersect, but that does not mean there actually
			// *is* an intersection. So, try and be certain there is an
			// intersection.

			for (int x = intersection.getMinX(); x < intersection.getMaxX(); x++) {
				for (int y = intersection.getMinY(); y < intersection.getMaxY(); y++) {
					// see if this point is in both shapes
					if (isContained(x, y) && shape.isContained(x, y) && !currentCollisions.contains(shape)) {
						// Yes, a collision has occured.
						// See:
						// http://en.wikipedia.org/wiki/Coefficient_of_restitution
						// for the source of this equation.
						double mA = getArea();
						double mB = shape.getArea();
						Vec2D uA = nextVelocity;
						Vec2D uB = shape.getVelocity();
												
						Vec2D vA = nextVelocity.multiply(mA);
						vA = vA.add(uB.multiply(mB));
						vA = vA.add((uB.subtract(uA).multiply(mA * coefficientOfRestitution)));
						vA = vA.divide(mA + mB);
						// done
						nextVelocity = vA;
						currentCollisions.add(shape);						
					}
				}
			}
		} else {
			currentCollisions.remove(shape);
		}
	}
}
