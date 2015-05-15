package action;

import interfaces.RobotExpNode;
import interfaces.RobotNode;
import java.util.Scanner;
import expression.ExpressionNode;
import parser.Parser;
import robot.Robot;

public class WaitNode implements RobotNode {

	private RobotExpNode expressionNode;

	@Override
	public void execute(Robot robot) {

		if (expressionNode == null) {
			robot.idleWait();
		} else {
			expressionNode.execute(robot);
			int value = expressionNode.getValue();
			for (int i = 0; i < value; i++) {
				robot.idleWait();
			}
		}
	}

	@Override
	public RobotNode parse(Scanner scan) {

		if (!Parser.gobble(Parser.WAIT, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.WAIT.toString(), scan);
		}
		if (scan.hasNext(Parser.OPENP)) {
			// has open parenthesis -> gobble "("
			Parser.gobble(Parser.OPENP, scan);
			// new expression node -> parse and gobble
			expressionNode = new ExpressionNode();
			expressionNode.parse(scan);
			if (scan.hasNext(Parser.CLOSEP)) {
				Parser.gobble(Parser.CLOSEP, scan);
			} else {
				Parser.fail("Fail: expected "+Parser.CLOSEP, scan);
			}
		}
		return this;
	}

	@Override
	public String toString() {

		String s = "wait";
		if (expressionNode != null) s += "(" + expressionNode.toString() + ")";
		return s;
	}
}
