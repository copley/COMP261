package swen221.textadventure;

/**
 * This class represents an item of furniture in the game.
 * 
 * @author djp
 * 
 */
public class BookCase extends Furniture {
		
	public BookCase() {
		super("A special book case", "A special bookcase. This item is just like a book case, except that prodding it reveals a book which you can then pick up!");
	}
			
	public void prod(Player player, Room room) {
		System.out.println("Hey look, there is a book! You picked up the book. It's now in your pocket");
		player.addItem(new Book());
	}
}
