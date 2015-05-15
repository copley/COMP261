package interfaces;

import java.util.Scanner;
import robot.Robot;

/**
 * Interface for all nodes that can be executed, including the top level program node
 */
public interface RobotProgramNode {
	
	public void execute(Robot robot);

	public RobotProgramNode parse(Scanner scan);
}
