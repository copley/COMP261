package util;

import java.util.Scanner;
import robot.Robot;

/**
 * Interface for all nodes that can be executed, including the top level program node
 */
public interface RobotNode {
	
	public void execute(Robot robot);

	public RobotNode parse(Scanner scan);
}
