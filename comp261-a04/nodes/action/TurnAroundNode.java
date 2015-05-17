package action;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class TurnAroundNode implements RobotProgramNode {

	@Override
	public RobotProgramNode parse(Scanner scan) {
	
		if (!Parser.gobble(Parser.TURNAROUND, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.TURNAROUND.toString(), scan);
		}
		return this;
	}

	@Override
	public void execute(Robot robot) {

		robot.turnAround();
	}

	@Override
	public String toString() {

		return "turnAround";
	}
}
