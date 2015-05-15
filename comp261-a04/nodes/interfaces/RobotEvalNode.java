package interfaces;

import java.util.Scanner;
import robot.Robot;

public interface RobotEvalNode {

	public boolean eval(Robot robot);

	public RobotEvalNode parse(Scanner scan);
}
