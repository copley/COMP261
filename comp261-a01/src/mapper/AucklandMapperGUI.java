package mapper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Auckland Mapper GUI Class loads from a set of files data containing GPS coordinate of intersection, roads and polygons of the Auckland
 *
 * @author Diego Trazzi
 * @version 0.6
 */

public class AucklandMapperGUI extends GUI {
	
	/**
	 * {@inheritDoc}
	 */

	// Drawing Elements
	private Location origin;
	private double scale = 30;	
	private int sX = -1;
	private int sY = -1;
	private int dX; // Delta X: movement from sX
	private int dY; // Delta Y: movement from sY
	private int dragLag = 50;
	private Node activeNode;
	private QuadNode quadTreeRoot = null;
	private Map<Integer, PolyColor> colorMap = new TreeMap<Integer,PolyColor>();
	private boolean debug = false;
	private boolean dragging = true;
	
	// Map of all nodes, indexed by ID
	private Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
	private Map<Integer, Road> roadMap = new HashMap<Integer, Road>();
	private Set<Polygon> polySet = new HashSet<Polygon>();

	// Map Boundaries (sets the to infinite so we can compare with data set)
	private double north = Double.NEGATIVE_INFINITY;
	private double south = Double.POSITIVE_INFINITY;
	private double east = Double.NEGATIVE_INFINITY;
	private double west = Double.POSITIVE_INFINITY;

	// Tri-Search
	TNode triRoot = new TNode();
	List<Road> activeRoads = new ArrayList<Road>();

	// Color set for polygons
	public AucklandMapperGUI() {
		colorMap.put(2, new PolyColor(174, 80, 58));
		colorMap.put(5, new PolyColor(174, 40, 58));
		colorMap.put(7, new PolyColor(174, 20, 58));
		colorMap.put(8, new PolyColor(174, 124, 58));
		colorMap.put(10, new PolyColor(125, 10, 200));
		colorMap.put(11, new PolyColor(174, 128, 87));
		colorMap.put(14, new PolyColor(174, 128, 87));
		colorMap.put(19, new PolyColor(149, 118, 78));
		colorMap.put(22, new PolyColor(79, 188, 78));
		colorMap.put(23, new PolyColor(79, 162, 70));
		colorMap.put(24, new PolyColor(125, 10, 200));
		colorMap.put(25, new PolyColor(125, 10, 200));
		colorMap.put(26, new PolyColor(125, 10, 200));
		colorMap.put(30, new PolyColor(125, 10, 200));
		colorMap.put(40, new PolyColor(79, 162, 170)); // OCEAN
		colorMap.put(60, new PolyColor(79, 126, 82));
		colorMap.put(62, new PolyColor(79, 126, 82));
		colorMap.put(64, new PolyColor(79, 207, 237)); // RIVERS
		colorMap.put(65, new PolyColor(79, 207, 237)); // RIVERS
		colorMap.put(69, new PolyColor(79, 207, 237)); // RIVERS
		colorMap.put(71, new PolyColor(79, 207, 237)); // RIVERS
		colorMap.put(72, new PolyColor(79, 207, 237)); // RIVERS
		colorMap.put(80, new PolyColor(79, 207, 237)); // RIVERS
	}

	/**
	 * Is called when the user has successfully selected a directory to load the
	 * data files from. File objects representing the four files of interested
	 * are passed to the method. The fourth File, polygons, might be null if it
	 * isn't present in the directory.
	 *
	 * @param nodes
	 *            a File for nodeID-lat-lon.tab
	 * @param roads
	 *            a File for roadID-roadInfo.tab
	 * @param segments
	 *            a File for roadSeg-roadID-length-nodeID-nodeID-coords.tab
	 * @param polygons
	 *            a File for polygon-shapes.mp
	 */
	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		this.loadData(nodes, roads, segments, polygons);
		this.setOrigin();
	}

	/**
	 * Load all data from text files
	 * @param nodes nodes file
	 * @param roads roads file
	 * @param segments segments file
	 * @param polygons polygons file
	 */
	protected void loadData(File nodes, File roads, File segments, File polygons) {
		getTextOutputArea().append("Loading from "+roads+"\n");
		this.loadRoads(roads);
		getTextOutputArea().append("Loading from "+nodes+"\n");
		this.loadNodes(nodes);
		getTextOutputArea().append("Loading from "+segments+"\n");
		this.loadSegments(segments);
		if (polygons != null) { 
			getTextOutputArea().append("Loading from "+polygons+"\n");
			this.loadPolygons(polygons);
		}
	}

	/**
	 * try to load the roads from text file and adds them to "roadMap". In case of fail will print out path file
	 * @param roads road file
	 */
	protected void loadRoads(File roads) {
		try {
			BufferedReader data = new BufferedReader(new FileReader(roads));
			// Removes first line
			data.readLine();
			while (data.ready()) {
				// Read line and creates a new road
				String line = data.readLine();
				// Add new road to the roads map
				Road road = new Road(line);
				roadMap.put(road.getId(), road);
				triRoot.addRoad(road);
			}
			data.close();
		} catch (IOException e) {
			System.out.printf("Failed to open file: %s, %s", e.getMessage(),
					e.getStackTrace());
		}
	}

	/**
	 * load all nodes from Node file and adds them to "nodeMap"
	 * @param nodes node file
	 */
	protected void loadNodes(File nodes) {
		try {
			BufferedReader data = new BufferedReader(new FileReader(nodes));
			while (data.ready()) {
				// Read line and creates a new node
				String line = data.readLine();
				if (line.length() > 0) {
					Node node = new Node(line);
					// Add the new node to the nodes map
					nodeMap.put(node.getId(), node);
				}
			}
			// Generates a quad-tree structure for the nodes
			double[] bounds = this.setBounds();
			quadTreeRoot = new QuadNode(new Location(bounds[0], bounds[2]), new Location(bounds[1], bounds[3]));
			// Add all the nodes to the quad-tree
			for(Node node : nodeMap.values()) {
				quadTreeRoot.addNode(node);
			}
			data.close();
		} catch (IOException e) {
			System.out.printf("Failed to open file: %s, %s", e.getMessage(),
					e.getStackTrace());
		}
	}

	/**
	 * load all segments from text file and adds them to "nodeMap" & "roadMap"
	 * @param segments Segments file
	 */
	protected void loadSegments(File segments) {
		try {
			BufferedReader data = new BufferedReader(new FileReader(segments));
			// Removes first line
			data.readLine();
			// Creat Segments
			while (data.ready()) {
				String line = data.readLine();
				Segment segment = new Segment(line, roadMap, nodeMap);
				// If road exists, add the segment
				Road road = segment.getRoad();
				if (road != null) {
					road.addSegment(segment);
				}
				// If node exists, add the segment
				Node nodeStart = segment.getNodeStart();
				if (nodeStart != null) {
					nodeStart.addSegment(segment);
				}
				Node nodeEnd = segment.getNodeEnd();
				if (nodeEnd != null) {
					nodeEnd.addSegment(segment);
				}
			}
			data.close();
		} catch (IOException e) {
			System.out.printf("Failed to open file: %s, %s", e.getMessage(),
					e.getStackTrace());
		}
	}

	/**
	 * load polygon file into memory
	 * @param polygons polygon file
	 */
	protected void loadPolygons(File polygons){
		Integer polyType =0;
		String label = "";
		Integer endLevel = 0;
		Integer cityIdx = 0;
		List<Location> coordList = new ArrayList<Location>();
		Set<Integer> typeSet = new TreeSet<Integer>();

		try {
			BufferedReader data = new BufferedReader(new FileReader(polygons));
			while (data.ready()){
				String line = data.readLine();
					if (line.startsWith("Type=")){
						polyType = Integer.parseInt(line.substring(7),16);
						// Add polyType to a tmp set, to check how many colors to use
						typeSet.add(polyType);
					} else if (line.startsWith("Label=")) {
						label = line.substring(6);
						//System.out.printf("Label: %s ",label);
					} else if (line.startsWith("EndLevel=")) {
						endLevel = Integer.parseInt(line.substring(9));
						//System.out.printf("End Level: %d ",endLevel);
					} else if (line.startsWith("CityIdx=")) {
						cityIdx = Integer.parseInt(line.substring(8));
						//System.out.printf("End Level: %d ",endLevel);
					} else if (line.startsWith("Data0=")) {
						String strCoords = line.substring(6);
						coordList.clear();
						// Splits the coordList String into tuples, then further separates each tuple in X and Y
						String[] coordArray = strCoords.substring(1,strCoords.length()-2).split("\\),\\(",-1);
						for (int i=0;i<coordArray.length;i++){
							Double coordX = Double.parseDouble(coordArray[i].split(",")[0]);
							Double coordY = Double.parseDouble(coordArray[i].split(",")[1]);
							coordList.add(Location.newFromLatLon(coordX, coordY));
							//System.out.printf("Coords: x: %f \t y: %f\n",coordX, coordY);
						}
						Polygon polyShape = new Polygon(polyType, endLevel, label, cityIdx, coordList, colorMap.get(polyType));
						polySet.add(polyShape);
					}
				}
			data.close();
			// Display how many types there are in the GPS polygon file
			//printIntSet(typeSet);
		}
		catch (IOException e){
			System.out.printf("Failed to open file %s, %s", e.getMessage(), e.getStackTrace());
		}
	}

	/**
	 * Find the center of the graph from the bounds array and screen size
	 */
	protected void setOrigin() {
		double[] bounds = this.setBounds();
		origin = new Location(bounds[0], bounds[2]);
		scale = Math.min(GUI.DEFAULT_DRAWING_WIDTH / (bounds[1] - bounds[0]),
				GUI.DEFAULT_DRAWING_HEIGHT / (bounds[2] - bounds[3]));
	}

	/**
	 * utility to print out all the node entries
	 */
	protected void printNodeMap() {
		for (Map.Entry<Integer, Node> entry : nodeMap.entrySet()) {
			System.out.printf("NodeID: %d\t Segments: %s\n", entry.getKey(),
					entry.getValue().getSegments());
		}
	}

	/**
	 * utility to print out all the type entries from the polygon file
	 * @param set the set to be printed
	 */
	protected void printIntSet(Set<Integer> set) {
		for (Integer entry : set) {
			System.out.printf("Entry: %d\n", entry);
		}
	}

	/**
	 * utility method to print out all the road entries
	 */
	protected void printRoadMap() {
		for (Map.Entry<Integer, Road> entry : roadMap.entrySet()) {
			System.out.printf("RoadID: %d\t Segments: %s\n", entry.getKey(),
					entry.getValue().getSegments());
		}
	}

	/**
	 * set the maximum and minimum location of the loaded data
	 */
	protected double[] setBounds() {
		for (Node node : nodeMap.values()) {
			if (node.getLocation().x < west)
				west = node.getLocation().x;
			if (node.getLocation().x > east)
				east = node.getLocation().x;
			if (node.getLocation().y > north)
				north = node.getLocation().y;
			if (node.getLocation().y < south)
				south = node.getLocation().y;
		}
		return new double[] { west, east, north, south };
	}

	/**
	 * Is called when the drawing area is redrawn and performs all the logic for
	 * the actual drawing, which is done with the passed Graphics object.
	 */
	@Override
	protected void redraw(Graphics g) {
		this.draw(g, origin, scale,
				super.getDrawingAreaDimension().getHeight(), super
						.getDrawingAreaDimension().getWidth());
	}

	/**
	 * calls draw methods on all the nodes and segments
	 */
	protected void draw(Graphics g, Location origin, double scale, double screenH, double screenW) {

		// Pass origin and scale to the top, so we can use them to know where the user is clicking withouth having to call draw
		this.origin = origin;
		this.scale = scale;

		if (nodeMap.size() > 0) {
			// Filter out nodes which are not on screen to speed up drawing
			double topY = (origin.y * .97);
			double leftX = origin.x * .97;
			double bottomY = (origin.y - screenH / (scale * 1.03));
			double rightX = (origin.x + screenW / (scale * 1.03));
			// Draw polygons
			for (Polygon poly : polySet) {
				for (Integer entry : colorMap.keySet()) {
					poly.draw(g, origin, scale, entry);
				}
			}
			// Draw segments
			for (Road road : roadMap.values()) {
				if (activeRoads.contains(road)) {
					g.setColor(Color.RED);
					for (Segment seg : road.getSegments()) {
						seg.draw(g, origin, scale, 4);
					}
				} else {
					g.setColor(Color.BLACK);
					for (Segment seg : road.getSegments()) {
						seg.draw(g, origin, scale, 1);
					}
				}
			}
			// Draw nodes
			for (Node node : nodeMap.values()) {
				if (node.getLocation().y < topY && node.getLocation().y > bottomY) {
					if (node.getLocation().x > leftX && node.getLocation().x < rightX) {
						if (node == activeNode){
							g.setColor(Color.RED);
						}
						else {
							g.setColor(Color.BLUE);
						}
						int dotSize = (int) Math.min(.2 * (scale),7);
						node.draw(g, origin, scale, dotSize);
					}
				}
			}
//			// Draw quads with recursion
//			if (debug && quadTreeRoot != null) {
//				quadTreeRoot.drawRec(quadTreeRoot, g, origin, scale);
//			}
		}
	}
	
	/**
	 * It's called when the DEBUG button from the GUI is pressed. Utilised for displaying the quad tree structure.
	 */
	protected void onDebug() {
		if (!debug) {
			debug = true;
			redraw();
		} else {
			debug = false;
			redraw();
		}
	}
	
	/**
	 * Is called when the mouse is clicked (actually, when the mouse is
	 * released), and is passed the MouseEvent object for that click.
	 */
	@Override
	protected void onMousePressed(MouseEvent e) {
		// When the middle-mouse-button is pressed get the coordinates
		if (e.getButton() == 2) {
			Point point = e.getPoint();
			//System.out.println("mousePressed at " + point.getX() +" "+ point.getY());
			this.sX = point.x;
			this.sY = point.y;
			this.dragging = true;
			// When the left button is clicked, sample for closest point
		}
		if (e.getButton() == 1) {
			int x = e.getPoint().x;
			int y = e.getPoint().y;
			Location clickLocation = Location.newFromPoint(new Point(x, y), this.origin, this.scale);
			// Find quad tree in area then query the children for intersections
			System.out.printf("clicked at %f %f", clickLocation.x, clickLocation.y);
			QuadNode childNode = quadTreeRoot.getPosition(clickLocation.x, clickLocation.y);
			for (Node node : childNode.getNodeList()) {
				if (node.getLocation().isClose(clickLocation, 0.1)) {
					int nodeID = node.getId();
					String msg = String.format("Mouse clik at %d %d. Selected node ID: %d \nCrossing of:\t", x,y,nodeID);
					getTextOutputArea().setText(msg);
					List<Segment> segList = node.getSegments();
					for (Segment seg : segList) {
						getTextOutputArea().append(seg.getRoad().getName()+"\t");
					}
					activeNode = node;					
				}
			}
		}
	}
	
	/**
	 * It's called when the mouse is dragged across the map. Used for panning
	 */
	@Override
	protected void onMouseDrag(MouseEvent e) {
		if (e.getButton() == 2) {
			//System.out.printf("Mouse X Y: %d %d\n",e.getX(),e.getY());
			dX = (int) sX - e.getX();
			dY = (int) sY - e.getY();
			//System.out.printf("Delta X Y: %d %d\n",dX,dY);
			// Pans more when is zoomed out and less distance when zoomed in
			origin = origin.moveBy(dX/(dragLag*scale*.1), -dY/(dragLag*scale*.1));
			redraw();
		}
	}
	
	/**
	 * It's called when the mouse is released. Used to terminate the panning feature associate with the mouse behaviour
	 */
    @Override
    protected void onMouseReleased(MouseEvent e) {
    	dragging = false;
    }
    
	/**
	 * It's called when the mouse wheel is rotated. Used for zooming feature
	 */
    @Override
	protected void onMouseScroll(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches > 0){
		onMove(Move.ZOOM_IN);
		} else if (notches < 0 ) {
			onMove(Move.ZOOM_OUT);
		}
		redraw();
	}
		
	/**
	 * Is called whenever the search box is updated. Use getSearchBox to get the
	 * JTextField object that is the search box itself.
	 */
	@Override
	protected void onSearch() {
		String query = getSearchBox().getText();
		String scrMsg = "";
		Set<String> roadNames = new HashSet<String>();
		if (query.length() > 0) {
			TNode node = triRoot.diveToNode(query);
			if (node != null) {
				activeRoads = node.getRoads();
				for (Road road : activeRoads) {
					if (!roadNames.contains(road.getName())) {
						scrMsg = scrMsg + "\n" + road.getName();
						roadNames.add(road.getName());
					}
				}
				getTextOutputArea().setText(scrMsg);
			} else
				activeRoads.clear();
		}
	}

	/**
	 * Is called whenever a navigation button is pressed. An instance of the
	 * Move enum is passed, representing the button clicked by the user.
	 */
	@Override
	protected void onMove(Move m) {
		double zoomFactor = 1.25;
		int factor = 50;
		switch (m) {
		case NORTH: {
			origin = origin.moveBy(0, factor / scale);
			this.redraw();
			break;
		}
		case SOUTH: {
			origin = origin.moveBy(0, -factor / scale);
			this.redraw();
			break;
		}
		case EAST: {
			origin = origin.moveBy(factor / scale, 0);
			this.redraw();
			break;
		}
		case WEST: {
			origin = origin.moveBy(-factor / scale, 0);
			this.redraw();
			break;
		}
		case ZOOM_IN: {
			double deltaOrig = super.getDrawingAreaDimension().getHeight()
					/ scale * (zoomFactor - 1) / zoomFactor / 2;
			origin = new Location(origin.x + deltaOrig, origin.y - deltaOrig);
			scale = scale * zoomFactor;
			this.redraw();
			break;
		}
		case ZOOM_OUT: {
			scale = scale / zoomFactor;
			double deltaOrig = super.getDrawingAreaDimension().getHeight()
					/ scale * (zoomFactor - 1) / zoomFactor / 2;
			origin = new Location(origin.x - deltaOrig, origin.y + deltaOrig);
			this.redraw();
			break;
		}
		}
		return;
	}

	// Main method
	public static void main(String[] args) {
		new AucklandMapperGUI();
	}
}
