package sensor;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import interfaces.RobotExpNode;
import interfaces.RobotSensNode;

public class WallDist implements RobotSensNode {

	private int value = -1;

	@Override
	public int evaluate(Robot robot) {

		return robot.getDistanceToWall();
	}

	@Override
	public RobotExpNode parse(Scanner scan) {

		// "wallDist"
		if (!Parser.gobble(Parser.WALLDIST, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.WALLDIST.toString(), scan);
		}

		return this;
	}

	@Override
	public int getValue() {

		return value;
	}

	@Override
	public String toString() {

		return Parser.WALLDIST.toString();
	}
}
