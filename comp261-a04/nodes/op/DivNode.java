package op;

import java.util.Scanner;
import parser.Parser;
import expression.Expression;
import robot.Robot;
import interfaces.RobotExpNode;
import interfaces.RobotSensNode;

public class DivNode implements RobotSensNode {

	private Expression expNode1 = null;
	private Expression expNode2 = null;
	private int val1 = -1;
	private int val2 = -1;

	@Override
	public int evaluate(Robot robot) {

		val1 = expNode1.evaluate(robot);
		val2 = expNode2.evaluate(robot);
		// check division by 0
		if (expNode2.evaluate(robot) == 0) {
			throw new ArithmeticException("You can't divide by zero !!");
		}
		return (int) (Math.round((float) val1 / val2));
	}

	@Override
	public RobotExpNode parse(Scanner scan) {

		// "sub"
		if (!Parser.gobble(Parser.SUB, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.SUB.toString(), scan);
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

	public String toString() {

		return String.format("sub ( %d, %d )", val1, val2);
	}

}