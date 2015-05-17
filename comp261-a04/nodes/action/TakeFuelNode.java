package action;

import interfaces.RobotProgramNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class TakeFuelNode implements RobotProgramNode {

	@Override
	public RobotProgramNode parse(Scanner scan) {
	
		if (!Parser.gobble(Parser.TAKEFUEL, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.TAKEFUEL.toString(), scan);
		}
		return this;
	}

	@Override
	public void execute(Robot robot) {

		robot.takeFuel();
	}

	@Override
	public String toString() {

		return "takeFuel";
	}
}
