package shapes.viewer;

import java.awt.*;
import java.util.*;
import shapes.core.Shape;

public class BoardCanvas extends Canvas {		
	private static final Color BLACK = new Color(90,48,158);
	private static final Color WHITE = new Color(210,205,185);
	
	private final ArrayList<Shape> shapes;
	
	public BoardCanvas(ArrayList<Shape> shapes) {
		this.shapes = shapes;
		setBounds(0, 0, 400, 400);				
	}
	
	public void paint(Graphics g) {
		int width = getWidth();
		int height = getHeight();
				
		g.setColor(Color.WHITE);
		g.fillRect(0,0,width,height);		
		
		for(Shape s : shapes) {
			s.paint(g);
		}		
	}
	
	private Image offscreen = null;
	
	public void update(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		if (offscreen == null || offscreen.getWidth(this) != width
				|| offscreen.getHeight(this) != height) {
			offscreen = createImage(width, height);
		}
		Image localOffscreen = offscreen;
		Graphics offgc = offscreen.getGraphics();		
		// do normal redraw
		paint(offgc);
		// transfer offscreen to window
		g.drawImage(localOffscreen, 0, 0, this);
	}	
}
