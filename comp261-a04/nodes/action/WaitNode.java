package action;

import java.util.Scanner;
import parser.Parser;
import robot.Robot;
import util.RobotNode;

public class WaitNode implements RobotNode {

	private RobotNode expressionNode = null;

	@Override
	public void execute(Robot robot) {

		if (expressionNode == null) {
			robot.idleWait();
		} else {
			expressionNode.execute(robot);
			// IMPLEMENT CODE HERE: should retrive the expression form the epxressionNode and then use it for wait
		}
	}

	@Override
	public RobotNode parse(Scanner scan) {

		if (!Parser.gobble(Parser.WAIT, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.WAIT.toString(), scan);
		}
		// if (scan.hasNext(Parser.OPENP)){ // has "("
		// Parser.gobble(Parser.OPENP, scan);
		// }
		//
		// // parse exp (and gobble)
		// expressionNode = new ExpressionNode().parse(scan);
		//
		// // close parenthesis
		// if (scan.hasNext(Parser.CLOSEP)){ // has ")"
		// Parser.gobble(Parser.CLOSEP, scan);
		// }
		return this;
	}

	@Override
	public String toString() {

		String s = "wait";
		if (expressionNode != null) s += "(" + expressionNode.toString() + ")";
		return s;
	}
}
