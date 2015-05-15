package action;

import interfaces.RobotExpNode;
import interfaces.RobotNode;
import java.util.Scanner;
import expression.ExpressionNode;
import parser.Parser;
import robot.Robot;

public class MoveNode implements RobotNode {

	private RobotExpNode expressionNode;

	@Override
	public void execute(Robot robot) {

		if (expressionNode != null) {
			expressionNode.execute(robot);
			int value = expressionNode.getValue();
			for (int i = 0; i < value; i++) {
				robot.move();
			}
		} else {
			robot.move();
		}
	}

	@Override
	public RobotNode parse(Scanner scan) {

		// "move"
		if (!Parser.gobble(Parser.MOVE, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.MOVE.toString(), scan);
		}
		
		// "("
		if (scan.hasNext(Parser.OPENP)) {
			// has open parenthesis -> gobble "("
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

	public String toString() {

		String s = "move";
		if (expressionNode != null) {
			s += String.format(" %s", expressionNode.getValue());
		}
		return s;
	}
}
