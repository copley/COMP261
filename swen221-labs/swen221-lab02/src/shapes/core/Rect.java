package shapes.core;

import java.awt.*;

/**
 * Represents a simple square on the display.
 *
 * @author David J. Pearce
 *
 */
public class Rect extends AbstractShape {
	private int width;
	private int length;

	public Rect(int width, int length, Vec2D position, Vec2D velocity, Color color) {
		super(position, velocity, color);
		this.width = width;
		this.length = length;
	}

	public double getArea() {
		return width*length;
	}

	public boolean isContained(int px, int py) {
		Vec2D position = getPosition();
		int x = (int) position.getX();
		int y = (int) position.getY();
		return x <= px && px <= (x + width) && y <= py && py <= (y + width);
	}

	public BoundingBox getBoundingBox() {
		int x = (int) getPosition().getX();
		int y = (int) getPosition().getY();
		return new BoundingBox(x,y, x+width, y+length);
	}

	public void paint(Graphics g) {
		g.setColor(getColor());
		g.fillRect((int) getPosition().getX(), (int) getPosition().getY(),
				width, length);
	}
}
