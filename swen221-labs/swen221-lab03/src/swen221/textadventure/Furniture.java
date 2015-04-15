package swen221.textadventure;

/**
 * This class represents an item of furniture in the game.
 * 
 * @author djp
 * 
 */
public class Furniture implements Item {
	private String shortDescription;
	private String longDescription;
	
	public Furniture(String shortDescription, String longDescription) { 
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
	}
	
	public void pickUp(Player p, Room r) {
		System.out.println("You can't pick up furniture you idiot!");
	}
	
	public void prod(Player player, Room room) {
		System.out.println("Nothing happens (as usual)!");
	}
	
	public Room walkInto(Player player, Room room) {
		System.out.println("Don't be silly ... you can't walk into furniture!");
		return room;
	}
	
	public String shortDescription() { return shortDescription; }
	
	public String longDescription() { return longDescription; }
}
