package swen221.textadventure;

/**
 * This class represents a key item.
 * 
 * @author Diego Trazzi
 * 
 */
public class Key implements Item {
	
	public Key() { 
		}
	
	public void pickUp(Player p, Room r) {
		System.out.println("You picked up a key. It's a USB key!!!");
		r.getItems().remove(this);	
		p.getItems().add(this);
	}
	
	public void prod(Player player, Room room) {
		System.out.println("You prodded the USB key. You guessed it.  Nothing happened!");
	}
	
	public Room walkInto(Player player, Room room) {
		System.out.println("Don't be silly ... you can't walk into a USB key.");
		return room;
	}
	
	public String shortDescription() { return "Usb key."; }
	
	public String longDescription() { return " Usb key."; }
}
