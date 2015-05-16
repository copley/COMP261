package action;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class TurnRNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {

		robot.turnRight();
	}

	@Override
	public RobotProgramNode parse(Scanner scan) {

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
