package action;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import util.RobotNode;

public class TurnRNode implements RobotNode {

	@Override
	public void execute(Robot robot) {

		robot.turnRight();
	}

	@Override
	public RobotNode parse(Scanner scan) {

		if (!Parser.gobble(Parser.TURNR, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.TURNR.toString(), scan);
		}
		return this;
	}

	@Override
	public String toString() {

		return "turnR";
	}
}
