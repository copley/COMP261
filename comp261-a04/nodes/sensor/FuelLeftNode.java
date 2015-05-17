package sensor;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import interfaces.RobotExpNode;
import interfaces.RobotSensNode;

public class FuelLeftNode implements RobotSensNode {

	@Override
	public RobotExpNode parse(Scanner scan) {
	
		// "fuelLeft"
		if (!Parser.gobble(Parser.FUELLEFT, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.FUELLEFT.toString(), scan);
		}
	
		return this;
	}

	@Override
	public int evaluate(Robot robot) {

		return robot.getFuel();
	}

	@Override
	public String toString() {

		return Parser.FUELLEFT.toString();
	}
}
