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
import javax.swing.SwingUtilities;

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

	// Restrictions
	Map<Integer, Restriction> restrictionMap = new HashMap<Integer, Restriction>();

	// Articulation points
	Set<Node> artPts = new HashSet<Node>();
	private boolean artPointDisp = false;
	private List<Node> unvisitedNodes = new ArrayList<Node>();
	// for the iterative version we need a stack
	private Stack<artPNode> artStack = new Stack<artPNode>();

	// A* find path
	private boolean findPaths = false;
	private Node firstNode;
	private Node secondNode;
	private int activeNodeRadius = 7;
	private ArrayList<Node> activeNodes;
	private List<Segment> activeSegments;
//	private List<SearchNode> pathToGoal;

	// Drawing Elements
	private Location origin;
	private double scale = 300;
	private int sX = -1;
	private int sY = -1;
	private int dX; // Delta X: movement from sX
	private int dY; // Delta Y: movement from sY
	private int dragLag = 50;
	private Node activeNode;
	private QuadNode quadTreeRoot = null;
	private Map<Integer, PolyColor> colorMap = new TreeMap<Integer, PolyColor>();
	private boolean debug = false;

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
		colorMap.put(40, new PolyColor(79, 162, 200)); // OCEAN
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
	 * Is called when the user has successfully selected a directory to load the data files from. File objects representing the four files of
	 * interested are passed to the method. The fourth File, polygons, might be null if it isn't present in the directory.
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
	protected void onLoad(File nodes, File roads, File segments, File polygons, File restrictions, File traffic) {
		this.loadData(nodes, roads, segments, polygons, restrictions, traffic);
		this.setOrigin();
	}

	/**
	 * Is called when the drawing area is redrawn and performs all the logic for the actual drawing, which is done with the passed Graphics object.
	 */
	@Override
	protected void redraw(Graphics g) {
		this.draw(g, origin, scale, super.getDrawingAreaDimension().getHeight(), super.getDrawingAreaDimension().getWidth());
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
	 * Function called when user activates the find path mode
	 */
	protected void onFindPaths() {
		if (findPaths) {
			getTextOutputArea().setText("Find path mode deactivated");
			findPaths = false;
			activeNode = null;
		} else {
			findPaths = true;
			getTextOutputArea().setText("Find path mode activated");
			activeNode = null;
			firstNode = null;
			secondNode = null;
		}
	}

	/**
	 * Function called when user presses on articulation pts button
	 */
	protected void onArtPts() {
		if (!artPointDisp) {
			getTextOutputArea().setText("Find articulation points RECURSIVE...");
			findArtPts(false);
			artPointDisp = true;
			redraw();
		} else {
			artPointDisp = false;
			redraw();
		}
	}
	
	/**
	 * Function called when user presses on articulation pts button
	 */
	protected void onArtPtsIter() {
		if (!artPointDisp) {
			getTextOutputArea().setText("Find articulation points ITERATIVE...");
			findArtPts(true);
			artPointDisp = true;
			redraw();
		} else {
			artPointDisp = false;
			redraw();
		}
	}

	/**
	 * Is called when the mouse is clicked (actually, when the mouse is released), and is passed the MouseEvent object for that click.
	 */
	@Override
	protected void onMousePressed(MouseEvent e) {

		if (SwingUtilities.isLeftMouseButton(e)) {
			sX = (int) e.getX();
			sY = (int) e.getY();
		}

		if (SwingUtilities.isLeftMouseButton(e)) {
			int x = e.getPoint().x;
			int y = e.getPoint().y;
			Location clickLocation = Location.newFromPoint(new Point(x, y), this.origin, this.scale);
			QuadNode childNode = quadTreeRoot.getPosition(clickLocation.x, clickLocation.y); // Find quad tree in area and return the children for
																								// intersections
			for (Node node : childNode.getNodeList()) {
				if (node.getLocation().isClose(clickLocation, 0.05)) { // find closest node to click
					if (!findPaths) { // find-path mode is inactive -- NORMAL MODE
						int nodeID = node.getId();
						String msg = String.format("Mouse clik at %d %d.\nSelected node ID: %d \nCrossing of:\t", x, y, nodeID);
						getTextOutputArea().setText(msg);
						List<Segment> segList = node.getSegments();
						Set<String> intersetionList = new HashSet<String>();
						for (Segment seg : segList) {
							intersetionList.add(seg.getRoad().getName());
						}
						for (String road : intersetionList) {
							getTextOutputArea().append(road + "  ");
						}
						getTextOutputArea().append("\n" + node.toString()); // append extra text for segments
						activeNode = node;
					} else if (findPaths) { // find path mode is active
						if (firstNode == null) {
							setActiveFirstNode(node);
							getTextOutputArea().setText(String.format("From: %d", node.getId()));
						} else if (firstNode != null && secondNode == null) {
							setActiveSecondNode(node);
							getTextOutputArea().append(String.format(" To: %d", node.getId()));
						} else {
							setActiveFirstNode(secondNode);
							setActiveSecondNode(node);
							getTextOutputArea().setText(String.format("From : %d To: %d", secondNode.getId(), firstNode.getId()));
						}
						if (firstNode != null && secondNode != null) { // if nodes are active look up for shortest path
							activeNodes = new ArrayList<Node>();
							activeNodes.add(firstNode);
							activeSegments = new ArrayList<Segment>();
							SearchNode arrivalNode = findPathToGoal(firstNode, secondNode);
							if (arrivalNode != null) { // check if there is possible route
								if (debug) {
									System.out.println("\nPath found (reverse order): ");
									System.out.printf("%d ", arrivalNode.getNode().getId());
								}
								activeNodes.add(firstNode);
								activeNodes.add(secondNode);
								while (arrivalNode.getFromNode() != null) {
									for (Segment seg : arrivalNode.getNode().getSegments()) { // selects only the segments which are on the path
										if ((seg.getNodeEnd().getId() == arrivalNode.getNode().getId() || seg.getNodeStart().getId() == arrivalNode.getNode().getId())
												&& ((seg.getNodeStart().getId() == arrivalNode.getFromNode().getNode().getId()) || seg.getNodeEnd().getId() == arrivalNode.getFromNode().getNode()
														.getId())) {
											activeSegments.add(seg);
										}
									}
									activeNodes.add(arrivalNode.getNode());
									arrivalNode = arrivalNode.getFromNode();
									if (debug) System.out.printf("%d ", arrivalNode.getNode().getId());
								}
								Collections.reverse(activeNodes); // Reverse the node list ;-)
							}
						}
					}
				}
			}
		}
	}

	/**
	 * It's called when the mouse is dragged across the map. Used for panning
	 */
	@Override
	protected void onMouseDrag(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			dX = (int) e.getX() - sX;
			dY = (int) e.getY() - sY;
			// Pans more when is zoomed out and less distance when zoomed in
			origin = origin.moveBy(-dX / (dragLag * scale * .1), dY / (dragLag * scale * .1));
			redraw();
		}
	}

	/**
	 * It's called when the mouse is released. Used to terminate the panning feature associate with the mouse behaviour
	 */
	@Override
	protected void onMouseReleased(MouseEvent e) {
	}

	/**
	 * It's called when the mouse wheel is rotated. Used for zooming feature
	 */
	@Override
	protected void onMouseScroll(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		double zoomFactor = 1.25;
		// mouse position 
		int x = e.getPoint().x;
		int y = e.getPoint().y;
		Location locBeforeZoom = Location.newFromPoint(new Point(x, y), this.origin, this.scale);

		if (notches > 0) {
			if (scale < 1000) {
				scale = scale * zoomFactor;
				Location locAfterZoom = Location.newFromPoint(new Point(x, y), this.origin, this.scale);
				origin = new Location(origin.x - (locAfterZoom.x-locBeforeZoom.x), origin.y - (locAfterZoom.y-locBeforeZoom.y));
				this.redraw();
			}
		} else if (notches < 0) {
			if (scale > 5) {
				scale = scale / zoomFactor;
				Location locAfterZoom = Location.newFromPoint(new Point(x, y), this.origin, this.scale);
				origin = new Location(origin.x - (locAfterZoom.x-locBeforeZoom.x), origin.y - (locAfterZoom.y-locBeforeZoom.y));
				this.redraw();
			}
		}
		redraw();
	}

	/**
	 * Is called whenever the search box is updated. Use getSearchBox to get the JTextField object that is the search box itself.
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
	 * Is called whenever a navigation button is pressed. An instance of the Move enum is passed, representing the button clicked by the user.
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
			if (scale < 1000) {
				scale = scale * zoomFactor;
				double deltaOrig = super.getDrawingAreaDimension().getHeight() / scale * (zoomFactor - 1) / zoomFactor / 2;
				origin = new Location(origin.x + deltaOrig, origin.y - deltaOrig);
				this.redraw();
			}
			break;
		}
		case ZOOM_OUT: {
			if (scale > 5) {
				scale = scale / zoomFactor;
				double deltaOrig = super.getDrawingAreaDimension().getHeight() / scale * (zoomFactor - 1) / zoomFactor / 2;
				origin = new Location(origin.x - deltaOrig, origin.y + deltaOrig);
				this.redraw();
			}
			break;
		}
		}
		return;
	}

	/**
	 * Sets the first active node
	 *
	 * @param Node
	 *            node
	 */
	private void setActiveFirstNode(Node node) {
		this.firstNode = node;
	}

	/**
	 * Sets the second active node
	 *
	 * @param Node
	 *            node
	 */
	private void setActiveSecondNode(Node node) {
		this.secondNode = node;
	}

	/**
	 * Load all data from text files
	 *
	 * @param nodes
	 *            nodes file
	 * @param roads
	 *            roads file
	 * @param segments
	 *            segments file
	 * @param polygons
	 *            polygons file
	 */
	private void loadData(File nodes, File roads, File segments, File polygons, File restrictions, File traffic) {
		getTextOutputArea().append("Loading from " + roads + "\n");
		this.loadRoads(roads);
		getTextOutputArea().append("Loading from " + nodes + "\n");
		this.loadNodes(nodes);
		getTextOutputArea().append("Loading from " + segments + "\n");
		this.loadSegments(segments);
		if (polygons != null) {
			getTextOutputArea().append("Loading from " + polygons + "\n");
			this.loadPolygons(polygons);
		}
		if (restrictions != null) {
			getTextOutputArea().append("Loading from " + restrictions + "\n");
			this.loadRestrictions(restrictions);
		}
		if (traffic != null) {
			getTextOutputArea().append("Loading from " + traffic + "\n");
			this.loadTraffic(traffic);
		}
	}

	/**
	 * try to load the roads from text file and adds them to "roadMap". In case of fail will print out path file
	 *
	 * @param roads
	 *            road file
	 */
	private void loadRoads(File roads) {
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
			System.out.printf("Failed to open file: %s, %s", e.getMessage(), e.getStackTrace());
		}
	}

	/**
	 * load all nodes from Node file and adds them to "nodeMap"
	 *
	 * @param nodes
	 *            node file
	 */
	private void loadNodes(File nodes) {
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
			for (Node node : nodeMap.values()) {
				quadTreeRoot.addNode(node);
			}
			data.close();
		} catch (IOException e) {
			System.out.printf("Failed to open file: %s, %s", e.getMessage(), e.getStackTrace());
		}
	}

	/**
	 * load all segments from text file and adds them to "nodeMap" & "roadMap"
	 *
	 * @param segments
	 *            Segments file
	 */
	private void loadSegments(File segments) {
		try {
			BufferedReader data = new BufferedReader(new FileReader(segments));
			// Removes first line
			data.readLine();
			// Create Segments
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
					nodeStart.addSegmentOut(segment);
				}
				Node nodeEnd = segment.getNodeEnd();
				if (nodeEnd != null) {
					nodeEnd.addSegment(segment);
					nodeEnd.addSegmentIn(segment);
				}
			}
			data.close();
		} catch (IOException e) {
			System.out.printf("Failed to open file: %s, %s", e.getMessage(), e.getStackTrace());
		}
	}

	/**
	 * load polygon file into memory
	 *
	 * @param polygons
	 *            polygon file
	 */
	private void loadPolygons(File polygons) {
		Integer polyType = 0;
		String label = "";
		Integer endLevel = 0;
		Integer cityIdx = 0;
		List<Location> coordList = new ArrayList<Location>();
		Set<Integer> typeSet = new TreeSet<Integer>();

		try {
			BufferedReader data = new BufferedReader(new FileReader(polygons));
			while (data.ready()) {
				String line = data.readLine();
				if (line.startsWith("Type=")) {
					polyType = Integer.parseInt(line.substring(7), 16);
					// Add polyType to a tmp set, to check how many colors to use
					typeSet.add(polyType);
				} else if (line.startsWith("Label=")) {
					label = line.substring(6);
					 if (debug) System.out.printf("Label: %s ",label);
				} else if (line.startsWith("EndLevel=")) {
					endLevel = Integer.parseInt(line.substring(9));
					if (debug) System.out.printf("End Level: %d ",endLevel);
				} else if (line.startsWith("CityIdx=")) {
					cityIdx = Integer.parseInt(line.substring(8));
					if (debug) System.out.printf("End Level: %d ",endLevel);
				} else if (line.startsWith("Data0=")) {
					String strCoords = line.substring(6);
					coordList.clear();
					// Splits the coordList String into tuples, then further separates each tuple in X and Y
					String[] coordArray = strCoords.substring(1, strCoords.length() - 2).split("\\),\\(", -1);
					for (int i = 0; i < coordArray.length; i++) {
						Double coordX = Double.parseDouble(coordArray[i].split(",")[0]);
						Double coordY = Double.parseDouble(coordArray[i].split(",")[1]);
						coordList.add(Location.newFromLatLon(coordX, coordY));
						if (debug) System.out.printf("Coords: x: %f \t y: %f\n",coordX, coordY);
					}
					Polygon polyShape = new Polygon(polyType, endLevel, label, cityIdx, coordList, colorMap.get(polyType));
					polySet.add(polyShape);
				}
			}
			data.close();
			// Display how many types there are in the GPS polygon file
			// printIntSet(typeSet);
		} catch (IOException e) {
			System.out.printf("Failed to open file %s, %s", e.getMessage(), e.getStackTrace());
		}
	}

	/**
	 * Load restriction file -- challenge --
	 * 
	 * @param restrictions
	 *            restriction file
	 */
	private void loadRestrictions(File restrictions) {
		try {
			BufferedReader data = new BufferedReader(new FileReader(restrictions));
			data.readLine();
			while (data.ready()) {
				String line = data.readLine();
				String[] l = line.split("\t");
				int nodeID = Integer.parseInt(l[2]);
				Restriction restriction = new Restriction(line);
				restrictionMap.put(nodeID, restriction);
			}
			data.close();
		} catch (IOException e) {
			System.out.printf("filed to open file %s, %s", e.getMessage(), e.getStackTrace());
		}
	}
	
	private void loadTraffic(File traffic){
		try {
			BufferedReader data = new BufferedReader(new FileReader(traffic));
			data.readLine();
			while (data.ready()) {
				String line = data.readLine();
				String[] l = line.split("\t");
				double lat = Double.parseDouble(l[1]);
				double lon = Double.parseDouble(l[0]);
				Location location = Location.newFromLatLon(lat, lon);
				QuadNode childNode = quadTreeRoot.getPosition(location.x, location.y);
				
				for(Node node : childNode.getNodeList()) {
					Location nodeLocation = node.getLocation();
					if(nodeLocation.x == location.x && nodeLocation.y == location.y) {
						node.setTrafficLights(true);
					}
				}
			}
			data.close();
		} catch (IOException e) {
			System.out.printf("filed to open file %s, %s", e.getMessage(), e.getStackTrace());
		}
	}
	
	/**
	 * Find the center of the graph from the bounds array and screen size
	 */
	private void setOrigin() {
		double[] bounds = this.setBounds();
		origin = new Location(bounds[0], bounds[2]);
		scale = Math.min(GUI.DEFAULT_DRAWING_WIDTH*2 / (bounds[1] - bounds[0]), GUI.DEFAULT_DRAWING_HEIGHT*2 / (bounds[2] - bounds[3]));
	}

	/**
	 * set the maximum and minimum location of the loaded data
	 */
	private double[] setBounds() {
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
	 * Search the shortest distance between two nodes utilizing the A* algorithm
	 * 
	 * @param startNode
	 * @param arrivalNode
	 * @return
	 */
	private SearchNode findPathToGoal(Node startNode, Node arrivalNode) {

		Queue<SearchNode> fringe = new PriorityQueue<SearchNode>();
		Set<SearchNode> visitedSearchNodeSet = new HashSet<SearchNode>();
		Set<Node> visitedNodeSet = new HashSet<Node>();

		// Add the startNode to the Priority Queue
		SearchNode first = new SearchNode(startNode, null, 0, startNode.distanceBetween(arrivalNode));
		fringe.add(first);

		while (fringe.size() > 0) { // repeat until fringe is not empty

			SearchNode searchNode = fringe.poll(); // dequeue the highest node, and process it if is not been visited
			if (!visitedSearchNodeSet.contains(searchNode)) { // if node is not being visited yet
				visitedSearchNodeSet.add(searchNode); // mark the node as visited adding to visitedSet
			}

			if (searchNode.getNode().equals(arrivalNode)) { // if the node equals goal, add exit here
				return searchNode;
			}

			double costToHere = searchNode.getCostSoFar(); // calculates the cost to here

			if (searchNode.getNode() != null) { // Add this node to the visited set
				visitedNodeSet.add(searchNode.getNode());
			}

			Map<Node, Segment> neighbours = searchNode.getNode().getNeighbours(); // add each neighbor out of this searchNode to fringe

			if (neighbours.size() > 0) {

				// Check the restrictions
				for (Node entry : neighbours.keySet()) {
					Restriction restriction = restrictionMap.get(entry.getId()); // find the restrictions on this "entry" node
					// check if the road we sit on is one way, with from node, this is arrival node, so we can check if thorough the segments
					for (Segment seg : entry.getSegments()) {
						// check if we have restriction on this node
						if (restriction != null) { // check if restriction are clashing with intended path
							if (debug) System.out.printf("Restriction between nodeID %d, to nodeID: %d\n", seg.getNodeStart().getId(), seg.getNodeEnd().getId());
							if (restriction.getNodeID() == entry.getId() // same ID node
									&& restriction.getFromNodeID() == searchNode.getNode().getId() // same from ID node
									&& restriction.getToRoadID() == seg.getRoad().getId()) { // same to ID road
								// skip segment
								visitedNodeSet.add(entry);
								break;
							} else {
								if (debug) System.out.println("THERE ARE RESTRICTIONS BUT SEGMENT COULD BE VALID");
							}
						}
					}
				}

				// Iterate though nodes considering the restrictions above
				for (Node entry : neighbours.keySet()) {
					for (Segment seg : entry.getSegments()) {
						if (seg.getRoad().isOneway()) { // check if this segment of road is one way, if so, only select segmentsIN
							if (seg.getNodeEnd().equals(entry)) { // if nodeEnd of segment is == entry means the segment is going into this node
								Double segLength = neighbours.get(entry).getLength();
								Double estDistance = entry.distanceBetween(arrivalNode);
								if (entry.hasLights()) {
									estDistance += 2;
								}
								if (!visitedNodeSet.contains(entry)) { // if neighbor is not been visited
									SearchNode newNeighbour = new SearchNode(entry, searchNode, costToHere + segLength, costToHere + estDistance);
									fringe.add(newNeighbour);
								}
							}
						} else { // the road is not one way
							Double segLength = neighbours.get(entry).getLength();
							Double estDistance = entry.distanceBetween(arrivalNode);
							if (entry.hasLights()) {
								estDistance += 2;
							}
							if (!visitedNodeSet.contains(entry)) { // if neighbor is not been visited
								SearchNode newNeighbour = new SearchNode(entry, searchNode, costToHere + segLength, costToHere + estDistance);
								fringe.add(newNeighbour);
							}
						}
					}
				}
			}
		}
		return null; // there are no neighbors
	}


	
	/**
	 * Finds the articulation points in the graph
	 */
	private void findArtPts(boolean isIterative) {

		// keep track of how long it takes
		long startTime = System.nanoTime();

		// copy all the nodes into the articulation unVisited set
		for (Node node : nodeMap.values()) {
			unvisitedNodes.add(node);
		}

		// creates a new empty set for the articulation points (stored up-up, as a field variable, as it needs to be accessed by the recArtPts too)
		while (unvisitedNodes.size() > 0) {// while node-visited has elements keep running this algorithm
			
			// creates var to contain the numb of subtree
			int numSubTree = 0;
			Node startNode = null; // creates the root node
			int count = 0; // creates a counter
			
			// sets depth to infinity for all nodes
			for (int i=0; i<unvisitedNodes.size(); i++ ) {
				unvisitedNodes.get(i).resetDepth();
				if (count == 0) {
//				if (unvisitedNodes.get(i).getId() == 14793) {
					startNode = unvisitedNodes.get(i);
				}
				count++;
			}
//			System.out.println("START NODE ID: " + startNode.getId());

			// root case
			for (Node node : startNode.getAllNeighbours().keySet()) {
				unvisitedNodes.remove(startNode); // removes the startNode from the unVisited list
				if (node.getDepth() == Integer.MAX_VALUE) {
					if (isIterative) {
						iterArtPts(node, startNode); // iterative version
					} else {
						recArtPts(node, 1, startNode); // recursive version
					}
					numSubTree++;
				}
			}
			if (numSubTree > 1) {
				artPts.add(startNode);
			}
		}
		
		// stops the time and computes the execution time
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		getTextOutputArea().setText(String.format("Articulation point with recursion method took: %d milliseconds > ", duration));
	}

	private int recArtPts(Node node, int depth, Node fromNode) {
		int reachBack = depth;
		node.setDepth(depth);
		for (Node neighbourNode : node.getAllNeighbours().keySet()) {
			if (!neighbourNode.equals(node)) {
				if (neighbourNode.getDepth() < Integer.MAX_VALUE) {
					reachBack = Math.min(neighbourNode.getDepth(), reachBack);
				} else {
					int childReach = recArtPts(neighbourNode, depth + 1, node);
					reachBack = Math.min(childReach, reachBack);
					if (childReach >= depth) {
						artPts.add(node);
					}
				}
			}
		}
		unvisitedNodes.remove(node);
		return reachBack;
	}

	private void iterArtPts(Node firstNode, Node root) {
		
		artStack.push(new artPNode(firstNode, 1, new artPNode(root, 0, null)));
		
		while (!artStack.isEmpty()) {
			artPNode elem = artStack.peek(); // looks at previous artNode
			Node node = elem.getNode(); // looks at the node contained in artNode

			if (elem.children() == null) { // first time we visit the node: children are empty
				node.setDepth(elem.getDepth()); // set the node depth from the element depth
				elem.setReach(elem.getDepth()); // set the element reach to its depth
				ArrayDeque<Node> children = new ArrayDeque<Node>();
				elem.setChildren(children);
				
				for (Node neighbour : node.getAllNeighbours().keySet()) {
					if (neighbour != elem.getParent().getNode()) {
						children.offer(neighbour);
					}
				}
			
			} else if (!elem.children().isEmpty()) {
				Node child = elem.children().poll();				
				if (child.getDepth() < Integer.MAX_VALUE) {
					elem.setReach(Math.min(elem.getReach(), child.getDepth()));
				} else {
					artStack.push(new artPNode(child, node.getDepth() + 1, elem));
				}
			} else {
				if (node != firstNode) {
					if (elem.getReach() >= elem.getParent().getDepth()) {
						artPts.add(elem.getParent().getNode());
					}
					elem.getParent().setReach(Math.min(elem.getParent().getReach(), elem.getReach()));
				}
				unvisitedNodes.remove(artStack.pop().getNode());
			}
		}
	}

	/**
	 * calls draw methods on all the nodes and segments
	 */
	private void draw(Graphics g, Location origin, double scale, double screenH, double screenW) {

		// Pass origin and scale to the top, so we can use them to know where the user is clicking without having to call draw
		this.origin = origin;
		this.scale = scale;
		int dotSize = (int) Math.min(.2 * (scale), 7);

		if (nodeMap.size() > 0) {

			// normal mode: filter out nodes which are not on screen to speed up drawing
			double topY = (origin.y);
			double leftX = origin.x;
			double bottomY = (origin.y - screenH / (scale));
			double rightX = (origin.x + screenW / (scale));
			// draw polygons
			for (Polygon poly : polySet) {
				for (Integer entry : colorMap.keySet()) {
					poly.draw(g, origin, scale, entry);
				}
			}
			// draw segments
			for (Road road : roadMap.values()) {
				if (activeRoads.contains(road)) {
					g.setColor(Color.RED);
					for (Segment seg : road.getSegments()) {
						seg.draw(g, origin, scale, 4);
					}
				} else if (road.isOneway()) { // one way roads are colored in pink
					g.setColor(Color.PINK);
					for (Segment seg : road.getSegments()) {
						seg.draw(g, origin, scale, 2);
					}
				} else {
					g.setColor(Color.BLACK);
					for (Segment seg : road.getSegments()) {
						seg.draw(g, origin, scale, 1);
					}
				}
			}
			// draw nodes
			for (Node node : nodeMap.values()) {
				if (artPointDisp) { // articulation points display on
					if (!artPts.contains(node)) { // check if node belongs to articulation points
						if (node.getLocation().y < topY && node.getLocation().y > bottomY) {
							if (node.getLocation().x > leftX && node.getLocation().x < rightX) {
								if (node == activeNode) {
									g.setColor(Color.RED);
								} else {
									g.setColor(Color.BLUE);
								}
								node.draw(g, origin, scale, dotSize, 0, super.drawing);
							}
						}
					}
				} else { // articulation points display off
					if (node.getLocation().y < topY && node.getLocation().y > bottomY) {
						if (node.getLocation().x > leftX && node.getLocation().x < rightX) {
							if (node == activeNode) {
								g.setColor(Color.RED);
							} else if (node.hasLights()) {
								g.setColor(Color.ORANGE);
							} else {
								g.setColor(Color.BLUE);
							}
							node.draw(g, origin, scale, dotSize, 0, super.drawing);
						}
					}
				}
			}

			// debug mode : draw quads with recursion
			if (debug && quadTreeRoot != null) {
				quadTreeRoot.drawRec(quadTreeRoot, g, origin, scale);
			}

			// find path mode : draw shortest path between two nodes
			if (findPaths && firstNode != null && secondNode != null && activeNodes.size() > 0 && activeSegments.size() > 0) { // Draw start node if
																																// findPath is active
				getTextOutputArea().setText("A* shortest path starts from: ");
				int colorGradStepSeg = (255 / activeSegments.size());
				int colorRedGradSeg = 255;
				int colorGreenGradSeg = 0;
				for (Segment seg : activeSegments) { // draw segments
					g.setColor(new Color(colorRedGradSeg, colorGreenGradSeg, 0));
					seg.draw(g, origin, scale, 4);
					colorRedGradSeg = colorRedGradSeg - colorGradStepSeg;
					colorGreenGradSeg = colorGreenGradSeg + colorGradStepSeg;
				}
				// adding road names and distance to the UI
				Set<String> roadNames = new HashSet<String>();
				Double totalDist = 0.0;
				for (int i = 0; i < activeNodes.size() - 1; i++) {
					for (Segment seg : activeNodes.get(i).getSegments()) {
						if (seg.getNodeEnd().equals(activeNodes.get(i + 1))) { // if it's an end node
							String roadName = seg.getRoad().getName();
							double dist = seg.getLength();
							if (!roadNames.contains(roadName)) {
								getTextOutputArea().append(String.format("%s (%.2fkm) > ", roadName, dist));
								roadNames.add(roadName);
								totalDist += dist;
							}
						} else if (seg.getNodeStart().equals(activeNodes.get(i + 1))) { // if it's a start node
							String roadName = seg.getRoad().getName();
							double dist = seg.getLength();
							if (!roadNames.contains(roadName)) {
								getTextOutputArea().append(String.format("%s (%.2fkm) > ", roadName, dist));
								roadNames.add(roadName);
								totalDist += dist;
							}
						}
					}
				}
				getTextOutputArea().append(String.format(" Arrival! (total distance: %.2fkm)", totalDist));
				// drawing nodes
				for (Node node : activeNodes) { // draw nodes
					g.setColor(Color.YELLOW);
					node.draw(g, origin, scale, activeNodeRadius, 0, super.drawing);
				}
			}
			if (findPaths && firstNode != null) { // Draw start node if findPath is active
				firstNode.draw(g, origin, scale, activeNodeRadius, 1, super.drawing);
			}
			if (findPaths && secondNode != null) { // Draw arrival node if findPath is active
				secondNode.draw(g, origin, scale, activeNodeRadius, 2, super.drawing);
			}

			// articulation points mode : draw those nodes which are articulation points
			if (artPointDisp) {
				for (Node node : artPts) {
					g.setColor(Color.MAGENTA);
					node.draw(g, origin, scale, dotSize * 2, 0, super.drawing);
				}
			}
		}
	}

	// Main method
	public static void main(String[] args) {
		new AucklandMapperGUI();
	}
}
