package expression;

import interfaces.RobotExpNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;


public class Expression implements RobotExpNode {

	private RobotExpNode expressionNode = null;

	@Override
	public RobotExpNode parse(Scanner scan) {

		if (scan.hasNext(Parser.NUM)) {
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
	public Integer evaluate(Robot robot) {

		return expressionNode.evaluate(robot);
	}

	@Override
	public String toString() {

		return expressionNode.toString();
	}

}
