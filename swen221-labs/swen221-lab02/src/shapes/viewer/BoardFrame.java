package shapes.viewer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;

import shapes.core.BoundingBox;
import shapes.core.Circle;
import shapes.core.Rect;
import shapes.core.Shape;
import shapes.core.Square;
import shapes.core.Vec2D;

public class BoardFrame extends JFrame implements ActionListener {
	private JPanel bottomPanel;
	private JPanel centerPanel;
	private BoardCanvas boardCanvas;
	private ClockThread clock;
	private Random random = new Random();
	private ArrayList<Shape> shapes = new ArrayList<Shape>();

	public BoardFrame() {
		super("Shape Viewer");

		boardCanvas = new BoardCanvas(shapes);

		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		Border cb = BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(3, 3, 3, 3),
				BorderFactory.createLineBorder(Color.gray));
		centerPanel.setBorder(cb);
		centerPanel.add(boardCanvas, BorderLayout.CENTER);

		JButton resetbk = new JButton("Reset");
		JButton sqbk = new JButton("Add Square");
		JButton rectbk = new JButton("Add Rectangle");
		JButton circbk = new JButton("Add Circle");

		resetbk.addActionListener(this);
		sqbk.addActionListener(this);
		rectbk.addActionListener(this);
		circbk.addActionListener(this);

		bottomPanel = new JPanel();
		bottomPanel.add(resetbk);
		bottomPanel.add(sqbk);
		bottomPanel.add(rectbk);
		bottomPanel.add(circbk);

		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		setFocusable(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

		// finally, start the clock ticking

		clock = new ClockThread(10, this);
		clock.start();
	}

	public synchronized void clockTick() {
		// First, check for shape collisions
		for (int i = 0; i < shapes.size(); ++i) {
			for (int j = i + 1; j < shapes.size(); ++j) {
				Shape is = shapes.get(i);
				Shape js = shapes.get(j);
				is.checkShapeCollision(js);
				js.checkShapeCollision(is);
			}
		}

		// Second, detect for wall collisions
		int width = boardCanvas.getWidth();
		int height = boardCanvas.getHeight();

		for (Shape s : shapes) {
			s.checkWallCollision(width, height);
		}

		// Third, update positions.
		for (Shape s : shapes) {
			s.clockTick();
		}

		// Finally, repaint the entire display
		boardCanvas.repaint();
	}

	public synchronized void add(Shape s) {
		shapes.add(s);
		repaint();
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Reset")) {
			shapes.clear();

		} else if (cmd.equals("Add Square")) {
			shapes.add(new Square(randomWidth(), randomPosition(),
					randomVelocity(), randomColor()));

		} else if (cmd.equals("Add Rectangle")) {
			shapes.add(new Rect(randomWidth(), randomLength(),
					randomPosition(), randomVelocity(), randomColor()));

		} else if (cmd.equals("Add Circle")) {
			shapes.add(new Circle(randomRadius(), randomPosition(),
					randomVelocity(), randomColor()));

		}
	}

	private int randomWidth() {
		return 10 + random.nextInt(20); // min size 10, max size 30
	}

	private int randomRadius() {
		return 5 + random.nextInt(20); // min size 5, max size 25
	}

	private int randomLength() {
		return 10 + random.nextInt(30); // min size 10, max size 40
	}

	private Vec2D randomPosition() {
		int x = random.nextInt(boardCanvas.getWidth());
		int y = random.nextInt(boardCanvas.getHeight());
		return new Vec2D(x, y);
	}

	private Vec2D randomVelocity() {
		double x = 2 * random.nextDouble();
		double y = 2 * random.nextDouble();
		return new Vec2D(x, y);
	}

	private static Color[] colors = { Color.RED, Color.GREEN, Color.BLUE,
			Color.YELLOW, Color.CYAN, Color.GRAY, Color.DARK_GRAY,
			Color.LIGHT_GRAY, Color.MAGENTA, Color.PINK, Color.ORANGE };

	public Color randomColor() {
		int idx = random.nextInt(colors.length);
		return colors[idx];
	}
}
