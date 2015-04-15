package swen221.textadventure;

import java.util.*;

/**
 * This class records various pieces of information about the game player,
 * including: their name, how strong they are, and smart they are, and what
 * they're carrying.
 * 
 * @author djp
 * 
 */
public class Player {
	/**
	 * Name of this player
	 */
	private String name;
	
	/**
	 * How strong is this player (from 1 to 10)
	 */
	private int strength;
	
	/**
	 * How smart is this player (from 1 to 10)
	 */
	private int smarts;
	
	/**
	 * Items the player is carrying
	 */
	private ArrayList<Item> items;
	
	public Player(String name, int strength, int smarts) {
		this.name = name;
		this.strength = strength;
		this.smarts = smarts;
		this.items = new ArrayList<Item>();
	}
	
	/**
	 * Get name of this player
	 */
	public String getName() { return name; }
	
	/**
	 * How player strength (from 1 to 10)
	 */
	public int getStrength() { return strength; }
	
	/**
	 * get player smarts (from 1 to 10)
	 */
	public int getSmarts() { return smarts; }
	
	/**
	 * Get items the player is carrying
	 * 
	 * @return
	 */
	public List<Item> getItems() { return items; }
	
	/**
	 * Add items to the player is carrying
	 * 
	 * @return
	 */
	public void addItem(Item item) {
		items.add(item); 
		}
}
