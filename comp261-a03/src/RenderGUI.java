import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * My first 3D renderer. Based on simple Z-buffer algorithm
 * @author Diego Trazzi
 */
public class RenderGUI extends GUI {

	Model model;
	private static float ambientLight = (float) 0.1;

	@Override
	protected void onLoad(File file) {
		try {
			BufferedReader data = new BufferedReader(new FileReader(file));
			String lightString = data.readLine(); // read first line - light position
			String[] lightArray = lightString.split(" ");
			Vector3D lightDirection = new Vector3D(Float.parseFloat(lightArray[0]), Float.parseFloat(lightArray[1]), Float.parseFloat(lightArray[2]));
			model = new Model(lightDirection);
			while (data.ready()) { // read polygons
				String line = data.readLine();
				Polygon polygon = new Polygon(line);
				model.addPolygon(polygon);
			}
			data.close();
		} catch (IOException e) {
			System.out.printf("Failed opening the file %s", file);
		}
		// render();
	}

	@Override
	protected void onKeyPress(KeyEvent ev) {
		if (model != null) {
			
		}
	}

	@Override
	protected void onMouseScroll(MouseWheelEvent e) {

		if (model != null) {
			int notches = e.getWheelRotation();
			System.out.println(notches);
			if (notches > 0) {
				model.zoomOut();
				render();
			} else {
				model.zoomIn();
				render();
			}
		}
	}

	/**
	 * Renders the current model
	 */
	@Override
//	protected BufferedImage render() {
//
//		// Initialize Z Buffer
//		Color[][] colorBuffer = new Color[CANVAS_WIDTH][CANVAS_HEIGHT];
//		double[][] depthBuffer = new double[CANVAS_WIDTH][CANVAS_HEIGHT];
//
//		for (int x = 0; x < colorBuffer.length; x++) {
//			for (int y = 0; y < colorBuffer[0].length; y++) {
//				colorBuffer[x][y] = Color.gray; // Initialize colors to grey
//				// float ramp = (float) Math.sin(x + Math.pow(y,1))*255;
//				// colorBuffer[x][y] = new Color(255, ramp, 255);
//			}
//		}
//		for (int x = 0; x < depthBuffer.length; x++) {
//			for (int y = 0; y < depthBuffer[0].length; y++) {
//				depthBuffer[x][y] = Integer.MAX_VALUE; // Initialize depths to infinite
//			}
//		}
//
//		// Render polygons
//		if (model != null) {
//			System.out.println("RENDERING POLY");
//			for (Polygon p : model.getPolygons()) {
//				for (int i = 0; i < depthBuffer.length; i++) {
//					for (int j = 0; j < depthBuffer[0].length; j++) {
////						double depth = p.getDepth(i - 350, j - 350);
////						depthBuffer[i][j] = -555;
//						colorBuffer[i][j] = p.getColor();
//					}
//				}
//			}
//		}
//
//		// render the bitmap to the image so it can be displayed (and saved)
//		return convertBitmapToImage(colorBuffer);
//	}
	
	protected BufferedImage render() {
		
		// Initialize Z Buffer
		Color[][] colorBuffer = new Color[CANVAS_WIDTH][CANVAS_HEIGHT];
		double[][] depthBuffer = new double[CANVAS_WIDTH][CANVAS_HEIGHT];
		
		Iterator<Polygon> i = model.getPolygons().iterator();
		while (i.hasNext()) {
			Polygon p = i.next();
//			if (p.facing()) {
				p.shading(model.getLightSource(), ambientLight);
				EdgeList[] edgelist = p.edgeList();
				int minY = p.getMinY();
				Color c = p.getColor();

				for (int j = 0; j < edgelist.length && edgelist[j]!=null; j++) {
					int y = minY + j;
					int x = Math.round(edgelist[j].getLeftX());
					int z = Math.round(edgelist[j].getLeftZ());
					int mz = Math.round((edgelist[j].getRightZ() - edgelist[j].getLeftZ())/ (edgelist[j].getRightX() - edgelist[j].getLeftX()));
					
					while (x <= edgelist[j].getRightX()) {
						if (z < depthBuffer[x][y]) {
							//System.out.println("does this work?");
							depthBuffer[x][y] = z;
							colorBuffer[x][y] = c;
						}
						x++;
						z += mz;
					}
				}
			}
		return convertBitmapToImage(colorBuffer);
		}

	private BufferedImage convertBitmapToImage(Color[][] bitmap) {
		BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				image.setRGB(x, y, bitmap[x][y].getRGB());
			}
		}
		return image;
	}

	public static void main(String[] args) {
		new RenderGUI();
	}

}