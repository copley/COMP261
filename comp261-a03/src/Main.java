import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

public class Main extends GUI {

//	public Vector3D light;
	public ArrayList<Vector3D> ligths;
	private HashSet<Polygon> polygons;
	public BBox bounds;
	public float ambentIntensity = .3f;
	public float gamma = 1f;

	@Override
	protected void onLoad(File file) {

		try {
			BufferedReader fileScan = new BufferedReader(new FileReader(file));
			loadModel(fileScan);
			transformCompute(new Vector3D(0, 0, 0));
			fileScan.close();
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}

	private void loadModel(BufferedReader scan) {

		try {
			String line = scan.readLine();
			String[] values = line.split("\\s+");
			float x = Float.parseFloat(values[0]);
			float y = Float.parseFloat(values[1]);
			float z = Float.parseFloat(values[2]);
			ligths = new ArrayList<Vector3D>();
			polygons = new HashSet<Polygon>();
			ligths.add(new Vector3D(x, y, z));
			while ((line = scan.readLine()) != null) {
				values = line.split("\\s+");
				Vector3D[] vertices = new Vector3D[3];
				Color colour = null;
				for (int i = 0; i <= 9; i = i + 3) {
					if (i < 9) {
						x = Float.parseFloat(values[i]);
						y = Float.parseFloat(values[i + 1]);
						z = Float.parseFloat(values[i + 2]);
						Vector3D vector = new Vector3D(x, y, z);
						vertices[i / 3] = vector;
					} else {
						int r = Integer.parseInt(values[i]);
						int g = Integer.parseInt(values[i + 1]);
						int b = Integer.parseInt(values[i + 2]);
						colour = new Color(r, g, b);
					}
				}
				Polygon polygon = new Polygon(vertices, colour, this);
				polygons.add(polygon);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void transformCompute(Vector3D rotation) {

		System.out.println("---------------------------");
		// /*
		// calculate new scale
		BBox bounds = computeBounds(); // calculates bounds to set min and max
		Transform transform = Transform.identity();
		float width = CANVAS_WIDTH / (bounds.getMaxX() - bounds.getMinX());
		float height = CANVAS_HEIGHT / (bounds.getMaxY() - bounds.getMinY());
		float scaleRatio = Math.min(width, height); //
		System.out.println("SCALE-RATIO: " + scaleRatio);
		// finds whichever scale ratio is smaller between width and height
		transform = Transform.newScale(scaleRatio, scaleRatio, scaleRatio).compose(transform);
		transformPolygons(transform);
		// scale ends
		// Move back to 0,0,0
		bounds = computeBounds(); // calculates bounds to set min and max
		transform = Transform.identity();
		float tX = -bounds.getMinX();
		float tY = -bounds.getMinY();
		float tZ = -bounds.getMinZ();
		Vector3D offset = new Vector3D(tX, tY, tZ);
		transform = Transform.newTranslation(offset);
		transformPolygons(transform);
		bounds = computeBounds(); // calculates bounds to set min and max
		// If there is a rotation vector move everything back to zero, then rotate and move back to previous centre
		// reset transforms
		transform = Transform.identity();
		bounds = computeBounds(); // recompute the bounds
		Vector3D pivot = new Vector3D((bounds.getMaxX() - bounds.getMinX()) / 2, (bounds.getMaxY() - bounds.getMinY()) / 2, (bounds.getMaxZ() - bounds.getMinZ()) / 2);
		// Translate object back to 0,0,0 and rotate
		transform = Transform.newTranslation(-pivot.x, -pivot.y, -pivot.z).compose(transform);
		transform = Transform.newXRotation(rotation.x).compose(transform);
		transform = Transform.newYRotation(rotation.y).compose(transform);
		transform = Transform.newZRotation(rotation.z).compose(transform);
		transformPolygons(transform);
		// Translate back to canvas centre
		transform = Transform.identity();
		bounds = computeBounds(); // recompute new bounds
		pivot = new Vector3D((bounds.getMaxX() - bounds.getMinX()) / 2, (bounds.getMaxY() - bounds.getMinY()) / 2, (bounds.getMaxZ() - bounds.getMinZ()) / 2);
		transform = Transform.newTranslation(pivot.x, pivot.y, pivot.z).compose(transform);
		transformPolygons(transform);
	}

	@Override
	protected void onKeyPress(KeyEvent ev) {

		float increment = 0.1f;
		if (ev.getKeyCode() == KeyEvent.VK_LEFT || Character.toUpperCase(ev.getKeyChar()) == 'A') {
			Vector3D rotation = new Vector3D(0, increment, 0);
			transformCompute(rotation);
		} else if (ev.getKeyCode() == KeyEvent.VK_RIGHT || Character.toUpperCase(ev.getKeyChar()) == 'D') {
			Vector3D rotation = new Vector3D(0, -increment, 0);
			transformCompute(rotation);
		} else if (ev.getKeyCode() == KeyEvent.VK_UP || Character.toUpperCase(ev.getKeyChar()) == 'W') {
			Vector3D rotation = new Vector3D(-increment, 0, 0);
			transformCompute(rotation);
		} else if (ev.getKeyCode() == KeyEvent.VK_DOWN || Character.toUpperCase(ev.getKeyChar()) == 'S') {
			Vector3D rotation = new Vector3D(increment, 0, 0);
			transformCompute(rotation);
		}
	}

	@Override
	protected void onLightAdd() {
		float x = (float) ((Math.random()-0.5)*2);
		System.out.println(x);
		float y = (float) ((Math.random()-0.5)*2);
		System.out.println(y);
		float z = (float) ((Math.random()-0.5)*2);
		ligths.add(new Vector3D(x, y, z));
		System.out.println(z);

		System.out.println("ADD A LIGHT");
	}

	@Override
	protected void onSliderChange(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		gamma = source.getValue();
		redraw();
	}

	private void transformPolygons(Transform transform) {

		if (polygons != null) {
			for (Polygon poly : polygons) {
				poly.applyTransformation(transform);
			}
		}
	}

	private BBox computeBounds() {

		float maxX = Integer.MIN_VALUE;
		float maxY = Integer.MIN_VALUE;
		float maxZ = Integer.MIN_VALUE;
		float minX = Integer.MAX_VALUE;
		float minY = Integer.MAX_VALUE;
		float minZ = Integer.MAX_VALUE;
		if (polygons != null) {
			for (Polygon poly : polygons) {
				if (!poly.hidden) {
					for (int i = 0; i < 3; i++) {
						if (poly.vertices[i].x > maxX) maxX = poly.vertices[i].x;
						if (poly.vertices[i].x < minX) minX = poly.vertices[i].x;
						if (poly.vertices[i].y > maxY) maxY = poly.vertices[i].y;
						if (poly.vertices[i].y < minY) minY = poly.vertices[i].y;
						if (poly.vertices[i].z > maxZ) maxZ = poly.vertices[i].z;
						if (poly.vertices[i].z < minZ) minZ = poly.vertices[i].z;
					}
				}
			}
		}
		System.out.println("COMPUTE BOUNDS: " + minX + " " + maxX + " " + minY + " " + maxY + " " + minZ + " " + maxZ);
		BBox bounds = new BBox(minX, maxX, minY, maxY, minZ, maxZ);
		return bounds;
	}

	@Override
	protected BufferedImage render() {

		if (polygons != null) {
			Pixel[][] bitmap = new Pixel[CANVAS_WIDTH][CANVAS_HEIGHT];
			for (Polygon poly : polygons) {
				HashMap<Integer, EdgeBounds> edgeList = poly.getEdgeList();
				int y = Math.round(poly.minVector.y);
				if (y < 0) y = 0;
				int maxY = Math.round(poly.maxVector.y);
				if (!poly.hidden) {
					while (y < maxY && (y < CANVAS_HEIGHT)) {
						Edge left = edgeList.get(y).getLeft();
						Edge right = edgeList.get(y).getRight();
						int x = Math.round(left.x);
						if (x < 0) x = 0;
						float z = left.z;
						float changeZ = (right.z - left.z) / (right.x - left.x);
						while (x <= right.x && (x < CANVAS_WIDTH)) {
							if (bitmap[x][y] == null || z < bitmap[x][y].z) {
								bitmap[x][y] = new Pixel(x, y);
								bitmap[x][y].color = poly.colorOut;
								bitmap[x][y].z = z;
							}
							x++;
							z = z + changeZ;
						}
						y++;
					}
				}
			}
			return convertBitmapToImage(bitmap);
		} else {
			return null;
		}
	}

	private BufferedImage convertBitmapToImage(Pixel[][] bitmap) {

		BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				if (bitmap[x][y] != null) {
					Pixel pixel = bitmap[x][y];
					image.setRGB(x, y, pixel.color.getRGB());
				} else {
					image.setRGB(x, y, Color.GRAY.getRGB());
				}
			}
		}
		return image;
	}

	public static void main(String[] args) {

		new Main();
	}
}