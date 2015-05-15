package sensor;

import java.util.Scanner;
import nonTerminalNodes.ExpressionNode;
import parser.Parser;
import robot.Robot;
import interfaces.RobotExpNode;
import interfaces.RobotSensNode;

public class BarrelLR implements RobotSensNode {

	private int value = -1;
	private RobotExpNode expressionNode;

	@Override
	public int evaluate(Robot robot) {

		if (expressionNode != null) {
			value = expressionNode.evaluate(robot);
			return robot.getBarrelLR(value);
		} else {
			return robot.getClosestBarrelLR();
		}
	}

	@Override
	public RobotExpNode parse(Scanner scan) {

		// "barrelFB"
		if (!Parser.gobble(Parser.BARRELLR, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.BARRELLR.toString(), scan);
		}

		// "("
		if (scan.hasNext(Parser.OPENP)) {
			Parser.gobble(Parser.OPENP, scan);

			// "EXP"
			expressionNode = new ExpressionNode();
			expressionNode.parse(scan);

			// ")"
			if (scan.hasNext(Parser.CLOSEP)) {
				Parser.gobble(Parser.CLOSEP, scan);
			} else {
				Parser.fail("Fail: expected " + Parser.CLOSEP, scan);
			}
		}
		return this;
	}

	@Override
	public int getValue() {

		return value;
	}

	@Override
	public String toString() {

		return Parser.BARRELLR.toString();
	}
}