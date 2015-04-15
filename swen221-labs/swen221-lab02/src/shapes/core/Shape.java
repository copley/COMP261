package shapes.core;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A shape represents an item on the screen. Shapes have a position and
 * velocity, and occupy an area of some description (depending on what kind of
 * shape it is). Shapes are responsible for drawing themselves onto a graphics
 * canvas, and for checking whether they collide with either the outermost
 * walls, or another shape.
 * 
 * @author David J. Pearce
 * 
 */
public interface Shape {
	
	/**
	 * Check whether this shape contains a given point (i.e. the point is within
	 * this shape).
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isContained(int x, int y);
		
	/**
	 * Return the colour of this shape.
	 * @return
	 */
	public Color getColor();
	
	/**
	 * Return the area of the shape. This is used as substitute for "mass" in
	 * the simulation.  Thus, the bigger a shape, the heavier it should be.
	 * 
	 * @return
	 */
	public double getArea();
	
	/**
	 * Return the velocity of this shape. That is, its direction and speed.
	 * 
	 * @return
	 */
	public Vec2D getVelocity();	
		
	/**
	 * Return the bounding box for the shape in question. The bounding box is
	 * smallest rectangular box which encloses the shape entirely. This is
	 * useful as a first stage in determining whether two shapes are colliding.
	 * 
	 * @return
	 */
	public BoundingBox getBoundingBox();
	
	/**
	 * A clock event is signaled. The shape must respond to this by moving in
	 * some manner.
	 */
	public void clockTick();
	
	/**
	 * Draw this shape onto the given graphics context.
	 * 
	 * @param context
	 */
	public void paint(Graphics context);
	
	/**
	 * Check if this shape has collided with one of the outer walls. If so,
	 * respond in some appropriate manner.
	 * 
	 * @param width
	 * @param height
	 */
	public void checkWallCollision(int width, int height);
	
	/**
	 * Check if this shape has collided with another shape. If so, respond in
	 * some appropriate manner.
	 * 
	 * @param s
	 *            --- Shape to check for a collision with. Note, cannot be
	 *            "this" object.
	 */
	public void checkShapeCollision(Shape s);
}
