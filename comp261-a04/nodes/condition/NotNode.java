package condition;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import interfaces.RobotCondNode;


public class NotNode implements RobotCondNode {

	Condition condition = null;

	@Override
	public RobotCondNode parse(Scanner scan) {

		// "not"
		if (!Parser.gobble(Parser.NOT, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.NOT.toString(), scan);
		}

		// "("
		if (!Parser.gobble(Parser.OPENP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPENP.toString(), scan);
		}

		// "COND"
		condition = new Condition();
		condition.parse(scan);


		// ")"
		if (!Parser.gobble(Parser.CLOSEP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.CLOSEP.toString(), scan);
		}
		return this;
	}

	@Override
	public boolean evaluate(Robot robot) {

		if (condition.evaluate(robot)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		return "not("+condition.toString()+")";
	}
}
