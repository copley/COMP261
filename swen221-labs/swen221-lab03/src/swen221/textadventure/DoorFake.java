package swen221.textadventure;

/**
 * This class represents a fake door in the game. 
 * 
 * @author Diego Trazzi
 * 
 */
public class DoorFake extends Door {
	
	@SuppressWarnings("unused")
	private String name;
	@SuppressWarnings("unused")
	private Room oneSide;
	
	public DoorFake(String name, Room oneSide) {
		super(name, oneSide, oneSide);
		this.name = name;		
		this.oneSide = oneSide;
		}
			
	public Room walkInto(Player player, Room room) {
		System.out.println("BANG! Ouch the door is fake, you can't get though!");
		return room;
	}
				
}
