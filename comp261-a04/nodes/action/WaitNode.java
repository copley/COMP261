package action;

import interfaces.RobotExpNode;
import interfaces.RobotProgramNode;
import java.util.Scanner;
import expression.Expression;
import parser.Parser;
import robot.Robot;

public class WaitNode implements RobotProgramNode {

	private RobotExpNode expressionNode;
	private int expValue = -1;

	@Override
	public RobotProgramNode parse(Scanner scan) {
	
		if (!Parser.gobble(Parser.WAIT, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.WAIT.toString(), scan);
		}
		if (scan.hasNext(Parser.OPENP)) {
			// has open parenthesis -> gobble "("
			Parser.gobble(Parser.OPENP, scan);
			// new expression node -> parse and gobble
			expressionNode = new Expression();
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
	public void execute(Robot robot) {

		if (expressionNode == null) {
			robot.idleWait();
		} else {
			int expValue = expressionNode.evaluate(robot);
			for (int i = 0; i < expValue; i++) {
				robot.idleWait();
			}
		}
	}

	@Override
	public String toString() {

		String s = "wait";
		if (expressionNode != null) s += "(" + expValue + ")";
		return s;
	}
}
