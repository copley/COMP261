package interfaces;

import java.util.Scanner;
import robot.Robot;

/**
 * This interface, like sensor-node is a bit redundant but was created to clarify that, when queried, robot sensors return a boolean.
 * Cond-node could be a subtype of expression-node, and return a boolean casted into a (0-1) integer.
 * 
 * @author diego
 *
 */

public interface RobotCondNode {

	public boolean evaluate(Robot robot);

	public RobotCondNode parse(Scanner scan);
		
}
