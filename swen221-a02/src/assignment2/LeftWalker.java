package assignment2;

import static maze.Direction.*;

import java.util.HashSet;
import java.util.Set;

import maze.*;

/**
 * This left walker does some smart thinking, it searches for the exit path and remembers though out the walking where it has been and can determine where should go next
 * 
 */
public class LeftWalker extends Walker {
	/**
	 * {@inheritDoc}
	 */

	Direction direction;
	Direction from;
	Direction nextDirection;
	Move move;
	int locX, locY;
	Set<AmazingCoord> visitedSet = new HashSet<AmazingCoord>();
	boolean rotating;
	boolean hadLeftWall;


	public LeftWalker() {
		super("Left Walker");
		locX = 0;
		locY = 0;
		direction = NORTH;
		from = SOUTH;
		rotating = false;
		hadLeftWall = false;
	}

	@Override
	protected Direction move(View v) {
		// Debug -----------------
		// Uncomment if you want the walker take one step at the time.
//		System.out.printf("X: %d Y: %d\n",locX, locY);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException ex) {
//			Thread.currentThread().interrupt();
//		}
		// -------------------------

		// Check if the walker has already visited the current cell
		for (AmazingCoord entry : visitedSet) {
			if (entry.getLocX() == locX && entry.getLocY() == locY && entry.containsFrom(from) && entry.containsTo(direction)) {
//				System.out.println("Walker has already been here");
				// Update current direction to one over (in clockwise)
				nextDirection = entry.getNextTo();
				if (nextDirection.equals(NORTH)) {
					from = SOUTH;
				} else if (nextDirection.equals(EAST)) {
					from = WEST;
				} else if (nextDirection.equals(WEST)) {
					from = EAST;
				} else {
					from = NORTH;
				}
				entry.addTo(nextDirection);
				hadLeftWall = false;
				updateXY(direction, nextDirection);
				direction = nextDirection;
				return nextDirection;
			}
		}

		// FORWARD ####################################################################
		
		if (hasLeftWall(direction, v) && !hasFrontWall(direction, v)) {
//			System.out.println("Go Straigth");
			hadLeftWall = true;
			nextDirection = moveFFW(direction);
			addCellActivity(locX, locY, from, nextDirection);
			updateXY(direction, nextDirection);
			direction = nextDirection;
			return nextDirection;
			
		} else if (!hadLeftWall && !hasLeftWall(direction, v) && !hasFrontWall(direction, v) && !hasRightWall(direction, v) && !hasBackWall(direction, v)) {
//			System.out.println("No front, left, right or back -> Step forward");
			nextDirection = moveFFW(direction);
			hadLeftWall = false;
			addCellActivity(locX, locY, from, nextDirection);
			updateXY(direction, nextDirection);
			direction = nextDirection;
			return direction;	

		// ROTATE RIGHT ####################################################################
			
		} else if (hasFrontWall(direction, v) && hasLeftWall(direction, v) && !hasRightWall(direction, v)) {
//			System.out.println("Rotate right!");
			hadLeftWall = true;
			nextDirection = rotateRight(direction);
			addCellActivity(locX, locY, from, nextDirection);
			updateXY(direction, nextDirection);
			direction = nextDirection;
			return direction;
			
		} else if (hasFrontWall(direction, v) && !hadLeftWall) {
//			System.out.println("Rotate right!");
			hadLeftWall = true;
			nextDirection = rotateRight(direction);
			addCellActivity(locX, locY, from, nextDirection);
			updateXY(direction, nextDirection);
			direction = nextDirection;
			return direction;
			
		} else if (hasRightWall(direction, v) && hasBackWall(direction, v)) {
//			System.out.println("Rotate right!");
			hadLeftWall = false;
			nextDirection = rotateRight(direction);
			addCellActivity(locX, locY, from, nextDirection);
			updateXY(direction, nextDirection);
			direction = nextDirection;
			return direction;
			
		// ROTATE LEFT ####################################################################
			
		} else if (!hasLeftWall(direction, v) && hasFrontWall(direction, v) && hadLeftWall) {
//				System.out.println("No left wall but had one prev step -> Roatate Left!");
				nextDirection = rotateLeft(direction);
				addCellActivity(locX, locY, from, nextDirection);
				updateXY(direction, nextDirection);
				direction = nextDirection;
				return direction;
								
		} else if (!hasLeftWall(direction, v) && hadLeftWall) {
//				System.out.println("No left wall but had one prev step -> Roatate Left!");
				nextDirection = rotateLeft(direction);
				addCellActivity(locX, locY, from, nextDirection);
				updateXY(direction, nextDirection);
				direction = nextDirection;
				return direction;
						
		// ROTATE BACK ####################################################################
				
		} else if (hasLeftWall(direction, v) && hasFrontWall(direction, v) && hasRightWall(direction, v)) {
//			System.out.println("Rotate back!");
			hadLeftWall = true;
			nextDirection = rotateBACK(direction);
			addCellActivity(locX, locY, from, nextDirection);
			updateXY(direction, nextDirection);
			direction = nextDirection;
			return direction;
		}
		return null;
	}

	/**
	 * This method will add an activity to the current cell
	 * @param locX locX is the current X position (relative to initial walker position)
	 * @param locY locY is the current Y position (relative to initial walker position)
	 * @param from direction from which the walker is coming from
	 * @param to direction to where the walker is intending to go next
	 */
	private void addCellActivity(int locX, int locY, Direction from, Direction to) {
		AmazingCoord newEntry = null;
		if (visitedSet.size() == 0 ){
			newEntry = new AmazingCoord(locX, locY, from, nextDirection);	
			visitedSet.add(newEntry);
		}
		for (AmazingCoord entry : visitedSet) {
			if (entry.getLocX() == locX && entry.getLocX() == locY){
				entry.setFrom(from);
				entry.addTo(to);
			} else {
				newEntry = new AmazingCoord(locX, locY, from, nextDirection);	
			} 
		}
		if (newEntry != null){
			visitedSet.add(newEntry);
		}
	}
	
	/**
	 * Check if there is a left wall
	 * 
	 * @param dir current walking direction
	 * @param v view from walker
	 * @return true if has a left wall
	 */
	private boolean hasLeftWall(Direction dir, View v) {
		if (dir.equals(NORTH)) {
			if (v.mayMove(Direction.WEST)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(WEST)) {
			if (v.mayMove(Direction.SOUTH)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(SOUTH)) {
			if (v.mayMove(Direction.EAST)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(EAST)) {
			if (v.mayMove(Direction.NORTH)) {
				return false;
			} else {
				return true;
			}
		} else
			return false;
	}
	
	
	/**
	 * Check if there is a right wall
	 * 
	 * @param dir current walking direction
	 * @param v view from walker
	 * @return true if has a right wall
	 */
	private boolean hasRightWall(Direction dir, View v) {
		if (dir.equals(NORTH)) {
			if (v.mayMove(Direction.EAST)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(WEST)) {
			if (v.mayMove(Direction.NORTH)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(SOUTH)) {
			if (v.mayMove(Direction.WEST)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(EAST)) {
			if (v.mayMove(Direction.SOUTH)) {
				return false;
			} else {
				return true;
			}
		} else
			return false;
	}


	/**
	 * Check if there is a front wall
	 * 
	 * @param dir current walking direction
	 * @param v view from walker
	 * @return true if has a front wall
	 */
	private boolean hasFrontWall(Direction dir, View v) {
		if (dir.equals(NORTH)) {
			if (v.mayMove(Direction.NORTH)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(WEST)) {
			if (v.mayMove(Direction.WEST)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(SOUTH)) {
			if (v.mayMove(Direction.SOUTH)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(EAST)) {
			if (v.mayMove(Direction.EAST)) {
				return false;
			} else {
				return true;
			}
		} else
			return false;
	}


	/**
	 * Check if there is a back wall
	 * 
	 * @param dir current walking direction
	 * @param v view from walker
	 * @return true if has a back wall
	 */
	private boolean hasBackWall(Direction dir, View v) {
		if (dir.equals(NORTH)) {
			if (v.mayMove(Direction.SOUTH)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(WEST)) {
			if (v.mayMove(Direction.EAST)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(SOUTH)) {
			if (v.mayMove(Direction.NORTH)) {
				return false;
			} else {
				return true;
			}
		}
		if (dir.equals(EAST)) {
			if (v.mayMove(Direction.WEST)) {
				return false;
			} else {
				return true;
			}
		} else
			return false;
	}

	/**
	 * Helper method for the walker to rotate left. Returns the new forward direction
	 * 
	 * @param dir
	 * @return true of false
	 */
	private Direction rotateLeft(Direction dir) {
		if (dir.equals(NORTH)) {
			from = EAST;
			return WEST;
		} else if (dir.equals(WEST)) {
			from = NORTH;
			return SOUTH;
		} else if (dir.equals(SOUTH)) {
			from = WEST;
			return EAST;
		} else {
			from = SOUTH;
			return NORTH;
		}
	}
	
	/**
	 * Helper method to rotate the walker around
	 * @param dir
	 * @return the new direction
	 */

	private Direction rotateRight(Direction dir) {
		if (dir.equals(NORTH)) {
			from = WEST;
			return EAST;
		} else if (dir.equals(EAST)) {
			from = NORTH;
			return SOUTH;
		} else if (dir.equals(SOUTH)) {
			from = EAST;
			return WEST;
		} else {
			from = SOUTH;
			return NORTH;
		}
	}
	
	/**
	 * Helper method to tell the walker to keep walking forward
	 * @param dir current direction
	 * @return the new direction
	 */
	
	private Direction moveFFW(Direction dir) {
		if (dir.equals(NORTH)) {
			from = SOUTH;
			return NORTH;
		} else if (dir.equals(WEST)) {
			from = EAST;
			return WEST;
		} else if (dir.equals(SOUTH)) {
			from = NORTH;
			return SOUTH;
		} else {
			from = WEST;
			return EAST;
		}
	}

	/**
	 * Helper method to rotate around the walker
	 * @param dir current direction
	 * @return the new direction
	 */
	private Direction rotateBACK(Direction dir) {
		if (dir.equals(NORTH)) {
			from = NORTH;
			return SOUTH;
		} else if (dir.equals(WEST)) {
			from = WEST;
			return EAST;
		} else if (dir.equals(SOUTH)) {
			from = SOUTH;
			return NORTH;
		} else {
			from = EAST;
			return WEST;
		}
	}

	/**
	 * Updates the coordinate X Y of the walker
	 * @param dir
	 * @param nextDir
	 */
	private void updateXY(Direction dir, Direction nextDir){
		if (dir.equals(NORTH) && nextDir.equals(NORTH)){
			locY += 1;
		} else if (dir.equals(NORTH) && nextDir.equals(SOUTH)){
			locY -= 1;
		} else if (dir.equals(NORTH) && nextDir.equals(WEST)) {
			locX -= 1;
		} else if (dir.equals(NORTH) && nextDir.equals(EAST)) {
			locX += 1;
		}
		
		if (dir.equals(EAST) && nextDir.equals(NORTH)){
			locY += 1;
		} else if (dir.equals(EAST) && nextDir.equals(SOUTH)){
			locY -= 1;
		} else if (dir.equals(EAST) && nextDir.equals(WEST)) {
			locX -= 1;
		} else if (dir.equals(EAST) && nextDir.equals(EAST)) {
			locX += 1;
		}
		
		if (dir.equals(SOUTH) && nextDir.equals(NORTH)){
			locY += 1;
		} else if (dir.equals(SOUTH) && nextDir.equals(SOUTH)){
			locY -= 1;
		} else if (dir.equals(SOUTH) && nextDir.equals(WEST)) {
			locX -= 1;
		} else if (dir.equals(SOUTH) && nextDir.equals(EAST)) {
			locX += 1;
		}
		
		if (dir.equals(WEST) && nextDir.equals(NORTH)){
			locY += 1;
		} else if (dir.equals(WEST) && nextDir.equals(SOUTH)){
			locY -= 1;
		} else if (dir.equals(WEST) && nextDir.equals(WEST)) {
			locX -= 1;
		} else if (dir.equals(WEST) && nextDir.equals(EAST)) {
			locX += 1;
		}
	}
	
}

enum Move {
	FFW, BACK, LEFT
}
