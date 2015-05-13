package action;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import util.RobotNode;

public class MoveNode implements RobotNode {

	@Override
	public void execute(Robot robot) {

	}

	@Override
	public RobotNode parse(Scanner scan) {

		if (!Parser.gobble(Parser.MOVE, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.MOVE.toString(), scan);
		}
		if (scan.hasNext(Parser.OPENP)) {
			// has open parenthesis
			// gobble (
			// new expression node
			// gobble )
		}
		return this;
	}

	public String toString() {

		return "move";
	}
}
