package condition;

import java.util.Scanner;
import expression.Expression;
import parser.Parser;
import robot.Robot;
import interfaces.RobotCondNode;
import interfaces.RobotExpNode;

public class GreaterThanNode implements RobotCondNode {

	private RobotExpNode expNode1 = null;
	private RobotExpNode expNode2 = null;

	@Override
	public RobotCondNode parse(Scanner scan) {

		// "gt"
		if (!Parser.gobble(Parser.GREATERTHEN, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.GREATERTHEN.toString(), scan);
		}

		// "("
		if (!Parser.gobble(Parser.OPENP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPENP.toString(), scan);
		}

		// "EXP"
		expNode1 = new Expression();
		expNode1.parse(scan);

		// ","
		if (!Parser.gobble(",", scan)) {
			Parser.fail("FAIL: Expecting \",\"", scan);
		}
		// "EXP"
		expNode2 = new Expression();
		expNode2.parse(scan);

		// ")"
		if (!Parser.gobble(Parser.CLOSEP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.CLOSEP.toString(), scan);
		}
		return this;
	}

	@Override
	public boolean evaluate(Robot robot) {

		if (expNode1.evaluate(robot) > expNode2.evaluate(robot)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {

		return "gt(" + expNode1.toString() + ", " + expNode2.toString() + ")";
	}
}
