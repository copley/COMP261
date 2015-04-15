package swen221.textadventure;

/**
 * This class represents an item stored in a room.
 * 
 * @author djp
 *
 */
public interface Item {

	/**
	 * Pick up this item from the room.
	 * 
	 * @param player
	 * @param room
	 * @return
	 */
	public void pickUp(Player player, Room room);

	/**
	 * Prod this item (this might cause it to do something)
	 * 
	 * @param player
	 * @param room
	 * @return
	 */
	public void prod(Player player, Room room);

	/**
	 * Try to walk into this item (if it's a door, you'll go through it!). This method should return the room the player
	 * is now in. If no room change has taken place, then it just returns the room parameter given.
	 * 
	 * @param player
	 * @param room
	 * @return
	 */
	public Room walkInto(Player player, Room room);

	/**
	 * Get a short description of the item.
	 * 
	 * @return
	 */
	public String shortDescription();

	/**
	 * Get a long description of the item.
	 * 
	 * @return
	 */
	public String longDescription();
}
