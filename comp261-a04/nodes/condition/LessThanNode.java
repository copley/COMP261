package condition;

import java.util.Scanner;
import parser.Parser;
import expression.Expression;
import robot.Robot;
import interfaces.RobotCondNode;
import interfaces.RobotExpNode;


public class LessThanNode implements RobotCondNode {

	private RobotExpNode expNode1 = null;
	private RobotExpNode expNode2 = null;

	@Override
	public RobotCondNode parse(Scanner scan) {
	
		// "gt"
		if (!Parser.gobble(Parser.LESSTHEN, scan)) {
			Parser.fail("FAIL: Expecting " + Parser.LESSTHEN.toString(), scan);
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
		if (expNode1.evaluate(robot) < expNode2.evaluate(robot)){
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "lt("+expNode1.toString()+", "+expNode2.toString()+")";
	}
}
