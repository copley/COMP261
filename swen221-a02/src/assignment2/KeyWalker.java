package assignment2;

import java.awt.event.*;
import maze.*;

/**
 * A walker which moves based up the user input signals from keyboard
 */
public class KeyWalker extends Walker implements KeyListener {
	
	Direction direction;
	
	public KeyWalker() {
		super("Key Walker");		
	}
	
	/**
	 * Changes the direction of movement based on the arrow-keys of the keyboard when pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP:
				direction = Direction.NORTH;
				break;
			case KeyEvent.VK_DOWN:
				direction = Direction.SOUTH;
				break;
			case KeyEvent.VK_LEFT:
				direction = Direction.WEST;
				break;
			case KeyEvent.VK_RIGHT:
				direction = Direction.EAST;
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {		
		return;
	}

	/**
	 * Sets the direction to null when a key is released. This will allow the walker to stop.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		direction = null;
		return;
	}
	
	/**
	 * Moves repedetly the walker based on "direction". Implements a thread sleep to slow down the movement and allow more control to user
	 * @param view The view that the walker has from the current position in the maze 
	 */
	protected Direction move(View v) {
		// Not necessary, as there is a method pause() implemented for walker class
//		try {
//			Thread.sleep(50);
//		} catch (InterruptedException ex) {
//			Thread.currentThread().interrupt();
//		}
		pause();
		return direction;
	}
	
}