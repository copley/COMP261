package condition;

import interfaces.RobotCondNode;
import java.util.Scanner;
import parser.Parser;
import robot.Robot;

public class Condition implements RobotCondNode {

	RobotCondNode conditionNode = null;

	@Override
	public boolean evaluate(Robot robot) {
		return conditionNode.evaluate(robot);
	}

	@Override
	public RobotCondNode parse(Scanner scan) {

		if (scan.hasNext(Parser.GREATERTHEN)) {
			conditionNode = new GreaterThanNode();
		} else if (scan.hasNext(Parser.LESSTHEN)) {
			conditionNode = new LessThanNode();
		} else if (scan.hasNext(Parser.EQUAL)) {
			conditionNode = new EqualToNode();
		} else if (scan.hasNext(Parser.AND)) {
			conditionNode = new AndNode();
		} else if (scan.hasNext(Parser.OR)) {
			conditionNode = new OrNode();
		} else if (scan.hasNext(Parser.NOT)) {
			conditionNode = new NotNode();
		}
		conditionNode.parse(scan);
		return conditionNode;
	}

	@Override
	public String toString(){
		return conditionNode.toString();
	}
}

