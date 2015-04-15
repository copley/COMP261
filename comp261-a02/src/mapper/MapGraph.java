package mapper;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.io.*;

/**
 * GraphMap class loads the files with Nodes, Roads and Segments
 * @author Diego Trazzi
 * @version 0.5
 */

public class MapGraph{

	// Map of all nodes, indexed by ID
	Map<Integer,Node> nodeMap = new HashMap<Integer,Node>();
	Map<Integer,Road> roadMap = new HashMap<Integer,Road>();
	
	// Map Boundaries (sets the to infinite so we can compare with data set)
	private double north = Double.NEGATIVE_INFINITY;
	private double south = Double.POSITIVE_INFINITY;
	private double east = Double.NEGATIVE_INFINITY;
	private double west = Double.POSITIVE_INFINITY;
	
	// Tri-Search 
	TNode triRoot = new TNode();
	List<Road> activeRoads = new ArrayList<Road>();
	
	public MapGraph(){
	}

	/**
	 * load all data from text files
	 */
	public void loadData(File nodes, File roads, File segments, File polygons){
		System.out.println("Loading map...");
		this.loadRoads(roads);
		this.loadNodes(nodes);
		this.loadSegments(segments);
		//this.loadPolygons(File polygons);
	}

	/**
	 * load all roads from text file and adds them to "roadMap"
	 */
	public void loadRoads(File roads){
		try{
		BufferedReader data = new BufferedReader(new FileReader(roads));
		// Removes first line
		data.readLine();
		while (data.ready()){
			// Read line and creates a new road
			String line = data.readLine();
			// Add new road to the roads map
			Road road = new Road(line);
			roadMap.put(road.getId(), road);
			triRoot.addRoad(road);
		}
		data.close();
		} catch (IOException e) {System.out.printf("Failed to open file: %s, %s", e.getMessage(), e.getStackTrace());}
	}
	
	/**
	 * load all nodes from text file and adds them to "nodeMap"
	 */
	public void loadNodes(File nodes){
		try{
			BufferedReader data = new BufferedReader(new FileReader(nodes));
			// Loop though all lines adding to nodes map
			while (data.ready()){
				// Read line and creates a new node
				String line = data.readLine();
				if (line.length() > 0){
					Node node = new Node(line);
					// Add the new node to the nodes map
					nodeMap.put(node.getId(), node);
				}
			}
			data.close();
		} catch (IOException e) {System.out.printf("Failed to open file: %s, %s", e.getMessage(), e.getStackTrace());}	
	}
	
	/**
	 * load all segments from text file and adds them to "nodeMap" & "roadMap"
	 */
	public void loadSegments(File segments){
		try{
			BufferedReader data = new BufferedReader(new FileReader(segments));
			// Removes first line
			data.readLine();
			// Creat Segments
			while (data.ready()){
				String line = data.readLine();
				Segment segment = new Segment(line, roadMap, nodeMap);
				// If road exists, add the segment
				Road road = segment.getRoad();
				if (road != null) {
					road.addSegment(segment);
				}
				// If node exists, add the segment
				Node nodeStart = segment.getNodeStart();
				if (nodeStart != null){
					nodeStart.addSegment(segment);
				}
				Node nodeEnd = segment.getNodeEnd();
				if (nodeEnd != null){
					nodeEnd.addSegment(segment);
				}
			}
			data.close();
		} catch (IOException e) {System.out.printf("Failed to open file: %s, %s", e.getMessage(), e.getStackTrace());}
	}
	
	/**
	 * utility to print out all the node entries
	 */
	public void printNodeMap(){
		for (Map.Entry<Integer, Node> entry : nodeMap.entrySet() ){
			System.out.printf("NodeID: %d\t Segments: %s\n", entry.getKey(), entry.getValue().getSegments());
		}
	}
	
	/**
	 * utility to print out all the road entries
	 */
	public void printRoadMap(){
		for (Map.Entry<Integer, Road> entry : roadMap.entrySet() ){
			System.out.printf("RoadID: %d\t Segments: %s\n", entry.getKey(), entry.getValue().getSegments());
		}
	}
	
	/**
	 * set the maximum and minimum location of the loaded data
	 */
	public double[] setBounds(){
		for (Node node : nodeMap.values()){
			if (node.getLocation().x < west) west = node.getLocation().x;  
			if (node.getLocation().x > east) east = node.getLocation().x;  
			if (node.getLocation().y > north) north = node.getLocation().y;  
			if (node.getLocation().y < south) south = node.getLocation().y;
		}
		return new double[] {west,east,north,south}; 
	}

	/**
	 * calls draw methods on all the nodes and segments
	 */
	public void draw(Graphics g, Location origin, double scale, double screenH, double screenW) {

		if (nodeMap.size() > 0) {
			// Filter out nodes which are not on screen to speed up drawing
			double topY = (origin.y*.97);
			double leftX = origin.x*.97;
			double bottomY = (origin.y - screenH/(scale*1.03));
			double rightX = (origin.x + screenW/(scale*1.03));

			for (Node node : nodeMap.values()) {
				if (node.getLocation().y < topY && node.getLocation().y > bottomY) {
					//if (node.getLocation().x > leftX) {
						if (node.getLocation().x > leftX && node.getLocation().x < rightX) {
						// if (node.getLocation().y > (origin.y - (600/scale))) {
						g.setColor(Color.BLUE);
						node.draw(g, origin, scale);
					}
				}
				if (node.getSegments().size() > 0) {
					g.setColor(Color.BLACK);
					for (Segment seg : node.getSegments()) {
						seg.draw(g, origin, scale);
					}
				}
			}
		}
	}
}
