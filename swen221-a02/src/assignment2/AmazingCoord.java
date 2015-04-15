package assignment2;

import static maze.Direction.*;

import java.util.ArrayList;
import java.util.List;

import maze.*;

/**
 * This class stores information for the walker path so it can memorise where has been already and make smarter decisions on where to walk next
 * @author diego
 *
 */
public class AmazingCoord {

	private int locX;
	private int locY;
	private List<Direction> fromList = new ArrayList<Direction>();
	private List<Direction> toList = new ArrayList<Direction>();
	
	public AmazingCoord(int locX, int locY, Direction from, Direction to){
		this.locX = locX;
		this.locY = locY;
		if (!toList.contains(from)) {
			toList.add(from);
		}
		if (!toList.contains(to)) {
			toList.add(to);
		}
	}
	
	public int getLocX() {
		return locX;
	}

	public void setLocX(int locX) {
		this.locX = locX;
	}

	public int getLocY() {
		return locY;
	}

	public void setLocY(int locY) {
		this.locY = locY;
	}

	public List<Direction> getFrom() {
		return fromList;
	}
	
	public boolean containsFrom(Direction from) {
		if (fromList.contains(from)) {
			return true;
		} return false;
	}
	
	public boolean containsTo(Direction to) {
		if (toList.contains(to)) {
			return true;
		} return false;
	}

	public void setFrom(Direction from) {
		if (!fromList.contains(from)) {
			fromList.add(from);
		}
	}
	
	public Direction getTo() {
		// get the first item
		return toList.get(0);
	}
	
	public Direction getNextTo() {
		// get the last item of the list, then return the next location over
		Direction lastDir = toList.get(toList.size()-1);
		if (lastDir.equals(NORTH)) {
			return EAST;
		}
		if (lastDir.equals(EAST)) {
			return SOUTH;
		}
		if (lastDir.equals(SOUTH)) {
			return WEST;
		}
		if (lastDir.equals(WEST)) {
			return NORTH;
		}
		return null;
	}

	public void addTo(Direction to) {
		if (!toList.contains(to)) {
			toList.add(to);
		}
	}
	
}
