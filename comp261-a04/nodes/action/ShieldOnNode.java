package action;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class ShieldOnNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {

		robot.setShield(true);
	}

	@Override
	public RobotProgramNode parse(Scanner scan) {

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
