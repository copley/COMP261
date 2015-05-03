import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main extends GUI {

	// public Vector3D light;
	public List<Vector3D> ligths;
	private HashSet<Polygon> polygons;
	public BBox bounds;
	public float ambentIntensity = .1f;

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
				Vertex[] vertices = new Vertex[3];
				Color colour = null;
				for (int i = 0; i <= 9; i = i + 3) {
					if (i < 9) {
						x = Float.parseFloat(values[i]);
						y = Float.parseFloat(values[i + 1]);
						z = Float.parseFloat(values[i + 2]);
						// Vector3D vector = new Vector3D(x, y, z);
						vertices[i / 3] = new Vertex(x, y, z);
					} else {
						int r = Integer.parseInt(values[i]);
						int g = Integer.parseInt(values[i + 1]);
						int b = Integer.parseInt(values[i + 2]);
						colour = new Color(r, g, b);
					}
				}
				Polygon polygon = new Polygon(vertices, colour, this);
				for (Vertex v : polygon.vertices) {
					v.addPoly(polygon);
				}
				polygons.add(polygon);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void transformCompute(Vector3D rotation) {

//		System.out.println("---------------------------");
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
		// rotate the light as well (to make it look like the camera is moving)
		List<Vector3D> newLights = new ArrayList<Vector3D>();
		for (Vector3D light : ligths) {
			light = transform.multiply(light);
			newLights.add(light);
		}
		ligths = newLights;
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

		float x = (float) ((Math.random() - 0.5) * 2);
		float y = (float) ((Math.random() - 0.5) * 2);
		float z = (float) ((Math.random() - 0.5) * 2);
		ligths.add(new Vector3D(x, y, z));
		System.out.printf("ADDED A NEW LIGHT: %f %f %f\n", x, y, z);
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
						if (poly.vertices[i].vector.x > maxX) maxX = poly.vertices[i].vector.x;
						if (poly.vertices[i].vector.x < minX) minX = poly.vertices[i].vector.x;
						if (poly.vertices[i].vector.y > maxY) maxY = poly.vertices[i].vector.y;
						if (poly.vertices[i].vector.y < minY) minY = poly.vertices[i].vector.y;
						if (poly.vertices[i].vector.z > maxZ) maxZ = poly.vertices[i].vector.z;
						if (poly.vertices[i].vector.z < minZ) minZ = poly.vertices[i].vector.z;
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
				Map<Integer, EdgeBounds> edgeList = poly.getEdgeList();
				// compute vertex normal
				for (Vertex v : poly.vertices) {
					v.computeNormal();
				}
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
						int r = left.color.getRed();
						// System.out.println("EDGE RED: "+r);
						int g = left.color.getGreen();
						int b = left.color.getBlue();
						float deltaZ = (right.z - left.z) / (right.x - left.x);
						int deltaR = Math.round((right.color.getRed() - left.color.getRed()) / (right.x - left.x));
						// System.out.println("DELTA RED: "+ deltaR);
						int deltaG = Math.round((right.color.getGreen() - left.color.getGreen()) / (right.x - left.x));
						int deltaB = Math.round((right.color.getBlue() - left.color.getBlue()) / (right.x - left.x));
						while (x <= right.x && (x < CANVAS_WIDTH)) {
							if (bitmap[x][y] == null || z < bitmap[x][y].z) {
								bitmap[x][y] = new Pixel(x, y);
								// POLY COLOR
								bitmap[x][y].color = poly.colorOut;
								// VERTEX COLORS
								// bitmap[x][y].color = new Color(r,g,b);
								bitmap[x][y].z = z;
							}
							x++;
							z = z + deltaZ;
							r = r + deltaR;
							g = g + deltaG;
							b = b + deltaB;
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