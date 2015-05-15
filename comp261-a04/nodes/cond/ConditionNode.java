package cond;

import interfaces.RobotEvalNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class ConditionNode implements RobotEvalNode {

	RobotEvalNode nextNode = null;

	@Override
	public boolean eval(Robot robot) {
		return nextNode.eval(robot);
	}

	@Override
	public RobotEvalNode parse(Scanner scan) {

		if (scan.hasNext(Parser.GREATERTHEN)) {
			nextNode = new GreaterNode();
		} else if (scan.hasNext(Parser.LESSTHEN)) {
			nextNode = new LessNode();
		} else if (scan.hasNext(Parser.EQUAL)) {
			nextNode = new EqualNode();
		} else if (scan.hasNext(Parser.AND)) {
			nextNode = new AndNode();
		} else if (scan.hasNext(Parser.OR)) {
			nextNode = new OrNode();
		} else if (scan.hasNext(Parser.NOT)) {
			nextNode = new NotNode();
		}
		return nextNode.parse(scan);
	}

	@Override
	public String toString(){
		return nextNode.toString();
	}
}

