package action;

import interfaces.RobotExpNode;
import interfaces.RobotProgramNode;
import java.util.Scanner;
import nonTerminalNodes.ExpressionNode;
import parser.Parser;
import robot.Robot;

public class MoveNode implements RobotProgramNode {

	private RobotExpNode expressionNode;

	@Override
	public void execute(Robot robot) {

		if (expressionNode != null) {
			int value = expressionNode.evaluate(robot);
			for (int i = 0; i < value; i++) {
				robot.move();
			}
		} else {
			robot.move();
		}
	}

	@Override
	public RobotProgramNode parse(Scanner scan) {

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
			s += String.format(" %d", expressionNode.getValue());
		}
		return s;
	}
}
