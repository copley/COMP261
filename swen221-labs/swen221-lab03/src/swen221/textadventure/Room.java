package swen221.textadventure;

import java.util.*;

/**
 * This class represents a room in the adventure game.
 * 
 * @author djp
 *
 */
public class Room {
	private String name;
	private String description;
	private ArrayList<Item> items;	
	
	public Room(String name, String description) {
		this.name = name;
		this.description = description;
		this.items = new ArrayList<Item>();
	}
	
	/**
	 * Get the name of this room
	 */
	String getName() { return name; }
	
	
	/**
	 * Get the description of this room
	 */
	String getDescription() { return description; }
	
	/**
	 * Get access to the items in this room
	 * @return
	 */
	public List<Item> getItems() { return items; }		
}
