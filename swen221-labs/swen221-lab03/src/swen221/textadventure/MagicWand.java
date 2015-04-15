package swen221.textadventure;

/**
 * This class represents a collection of coins in the game.
 * 
 * @author djp
 * 
 */
public class MagicWand implements Item {
		
	public void pickUp(Player p, Room r) {
		// if the smart is above 9 is a wizard
		if (p.getSmarts() > 9) {
			System.out.println("You are a wizard, you know how to handle this magic want, so you can pick it up and use it to defeat evil! ");
			r.getItems().remove(this);	
		p.getItems().add(this);
		} else {
			System.out.println("Sorry but you are not a wizard, you can't pick up or use this item.");
		}
	}
	
	public void prod(Player player, Room room) {
		if (player.getSmarts() > 9) {
			System.out.println("Teleportation active!");
//			this.walkInto(player, new Room("Hidden room", "This is a hidden room .. takes you nowhere"));
		} else {
			System.out.println("Sorry but you touch this!");
		}
	}
	
	public Room walkInto(Player player, Room room) {
		System.out.println("Don't be silly ... you can't walk into a magic wand!");
		return room;
	}
	
	public String shortDescription() { return "This is a magic wand."; }
	
	public String longDescription() { return "Witht his magic wand you can do magic!"; }
}
