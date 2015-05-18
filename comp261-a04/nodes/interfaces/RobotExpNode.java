package interfaces;

import java.util.Scanner;
import robot.Robot;

public interface RobotExpNode {

	public Integer evaluate(Robot robot);

	public RobotExpNode parse(Scanner scan);

}
