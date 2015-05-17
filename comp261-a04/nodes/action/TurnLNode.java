package action;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class TurnLNode implements RobotProgramNode {

	@Override
	public RobotProgramNode parse(Scanner scan) {
	
		if (!Parser.gobble(Parser.TURNL, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.TURNL.toString(), scan);
		}
		return this;
	}

	@Override
	public void execute(Robot robot) {

		robot.turnLeft();
	}

	@Override
	public String toString() {

		return "turnL";
	}
}
