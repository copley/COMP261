package mapper;
import java.util.*;


public class Road {

	// Field variables
	private int id;
	private String name;
	private String city;
	private boolean oneway;
	private int speed;
	private int roadclass;
	private boolean notforcar;
	private boolean notforpede;
	private boolean notforbicy;
	private List<Segment> segments = new ArrayList<Segment>();
	
	// Constructor from String
	public Road(String line) {
		String l[] = line.split("\t");
		id = Integer.parseInt(l[0]);
		name = l[2];
		city = l[3];
		oneway = l[4].equals("1");
		speed = Integer.parseInt(l[5]);
		roadclass = Integer.parseInt(l[6]);
		notforcar = l[7].equals("1");
		notforpede = l[8].equals("1");
		notforbicy = l[9].equals("1");
	}
	
	// Add a segment
	public void addSegment(Segment seg) {
		segments.add(seg);
	}
	
	// Get id
	public int getId() {
		return id;
	}
	
	// Get segments
	public List<Segment> getSegments () {
		return segments;
	}
	
	// Get street name
	public String getName() {
		return name;
	}
	
	// Get city name
	public String getCity() {
		return city;
	}

	// Get, is one way ?
	public boolean isOneway() {
		return oneway;
	}
	
	// Get speed limit
	public int getSpeed() {
		return speed;
	}
	
	// Get road class
	public int getRoadclass() {
		return roadclass;
	}
	
	// Get not for cars
	public boolean getNotforcars() {
		return notforcar;
	}
	
	// Get not for pedestrians
	public boolean getNotforpede() {
		return notforpede;
	} 
	
	// Get not for bicycles
	public boolean getNotforbicy() {
		return notforbicy;
	}
}
