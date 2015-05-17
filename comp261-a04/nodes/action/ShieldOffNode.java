package action;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class ShieldOffNode implements RobotProgramNode {

	@Override
	public RobotProgramNode parse(Scanner scan) {
	
		if (!Parser.gobble(Parser.SHIELDOFF, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.SHIELDOFF.toString(), scan);
		}
		return this;
	}

	@Override
	public void execute(Robot robot) {

		robot.setShield(false);
	}

	@Override
	public String toString() {

		return "shieldOff";
	}
}
