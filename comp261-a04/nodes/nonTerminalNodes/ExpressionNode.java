package nonTerminalNodes;

import interfaces.RobotExpNode;
import java.util.Scanner;
import expression.NumberNode;
import expression.OpNode;
import expression.VarNode;
import parser.Parser;
import robot.Robot;

public class ExpressionNode implements RobotExpNode {

	private RobotExpNode expressionNode = null;

	@Override
	public int evaluate(Robot robot) {

		return expressionNode.evaluate(robot);
	}

	@Override
	public RobotExpNode parse(Scanner scan) {

		if (scan.hasNext(Parser.NUMPAT)) {
			expressionNode = new NumberNode();
		} else if (scan.hasNext(Parser.SENSOR)) {
			expressionNode = new SensorNode();
		} else if (scan.hasNext(Parser.VAR)) {
			expressionNode = new VarNode();
		} else if (scan.hasNext(Parser.OPERATION)) {
			expressionNode = new OpNode();
		} else {
			Parser.fail("Invalid action node", scan);
		}
		expressionNode.parse(scan);
		return expressionNode;
	}

	@Override
	public String toString() {

		return expressionNode.toString();
	}

	@Override
	public int getValue() {
		return expressionNode.getValue();
	}
}
