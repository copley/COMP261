package condition;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import interfaces.RobotCondNode;


public class OrNode implements RobotCondNode {

	Condition ifCondition1 = null;
	Condition ifCondition2 = null;

	@Override
	public RobotCondNode parse(Scanner scan) {

		// "and"
		if (!Parser.gobble(Parser.OR, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OR.toString(), scan);
		}

		// "("
		if (!Parser.gobble(Parser.OPENP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPENP.toString(), scan);
		}

		// "COND"
		ifCondition1 = new Condition();
		ifCondition1.parse(scan);

		// ","
		if (!Parser.gobble(",", scan)) {
			Parser.fail("FAIL: Expecting \",\"", scan);
		}

		// "COND"
		ifCondition2 = new Condition();
		ifCondition2.parse(scan);

		// ")"
		if (!Parser.gobble(Parser.CLOSEP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.CLOSEP.toString(), scan);
		}
		return this;
	}

	@Override
	public boolean evaluate(Robot robot) {

		if (ifCondition1.evaluate(robot) || ifCondition2.evaluate(robot)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "or("+ifCondition1.toString()+", "+ifCondition2.toString()+")";
	}
}
