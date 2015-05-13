package action;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import util.RobotNode;

public class TakeFuelNode implements RobotNode {

	@Override
	public void execute(Robot robot) {

		robot.takeFuel();
	}

	@Override
	public RobotNode parse(Scanner scan) {

		if (!Parser.gobble(Parser.TAKEFUEL, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.TAKEFUEL.toString(), scan);
		}
		return this;
	}

	@Override
	public String toString() {

		return "takeFuel";
	}
}
