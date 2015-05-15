package action;

import interfaces.RobotNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class TurnLNode implements RobotNode {

	@Override
	public void execute(Robot robot) {

		robot.turnLeft();
	}

	@Override
	public RobotNode parse(Scanner scan) {

		if (!Parser.gobble(Parser.TURNL, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.TURNL.toString(), scan);
		}
		return this;
	}

	@Override
	public String toString() {

		return "turnL";
	}
}
