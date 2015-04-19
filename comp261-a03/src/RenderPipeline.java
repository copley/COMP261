import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class RenderPipeline {
	private static ArrayList<Polygon> polys = new ArrayList<Polygon>(10);
	private Vector3D lightSource;

	private static Color[][] screen;
	private static int[][] zBuffer;

	private static float ambientLight = (float) 0.1;
	private static int screenWidth = 800;
	private static int screenHeight = 800;
	private static BufferedImage image;
	
	
	private static final int INFINITY = Integer.MAX_VALUE;

	public static void main(String[] args) {
		String fileName = fileChooser();
		RenderPipeline rp = new RenderPipeline(fileName);
		//rp.printPolys();
		rp.initialisezBuffer();

		rp.render();
		convertToImage(screen);
		saveImage("Output.png");
	}
	
	private static String fileChooser(){
		JFrame frame = new JFrame();
		String filename = File.separator+"tmp";
		JFileChooser fc = new JFileChooser(new File(filename));

		// Show open dialog; this method does not return until the dialog is closed
		fc.showOpenDialog(frame);
		File selFile = fc.getSelectedFile();
		return selFile.getName();
	}

	private void render() {
		Iterator<Polygon> i = polys.iterator();
		while (i.hasNext()) {
			Polygon p = i.next();
			if (p.facing()) {
				p.shading(lightSource, ambientLight);
				EdgeList[] edgelist = p.edgeList();
				int minY = p.getMinY();
				Color c = p.getColour();

				for (int j = 0; j < edgelist.length && edgelist[j]!=null; j++) {
					int y = minY + j;
					

					//edgelist[j].print();
					//System.out.println(edgelist[j].getLeftX());
					int x = Math.round(edgelist[j].getLeftX());
					int z = Math.round(edgelist[j].getLeftZ());

					int mz = Math.round((edgelist[j].getRightZ() - edgelist[j].getLeftZ())
							/ (edgelist[j].getRightX() - edgelist[j].getLeftX()));
					
					
					//System.out.println("Left X: "+ x + " RightX: "+ edgelist[j].getRightX());
					while (x <= edgelist[j].getRightX()) {
						if (z < zBuffer[x][y]) {
							//System.out.println("does this work?");
							zBuffer[x][y] = z;
							screen[x][y] = c;
						}
						x++;
						z += mz;
					}
				}
			}
		}
	}

	/*
	 * Converts a 2D array of Colors to a BufferedImage. Assumes that bitmap is
	 * indexed by column then row and has imageHeight rows and imageWidth
	 * columns. Note that image.setRGB requires x (col) and y (row) are given in
	 * that order.
	 */
	public static BufferedImage convertToImage(Color[][] bitmap) {
		image = new BufferedImage(screenWidth, screenHeight,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				image.setRGB(x, y, bitmap[x][y].getRGB());
			}
		}
		return image;
	}

	/**
	 * writes a BufferedImage to a file of the specified name
	 */
	public static void saveImage(String fname) {
		try {
			ImageIO.write(image, "png", new File(fname));
		} catch (IOException e) {
			System.out.println("Image saving failed: " + e);
		}
	}

	private void initialisezBuffer() {
		for (int i = 0; i < screenHeight; i++) {
			for (int j = 0; j < screenWidth; j++) {
				screen[i][j] = Color.gray;
				zBuffer[i][j] = INFINITY;
			}
		}
	}

	/*
	 * Prints out a list of all the polygons in the scene along with their
	 * individual properties
	 */
	@SuppressWarnings("unused")
	private void printPolys() {
		System.out.println("Number of polygons: " + polys.size());

		Iterator<Polygon> i = polys.iterator();
		while (i.hasNext())
			System.out.println(i.next().toString());
	}

	/*
	 * Creates a new renderpipeline with the filename specified
	 */
	private RenderPipeline(String s) {
		try {
			BufferedReader b = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(s))));
			String line;

			// Light source
			line = b.readLine();
			Scanner sc = new Scanner(line);
			lightSource = new Vector3D(sc.nextFloat(), sc.nextFloat(),
					sc.nextFloat());
			sc.close();

			// Polygons in the file
			while ((line = b.readLine()) != null) {
				Scanner scan = new Scanner(line);
				polys.add(new Polygon(line));
				scan.close();
			}
			b.close();

		} catch (IOException e) {
			System.err.println(e);
		}
		RenderPipeline.screen = new Color[screenHeight][screenWidth];
		RenderPipeline.zBuffer = new int[screenHeight][screenWidth];
	}
	
	 

}