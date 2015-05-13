package action;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import util.RobotNode;

public class ShieldOnNode implements RobotNode {

	@Override
	public void execute(Robot robot) {

		robot.setShield(true);
	}

	@Override
	public RobotNode parse(Scanner scan) {

		if (!Parser.gobble(Parser.SHIELDON, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.SHIELDON.toString(), scan);
		}
		return this;
	}

	@Override
	public String toString() {

		return "shieldOn";
	}
}
