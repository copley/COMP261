package swen221.textadventure;

/**
 * This class represents a collection of coins in the game.
 * 
 * @author djp
 * 
 */
public class Coin implements Item {
	private int amount;
	
	public Coin(int amount) { this.amount = amount; }
	
	public void pickUp(Player p, Room r) {
		System.out.println("You picked up " + amount + " gold coins.  You're rich!");
		r.getItems().remove(this);	
		p.getItems().add(this);
	}
	
	public void prod(Player player, Room room) {
		System.out.println("You prodded the gold pieces.  You guessed it.  Nothing happened!");
	}
	
	public Room walkInto(Player player, Room room) {
		System.out.println("Don't be silly ... you can't walk into gold coins!");
		return room;
	}
	
	public String shortDescription() { return "Gold coins."; }
	
	public String longDescription() { return amount + " Gold coins."; }
}
