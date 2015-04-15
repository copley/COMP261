package swen221.textadventure;

/**
 * This class represents a book.
 * 
 * @author Diego Trazzi
 * 
 */
public class Book implements Item {
	
	public Book() {
	}
	
	public void pickUp(Player p, Room r) {
		System.out.println("You picked up the book.");
		r.getItems().remove(this);	
		p.getItems().add(this);
	}
	
	public void prod(Player player, Room room) {
		if (player.getSmarts() > 5){			
			System.out.println("You decrypted the book. It's a manual on how to use the USB Key !!");
		} else {
			System.out.println("This is a encrypted book, you are not smart enough");			
		}
	}
	
	public Room walkInto(Player player, Room room) {
		System.out.println("Don't be silly ... you can't walk into a book!");
		return room;
	}
	
	public String shortDescription() { return "A book."; }
	
	public String longDescription() { return "A book which contains useful infomration."; }
}
