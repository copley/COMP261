package action;

import interfaces.RobotNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class TurnAroundNode implements RobotNode {

	@Override
	public void execute(Robot robot) {

		robot.turnAround();
	}

	@Override
	public RobotNode parse(Scanner scan) {

		if (!Parser.gobble(Parser.TURNAROUND, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.TURNAROUND.toString(), scan);
		}
		return this;
	}

	@Override
	public String toString() {

		return "turnAround";
	}
}
