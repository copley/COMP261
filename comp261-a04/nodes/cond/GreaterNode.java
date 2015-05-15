package cond;

import java.util.Scanner;
import expression.ExpressionNode;
import parser.Parser;
import robot.Robot;
import interfaces.RobotEvalNode;

public class GreaterNode implements RobotEvalNode {

	private ExpressionNode expressionNode1 = null;
	private ExpressionNode expressionNode2 = null;

	@Override
	public boolean eval(Robot robot) {
		expressionNode1.execute(robot);
		expressionNode2.execute(robot);
		if (expressionNode1.getValue() > expressionNode2.getValue()){
			return true;
		}
		return false;
	}

	@Override
	public RobotEvalNode parse(Scanner scan) {

		// "gt"
		if (!Parser.gobble(Parser.GREATERTHEN, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.GREATERTHEN.toString(), scan);
		}
		// "("
		if (!Parser.gobble(Parser.OPENP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.OPENP.toString(), scan);
		}
		// "EXP"
		expressionNode1 = new ExpressionNode();
		expressionNode1.parse(scan);
		// ","
		if (!Parser.gobble(",", scan)) {
			Parser.fail("FAIL: Expecting \",\"", scan);
		}
		// "EXP"
		expressionNode2 = new ExpressionNode();
		expressionNode2.parse(scan);
		// ")"
		if (!Parser.gobble(Parser.CLOSEP, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.CLOSEP.toString(), scan);
		}
		return this;
	}

	@Override
	public String toString() {

		return null;
	}
}
