package shapes.core;

import java.awt.*;

/**
 * Represents a simple square on the display.
 *
 * @author David J. Pearce
 *
 */
public class Circle extends AbstractShape {
	private int radius;

	public Circle(int radius, Vec2D position, Vec2D velocity, Color color) {
		super(position, velocity, color);
		this.radius = radius;
	}

	public double getArea() {
		return Math.PI*radius*radius;
	}

	public boolean isContained(int px, int py) {
		Vec2D position = getPosition();
		int x = (int) position.getX();
		int y = (int) position.getY();
		return x <= px && px <= (x + radius) && y <= py && py <= (y + radius);
	}

	public BoundingBox getBoundingBox() {
		int x = (int) getPosition().getX();
		int y = (int) getPosition().getY();
		return new BoundingBox(x,y, x+radius, y+radius);
	}

	public void paint(Graphics g) {
		g.setColor(getColor());
		g.fillOval((int) getPosition().getX(), (int) getPosition().getY(),
				radius, radius);
	}
}
